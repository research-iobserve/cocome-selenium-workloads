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

import org.iobserve.selenium.tasks.cocome.cashier.CSBuySingleItemNTimesWithCashTask;
import org.iobserve.selenium.tasks.cocome.cashier.CSLoginTask;
import org.iobserve.selenium.tasks.cocome.cashier.CSLogoutTask;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 * Login of the cashier, buying some products with cash and logout.
 *
 * @author Marc Adolf
 *
 */
public class CocomeCashierCashShoppingWorkload extends AbstractWorkload {

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.workloads.handling.AbstractWorkload#assembleWorkloadTasks()
     */
    @Override
    public WorkloadPlan assembleWorkloadTasks() {
        final int productBarCode = 12345678;
        // first shopping
        final int firstNumberOfItems = 4;
        final int firstCashToPay = 50;
        // second shopping
        final int secondNumberOfItems = 7;
        final int secondCashToPay = 100;
        return WorkloadPlan.builder().then(new CSLoginTask())
                .then(new CSBuySingleItemNTimesWithCashTask(productBarCode, firstNumberOfItems, firstCashToPay))
                .then(new CSBuySingleItemNTimesWithCashTask(productBarCode, secondNumberOfItems, secondCashToPay))
                .then(new CSLogoutTask()).build();
    }

    // @Override
    // public String getWorkloadDescription() {
    // return "Cocome workload, login, do two different shoppings and logout";
    // }

}
