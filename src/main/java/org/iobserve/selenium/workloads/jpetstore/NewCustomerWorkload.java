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

import org.iobserve.selenium.tasks.jpetstore.account.CreateNewCustomerTask;
import org.iobserve.selenium.tasks.jpetstore.buy.AddReptilesToCartTask;
import org.iobserve.selenium.tasks.jpetstore.common.CheckoutJPetStoreTask;
import org.iobserve.selenium.tasks.jpetstore.common.LoginJPetStoreTask;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 * Creates a new Customer, logs in and buys one reptile.
 *
 * @author Marc Adolf
 *
 */
public class NewCustomerWorkload extends AbstractWorkload {

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.workloads.handling.AbstractWorkload#assembleWorkloadTasks()
     */
    @Override
    public WorkloadPlan assembleWorkloadTasks() {
        final String username = "Newone";
        final String password = "new";

        return WorkloadPlan.builder().then(new CreateNewCustomerTask(username, password))
                .fuzzyThen(new AddReptilesToCartTask(1, 1), 1).then(new LoginJPetStoreTask(username, password))
                .then(new CheckoutJPetStoreTask()).build();
    }

}
