/***************************************************************************
 * Copyright (C) 2017 iObserve Project (https://www.iobserve-devops.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package org.iobserve.selenium.workloads.handling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.tasks.AbstractTask;
import org.iobserve.selenium.tasks.AbstractUserTask;
import org.iobserve.selenium.tasks.UserTaskWrapper;
import org.iobserve.selenium.tasks.systemtasks.CreateNewSessionTask;
import org.iobserve.selenium.workloadgeneration.WorkloadGeneration;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;

/**
 * Collects and handles all {@link AbstractUserTask tasks} and the {@link WorkloadConfiguration} for
 * one specified workload.
 *
 *
 * @author Marc Adolf
 * @author Sören Henning
 *
 */
public final class WorkloadPlan {

    private static final Logger LOGGER = LogManager.getLogger(WorkloadPlan.class);
    private final List<AbstractTask> plannedTasks = new ArrayList<>();
    private WorkloadConfiguration config;
    private Boolean repeatable = true;

    private WorkloadPlan() {
    }

    private WorkloadPlan(final WorkloadConfiguration config) {
        this.config = config;
    }

    /**
     * Creates a new {@link Builder} which is used to assemble the {@link WorkloadPlan}. A standard
     * {@link WorkloadConfiguration} is used if the plan is executed by the
     * {@link WorkloadGeneration}
     *
     * @return A new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();

    }

    public Boolean getRepeatable() {
        return this.repeatable;
    }

    public void setRepeatable(final Boolean repeatable) {
        this.repeatable = repeatable;
    }

    /**
     * Creates a new {@link Builder} which is used to assemble the {@link WorkloadPlan}.
     *
     * @param config
     *            The {@link WorkloadConfiguration} that defines the base URL of the used web
     *            service, the used driver and the number of repetitions.
     *
     * @return A new {@link Builder}.
     */
    public static Builder builder(final WorkloadConfiguration config) {
        return new Builder(config);

    }

    /**
     * Executes all defined task. Repeats as often as the {@link WorkloadConfiguration} states it.
     * If the {@link WorkloadPlan} already has a WorkloadConfiguration the parameter is unused.
     *
     * @param configuration
     *            The {@link WorkloadConfiguration} that defines the base URL of the used web
     *            service, the used driver and the number of repetitions. It will NOT be used if the
     *            Object already has an existing configuration.
     */
    public void execute(final WorkloadConfiguration configuration) {
        final WorkloadConfiguration usedConfig;
        if (this.config != null) {
            usedConfig = this.config;
        } else {
            usedConfig = configuration;
        }
        final List<AbstractTask> tasks = new ArrayList<>(this.plannedTasks);
        // Create fuzzed list of task
        // if (usedConfig.isFuzzy()) {
        // for (final AbstractTask t : this.plannedTasks) {
        // for (int i = 1; i <= t.getRepetitions(usedConfig.isFuzzy()); i++) {
        // tasks.add(t.getClass())
        // }
        //
        // }
        // }
        int numberOfRuns = usedConfig.getNumberOfRuns();
        if (!this.repeatable) {
            numberOfRuns = 1;
        }

        for (int i = 0; i < numberOfRuns; i++) {
            tasks.stream().forEach((final AbstractTask t) -> {
                IntStream.range(1, t.getRepetitions(usedConfig.isFuzzy()) + 1).forEach(idx -> {
                    WorkloadPlan.LOGGER.info(String.format("Executing task: %s in repetition %d ", t.getName(), idx));
                    t.accept(usedConfig);
                });
            });

        }
    }

    /**
     *
     * Assembles the {@link AbstractUserTask tasks} for a new {@link WorkloadPlan}.
     *
     * @author Marc Adolf
     * @author Sören Henning
     *
     */
    public static final class Builder {

        private final WorkloadPlan workloadPlan;

        private Builder() {
            this.workloadPlan = new WorkloadPlan();
        }

        private Builder(final WorkloadConfiguration config) {
            this.workloadPlan = new WorkloadPlan(config);
        }

        /**
         * Adds an {@link AbstractTask} at the current end of the task list in the given
         * {@link WorkloadPlan}.
         *
         * @param task
         *            The {@link AbstractTask} to be executed next.
         * @return The current {@link Builder} instance which assembles all tasks.
         */
        public Builder then(final AbstractTask task) {
            this.workloadPlan.plannedTasks.add(task);
            return this;
        }

        /**
         * Wraps an {@link AbstractUserTask} in an {@link AbstractTask} and adds it at the current
         * end of the task list in the given {@link WorkloadPlan}.
         *
         * @param task
         *            The {@link AbstractUserTask} to be executed next.
         * @return The current {@link Builder} instance which assembles all tasks.
         */
        public Builder then(final AbstractUserTask task) {
            this.workloadPlan.plannedTasks.add(new UserTaskWrapper(task));
            return this;
        }

        /**
         *
         * @param task
         *            The {@link AbstractUserTask} to be executed next.
         * @param maxRepetitions
         *            The maximum repetitions that may occur if the workload is executed as fuzzy.
         * @return The current {@link Builder} instance which assembles all tasks.
         */
        public Builder fuzzyThen(final AbstractUserTask task, final int maxRepetitions) {
            this.workloadPlan.plannedTasks.add(new UserTaskWrapper(task, maxRepetitions));
            return this;
        }

        /**
         * Finishes the assembling of the tasks.
         *
         * @return The completed {@link WorkloadPlan}.
         */
        public WorkloadPlan build() {
            return this.workloadPlan;
        }

        /**
         * Shortcut for adding the {@link CreateNewSessionTask}.
         *
         * @return The current {@link Builder} instance which assembles all tasks.
         */
        public Builder newSession() {
            return this.then(new CreateNewSessionTask());
        }

        public Builder setNonRepeatable() {
            this.workloadPlan.setRepeatable(false);
            return this;
        }

    }

}
