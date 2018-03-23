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
package org.iobserve.selenium.workloadgeneration;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.beust.jcommander.JCommander;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.common.CommandlineArguments;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.registry.WorkloadNotCreatedException;
import org.iobserve.selenium.workloads.registry.WorkloadRegistry;

/**
 * Generates workloads for CoCoME. Designed to be used with the iObserve Experiment. Uses Selenium
 * to execute the workloads.
 *
 * @author Christoph Dornieden
 * @author Marc Adolf
 * @author Reiner Jung
 */
public final class WorkloadGeneration {

    private static final Logger LOGGER = LogManager.getLogger(WorkloadGeneration.class);

    /**
     * Empty constructor.
     */
    private WorkloadGeneration() {
    }

    /**
     * Executes the given workloads with the given parameters.
     *
     * @param args
     *            The parameters to be parsed by {@link CommandlineArguments JCommander}.
     */
    public static void main(final String[] args) {

        final CommandlineArguments arguments = new CommandlineArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        // logger.debug("Webdriver path: " + arguments.getPathPhantomjs());
        // logger.debug("Base URL: " + arguments.getBaseUrl());
        // logger.debug("Number of runs: " + arguments.getNumberOfRuns());
        WorkloadGeneration.LOGGER.debug("Workloads to execute: {}", arguments.getWorkloads());

        WorkloadGeneration.LOGGER.info(
                "Creating the configuration for the workload with the webdriver path '{}', base URL '{}' and will repeating it {} times using fuzzy mode {}", // NOPMD
                arguments.getPathPhantomjs(), arguments.getBaseUrl(), arguments.getNumberOfRuns(),
                CommandlineArguments.getIsFuzzy());

        if (arguments.getWorkloads().isEmpty() || CommandlineArguments.getPrintWorkloads()) {
            WorkloadGeneration.printAvailableWorkloads();
            return;
        }

        final WorkloadConfiguration config = new WorkloadConfiguration(arguments.getBaseUrl(),
                arguments.getNumberOfRuns(), arguments.getPathPhantomjs(), CommandlineArguments.getIsFuzzy());

        final List<String> workloads = arguments.getWorkloads();

        /*
         * The registry has to be filled beforehand. Better Ideas are welcome.
         */

        WorkloadGeneration.LOGGER.info("Trying to execute following workloads: {}", workloads.toString());

        for (final String name : workloads) {
            try {
                final AbstractWorkload workload = WorkloadRegistry.getWorkloadInstanceByName(name);
                WorkloadGeneration.LOGGER.info("Execute workload: {}", workload); // NOPMD

                workload.assembleWorkloadTasks().execute(config);

            } catch (final WorkloadNotCreatedException e) {
                WorkloadGeneration.LOGGER.info("Could not create workload '{}'", name);
                WorkloadGeneration.LOGGER.debug("Workload {}, Error (resuming): {}", name, e.getMessage());
            }
        }

        config.getDriver().quit();
        WorkloadGeneration.LOGGER.info("Workload execution finished");
    }

    private static void printAvailableWorkloads() {
        final Map<String, Class<? extends AbstractWorkload>> registeredWorkloads = WorkloadRegistry
                .getRegisteredWorkloads();

        String output = "Following workloads are registered:";

        for (final Entry<String, Class<? extends AbstractWorkload>> e : registeredWorkloads.entrySet()) {
            output += "\n";
            output += "Workload name: ";
            output += e.getKey();
            output += ", Corresponding class: ";
            output += e.getValue().getSimpleName();
        }
        System.out.println(output); // NOPMD -> creates output for command line
    }
}
