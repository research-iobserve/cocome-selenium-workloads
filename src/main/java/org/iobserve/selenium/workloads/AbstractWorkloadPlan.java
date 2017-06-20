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

import java.util.List;

import org.iobserve.selenium.tasks.AbstractUserTask;

/**
 * Represents an interchangeable abstract workload format. Used for workload creation with Selenium.
 *
 * @author Marc Adolf
 *
 */
public abstract class AbstractWorkloadPlan {
    private final String baseUrl;
    private final int numberOfRuns;
    private final String pathPhantomjs;

    // TODO distinct between repeatable workloads and non repeatable

    /**
     *
     *
     * @param baseUrl
     *            base URL of the visited website.
     * @param numberOfRuns
     *            number of repetitions for each task.
     * @param pathPhantomjs
     *            path to the PhantomJS binaries.
     */
    protected AbstractWorkloadPlan(final String baseUrl, final int numberOfRuns, final String pathPhantomjs) {
        this.baseUrl = baseUrl;
        this.numberOfRuns = numberOfRuns;
        this.pathPhantomjs = pathPhantomjs;
    }

    /**
     * Aggregates all task that are contained in the {@link AbstractWorkloadPlan WorkloadPlan}.
     *
     * @return the tasks to be executed in a fixed order.
     */
    public abstract List<AbstractUserTask> generateWorkflow();

    protected final String getBaseUrl() {
        return this.baseUrl;
    }

    protected final int getNumberOfRuns() {
        return this.numberOfRuns;
    }

    protected final String getPathPhantomjs() {
        return this.pathPhantomjs;
    }

}
