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
package org.iobserve.selenium.workloads;

import java.util.ArrayList;
import java.util.List;

import org.iobserve.selenium.tasks.CreateNewSessionTask;
import org.iobserve.selenium.tasks.ITask;
import org.iobserve.selenium.tasks.IUserTask;
import org.iobserve.selenium.tasks.UserTaskWrapper;
import org.iobserve.selenium.tasks.enterprisemanager.EMLoginTask;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;
import org.openqa.selenium.WebDriver;

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

    private final List<ITask> tasks = new ArrayList<>();
    private final WorkloadConfiguration config;

    private WorkloadPlan(final WorkloadConfiguration config) {
        this.config = config;
    }

    private WorkloadPlan(final String baseUrl, final int numberOfRuns, final String pathWebDriver) {
        this.config = new WorkloadConfiguration(baseUrl, numberOfRuns, pathWebDriver);
    }

    /**
     * Creates a new {@link Builder} which is used to assemble the {@link WorkloadPlan}.
     *
     * @param config
     *            A configuration object that defines the base URL of the used web service, the used
     *            driver and the number of repetitions.
     * @return A new {@link Builder}.
     */
    public static Builder builder(final WorkloadConfiguration config) {
        return new Builder(config);

    }

    /**
     * Creates a new {@link Builder} which is used to assemble the {@link WorkloadPlan}.
     *
     * @param baseUrl
     *            The base URL of the used web service.
     * @param numberOfRuns
     *            Number of repetitions of the {@link WorkloadPlan}
     * @param pathWebDriver
     *            The path to the used {@link WebDriver}.
     * @return A new {@link Builder}.
     */
    public static Builder builder(final String baseUrl, final int numberOfRuns, final String pathWebDriver) {
        final WorkloadConfiguration config = new WorkloadConfiguration(baseUrl, numberOfRuns, pathWebDriver);
        return new Builder(config);
    }

    /**
     * Executes all defined task. Repeats as often as the {@link WorkloadConfiguration} states it.
     */
    public void execute() {
        for (int i = 0; i < this.config.getNumberOfRuns(); i++) {
            this.tasks.stream().forEach(t -> {
                System.out.println("Execute task: " + t.getName());
                t.accept(this.config);
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
    private static final class Builder {

        private final WorkloadPlan workloadPlan;

        private Builder(final WorkloadConfiguration config) {
            this.workloadPlan = new WorkloadPlan(config);
        }

        public Builder then(final ITask task) {
            this.workloadPlan.tasks.add(task);
            return this;
        }

        public Builder then(final IUserTask task) {
            this.workloadPlan.tasks.add(new UserTaskWrapper(task));
            return this;
        }

        public WorkloadPlan build() {
            return this.workloadPlan;
        }

        public Builder newSession() {
            return this.then(new CreateNewSessionTask());
        }

    }

    public static void main(final String[] args) {
        final WorkloadConfiguration config = new WorkloadConfiguration("https://172.17.0.2:8181", 1,
                "/usr/lib/node_modules/phantomjs/bin/phantomjs");

        WorkloadPlan.builder(config).then(EMLoginTask.create()).newSession().then(new EMLoginTask()).build().execute();

    }

}
