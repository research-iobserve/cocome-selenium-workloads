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
import org.iobserve.selenium.workloadgeneration.WorkloadGeneration;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.TestWorkload;

/**
 * Used to (manually) register Workloads. The {@link WorkloadGeneration Workload Generator} looks
 * the Workloads up and executes them.
 *
 * @author Marc Adolf
 *
 */
public class WorkloadRegistry {

    private static final Map<String, Class<? extends AbstractWorkload>> REGISTERED_WORKLOADS = new HashMap<>();

    /**
     * Fills the registry with predefined Workloads and their names. The keys are used to get the
     * right workloads from the command line input.
     */
    public static void fillRegistry() {
        LogManager.getLogger(WorkloadRegistry.class).debug("Filling the registry");
        WorkloadRegistry.REGISTERED_WORKLOADS.put("Test", TestWorkload.class);
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

        logger.debug("Looking for workload with name: " + name);

        final Class<? extends AbstractWorkload> searchedWorkload = WorkloadRegistry.REGISTERED_WORKLOADS.get(name);
        if (searchedWorkload == null) {
            logger.debug(name + " was not found in the registry");
            throw new WorkloadNotCreatedException("Workload " + name + " was not found in the (internal) registry");
        }

        try {
            return WorkloadRegistry.REGISTERED_WORKLOADS.get(name).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(name + " could not be instantiated", e);
            throw new WorkloadNotCreatedException("Workload " + name + " could not be instantiated");
        }
    }
}
