/***************************************************************************
 * Copyright (C) 2018 iObserve Project (https://www.iobserve-devops.net)
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
package org.iobserve.selenium.workloads.jpetstore;

import org.iobserve.selenium.tasks.jpetstore.account.ChangeAccountInformationTask;
import org.iobserve.selenium.tasks.jpetstore.account.ChangeAccountInformationTask.Attribute;
import org.iobserve.selenium.tasks.jpetstore.account.ViewOrderTask;
import org.iobserve.selenium.tasks.jpetstore.common.LoginJPetStoreTask;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 * Logs in , changes the 'Address2' attribute of the user and visits the first order, if present.
 *
 * @author Marc Adolf
 *
 */
public class AccountManagerWorkload extends AbstractWorkload {

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.workloads.handling.AbstractWorkload#assembleWorkloadTasks()
     */
    @Override
    public WorkloadPlan assembleWorkloadTasks() {
        final String username = "j2ee";
        final String password = "j2ee";

        return WorkloadPlan.builder().then(new LoginJPetStoreTask(username, password))
                .fuzzyThen(new ChangeAccountInformationTask(Attribute.ADDRESS2, "Christian-Albrechts-Platz 4"), 10)
                .fuzzyThen(new ViewOrderTask(), 20).newSession().build();
    }

}
