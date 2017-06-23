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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.tasks.CreateNewSessionTask;
import org.iobserve.selenium.tasks.ISystemTask;
import org.iobserve.selenium.tasks.IUserTask;
import org.iobserve.selenium.tasks.UserTaskWrapper;
import org.iobserve.selenium.workloadgeneration.WorkloadGeneration;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;

/**
 * Collects and handles all {@link IUserTask tasks} and the {@link WorkloadConfiguration} for one
 * specified workload.
 *
 *
 * @author Marc Adolf
 * @author Sören Henning
 *
 */
public final class WorkloadPlan {

    private final List<ISystemTask> tasks = new ArrayList<>();
    private WorkloadConfiguration config;
    private final Logger logger = LogManager.getLogger(this.getClass());

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

    // /**
    // * Creates a new {@link Builder} which is used to assemble the {@link WorkloadPlan}.
    // *
    // * @param baseUrl
    // * The base URL of the used web service.
    // * @param numberOfRuns
    // * Number of repetitions of the {@link WorkloadPlan}
    // * @param pathWebDriver
    // * The path to the used {@link WebDriver}.
    // * @return A new {@link Builder}.
    // */
    // public static Builder builder(final String baseUrl, final int numberOfRuns, final String
    // pathWebDriver) {
    // final WorkloadConfiguration config = new WorkloadConfiguration(baseUrl, numberOfRuns,
    // pathWebDriver);
    // return new Builder();
    // }

    /**
     * Executes all defined task. Repeats as often as the {@link WorkloadConfiguration} states it.
     * If the {@link WorkloadPlan} already has a WorkloadConfiguration the parameter is unused.
     *
     * @param config
     *            The {@link WorkloadConfiguration} that defines the base URL of the used web
     *            service, the used driver and the number of repetitions. It will NOT be used if the
     *            Object already has an existing configuration.
     */
    public void execute(final WorkloadConfiguration config) {
        final WorkloadConfiguration usedConfig;
        if (this.config != null) {
            usedConfig = this.config;
        } else {
            usedConfig = config;
        }
        for (int i = 0; i < usedConfig.getNumberOfRuns(); i++) {
            this.tasks.stream().forEach(t -> {
                this.logger.info("Executing task: " + t.getName());
                t.accept(usedConfig);
            });
        }
    }

    /**
     *
     * Assembles the {@link IUserTask tasks} for a new {@link WorkloadPlan}.
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
         * Adds an {@link ISystemTask} at the current end of the task list in the given
         * {@link WorkloadPlan}.
         *
         * @param task
         *            The {@link ISystemTask} to be executed next.
         * @return The current {@link Builder} instance which assembles all tasks.
         */
        public Builder then(final ISystemTask task) {
            this.workloadPlan.tasks.add(task);
            return this;
        }

        /**
         * Wraps an {@link IUserTask} in an {@link ISystemTask} and adds it at the current end of
         * the task list in the given {@link WorkloadPlan}.
         *
         * @param task
         *            The {@link IUserTask} to be executed next.
         * @return The current {@link Builder} instance which assembles all tasks.
         */
        public Builder then(final IUserTask task) {
            this.workloadPlan.tasks.add(new UserTaskWrapper(task));
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

    }

}
