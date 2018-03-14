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

import org.iobserve.selenium.tasks.jpetstore.buy.AddFishToCartTask;
import org.iobserve.selenium.tasks.jpetstore.common.CheckoutJPetStoreTask;
import org.iobserve.selenium.tasks.jpetstore.common.LoginJPetStoreTask;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 * Represents the workload used for evaluation in jPetstore by Christoph Dornieden in his Masters
 * Thesis. See {@link http://eprints.uni-kiel.de/38825/}.
 *
 * @author Marc Adolf
 *
 */
public class JPetStoreCdorWorkload extends AbstractWorkload {

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.workloads.handling.AbstractWorkload#assembleWorkloadTasks()
     */
    @Override
    public WorkloadPlan assembleWorkloadTasks() {
        final int amountOfFish = 10;
        // TODO better replace with enum?
        final int fishPosition = 0;

        final String username = "j2ee";
        final String password = "j2ee";

        // TODO add remaining workloads
        return WorkloadPlan.builder().then(new AddFishToCartTask(amountOfFish, fishPosition))
                .then(new LoginJPetStoreTask(username, password)).then(new CheckoutJPetStoreTask()).newSession()
                .build();
    }

}
