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
package org.iobserve.selenium.workloads.registry;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.WorkloadGenerationMain;
import org.iobserve.selenium.workloads.cocome.CocomeCashierCashShoppingWorkload;
import org.iobserve.selenium.workloads.cocome.CocomeEnterpriseManagerActionWorkload;
import org.iobserve.selenium.workloads.cocome.TestWorkload;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.jpetstore.AccountManagerWorkload;
import org.iobserve.selenium.workloads.jpetstore.BrowsingUserWorkload;
import org.iobserve.selenium.workloads.jpetstore.CatLoverWorkload;
import org.iobserve.selenium.workloads.jpetstore.FishLoverWorkload;
import org.iobserve.selenium.workloads.jpetstore.JPetStoreCdorWorkload;
import org.iobserve.selenium.workloads.jpetstore.NewCustomerWorkload;
import org.iobserve.selenium.workloads.jpetstore.SingleCatBuyerWorkload;
import org.iobserve.selenium.workloads.jpetstore.SingleReptileBuyerWorkload;

/**
 * Used to (manually) register Workloads. The {@link WorkloadGenerationMain Workload Generator} looks
 * the Workloads up and executes them.
 *
 * @author Marc Adolf
 *
 */
public final class WorkloadRegistry {

    /**
     * Empty Constructor.
     */
    private WorkloadRegistry() {
    }

    /**
     * Returns the predefined registered workloads their names and the corresponding classes. The
     * keys are used to get the right workloads from the command line input.
     *
     * @return all {@link AbstractWorkload workloads} known by the registry
     */
    public static Map<String, Class<? extends AbstractWorkload>> getRegisteredWorkloads() {
        LogManager.getLogger(WorkloadRegistry.class).debug("Filling the registry");
        final Map<String, Class<? extends AbstractWorkload>> registeredWorkloads = new HashMap<>();

        registeredWorkloads.put("Test", TestWorkload.class);
        registeredWorkloads.put("EM-Workload1", CocomeEnterpriseManagerActionWorkload.class);
        registeredWorkloads.put("CS_Workload1", CocomeCashierCashShoppingWorkload.class);
        registeredWorkloads.put("CDOR", JPetStoreCdorWorkload.class);
        registeredWorkloads.put("AccountManager", AccountManagerWorkload.class);
        registeredWorkloads.put("BrowsingUser", BrowsingUserWorkload.class);
        registeredWorkloads.put("CatLover", CatLoverWorkload.class);
        registeredWorkloads.put("FishLover", FishLoverWorkload.class);
        registeredWorkloads.put("NewCustomer", NewCustomerWorkload.class);
        registeredWorkloads.put("SingleCatBuyer", SingleCatBuyerWorkload.class);
        registeredWorkloads.put("SingleReptileBuyer", SingleReptileBuyerWorkload.class);

        return registeredWorkloads;
    }

    /**
     * Lookup the given name and try to create a new Instance of the referenced
     * {@link AbstractWorkload Workload}.
     *
     * @param name
     *            The registered name of the workload
     * @return new Instance of the {@link AbstractWorkload Workload}
     * @throws WorkloadNotCreatedException
     *             If the workload was not found or could not be created.
     */
    public static AbstractWorkload getWorkloadInstanceByName(final String name) throws WorkloadNotCreatedException {
        final Logger logger = LogManager.getLogger(WorkloadRegistry.class);

        if (logger.isDebugEnabled()) {
            logger.debug("Looking for workload with name: " + name);
        }

        final Class<? extends AbstractWorkload> searchedWorkload = WorkloadRegistry.getRegisteredWorkloads().get(name);
        if (searchedWorkload == null) {
            if (logger.isDebugEnabled()) {
                logger.debug(name + " was not found in the registry");
            }
            throw new WorkloadNotCreatedException("Workload " + name + " was not found in the (internal) registry");
        }

        try {
            return WorkloadRegistry.getRegisteredWorkloads().get(name).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            if (logger.isErrorEnabled()) {
                logger.error(name + " could not be instantiated", e);
            }
            throw new WorkloadNotCreatedException("Workload " + name + " could not be instantiated"); // NOPMD
                                                                                                      // stacktrace
                                                                                                      // in
                                                                                                      // error
                                                                                                      // log
        }
    }
}
