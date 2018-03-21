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
package org.iobserve.selenium.workloads.cocome;

import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMLoginTask;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 *
 * @author Marc Adolf
 *
 */
public class TestWorkload extends AbstractWorkload {

    @Override
    public WorkloadPlan assembleWorkloadTasks() {
        return WorkloadPlan.builder().then(EMLoginTask.create()).newSession().then(new EMLoginTask()).build();
    }

}
