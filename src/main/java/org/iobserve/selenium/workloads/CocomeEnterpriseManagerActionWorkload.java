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

import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMAddSecondProductToFirstStoreOfSecondEnterpriseTask;
import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMCreateNewEnterpriseTask;
import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMCreateNewProductTask;
import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMCreateNewStoreTask;
import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMLoginTask;
import org.iobserve.selenium.tasks.cocome.enterprisemanager.EMLogoutTask;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 * Login the enterprise manager, create a new enterprise, create new product, create a new store for
 * the created enterprise, adds the created product to the new store and logout.
 *
 * @author Marc Adolf
 *
 */
public class CocomeEnterpriseManagerActionWorkload extends AbstractWorkload {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.iobserve.selenium.workloads.handling.AbstractWorkload#assembleWorkloadTasks(org.iobserve.
     * selenium.workloads.config.WorkloadConfiguration)
     */
    @Override
    public WorkloadPlan assembleWorkloadTasks() {
        // Enterprise paramters
        final String enterpriseName = "SuperCatz";

        // Product parameters
        final String productName = "SuperCatz Catfood";
        final int productBarCode = 6666;
        final float productPrice = 6;

        // Shop paramters
        final String storeName = "Auntie Cat";
        final String storeLocation = "Flensburg Main Station";

        // Product parameters for shop
        final float shopPrice = productPrice * 2;
        final int stockMin = 20;
        final int stockMax = 500;
        final int stockCurrent = 300;

        return WorkloadPlan.builder().then(new EMLoginTask()).then(new EMCreateNewEnterpriseTask(enterpriseName))
                .then(new EMCreateNewProductTask(productName, productBarCode, productPrice))
                .then(new EMCreateNewStoreTask(storeName, storeLocation))
                .then(new EMAddSecondProductToFirstStoreOfSecondEnterpriseTask(shopPrice, stockMin, stockMax,
                        stockCurrent))
                .then(new EMLogoutTask()).build();
    }

    // @Override
    // public String getWorkloadDescription() {
    // return "Cocome workload: creates a new product, a new store and adds both to a new
    // enterprise";
    // }

}
