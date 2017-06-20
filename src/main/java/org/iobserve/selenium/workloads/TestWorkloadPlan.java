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

import java.util.LinkedList;
import java.util.List;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.iobserve.selenium.tasks.enterprisemanager.EMLogin;

/**
 * Represents a complete iteration of task that generate the workload.
 *
 * @author Marc Adolf
 *
 */
public class TestWorkloadPlan extends AbstractWorkloadPlan {

    /**
     * Calls the constructor of {@link AbstractWorkloadPlan}
     * {@link AbstractWorkloadPlan#AbstractWorkloadPlan(String, String, int)}.
     *
     * @param pathPhantomjs
     *            path to the PhantomJS binaries.
     * @param baseUrl
     *            base URL of the visited website.
     * @param numberOfRuns
     *            number of repetitions for each task.
     */

    public TestWorkloadPlan(final String baseUrl, final int numberOfRuns, final String pathPhantomjs) {
        super(baseUrl, numberOfRuns, pathPhantomjs);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.workloads.AbstractWorkload#generateWorkflow()
     */
    @Override
    public List<AbstractUserTask> generateWorkflow() {
        final List<AbstractUserTask> resultList = new LinkedList<>();
        final String baseUrl = this.getBaseUrl();
        final String pathDriver = this.getPathPhantomjs();
        final int numberOfRuns = this.getNumberOfRuns();
        resultList.add(new EMLogin(baseUrl, numberOfRuns, pathDriver));
        return resultList;
    }

}
