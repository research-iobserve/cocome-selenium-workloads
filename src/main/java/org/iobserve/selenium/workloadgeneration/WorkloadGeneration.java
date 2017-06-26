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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.common.CommandlineArguments;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.registry.WorkloadNotCreatedException;
import org.iobserve.selenium.workloads.registry.WorkloadRegistry;

import com.beust.jcommander.JCommander;

/**
 * Generates workloads for CoCoME. Designed to be used with the iObserve Experiment. Uses Selenium
 * to execute the workloads.
 *
 * @author Christoph Dornieden
 * @author Marc Adolf
 */
public class WorkloadGeneration {

    /**
     * Executes the given workloads with the given parameters.
     *
     * @param args
     *            The parameters to be parsed by {@link CommandlineArguments JCommander}.
     */
    public static void main(final String[] args) {

        final CommandlineArguments arguments = new CommandlineArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        final Logger logger = LogManager.getLogger(WorkloadGeneration.class);

        // logger.debug("Webdriver path: " + arguments.getPathPhantomjs());
        // logger.debug("Base URL: " + arguments.getBaseUrl());
        // logger.debug("Number of runs: " + arguments.getNumberOfRuns());
        logger.debug("Workloads to execute: " + arguments.getWorkloads());

        logger.info("Creating the configuration for the workload with the webdriver path: "
                + arguments.getPathPhantomjs() + ", base URL: " + arguments.getBaseUrl() + " and will repeating it "
                + arguments.getNumberOfRuns() + " times");

        final WorkloadConfiguration config = new WorkloadConfiguration(arguments.getBaseUrl(),
                arguments.getNumberOfRuns(), arguments.getPathPhantomjs());

        final List<String> workloads = arguments.getWorkloads();

        /*
         * The registry have to be filled beforehand. Better Ideas are welcome.
         */
        WorkloadRegistry.fillRegistry();

        logger.info("Trying to execute following workloads: " + workloads.toString());

        for (final String name : workloads) {
            try {
                final AbstractWorkload workload = WorkloadRegistry.getWorkloadInstanceByName(name);
                workload.assembleWorkloadTasks(config).execute(config);

            } catch (final WorkloadNotCreatedException e) {
                logger.info("Could not create workload '" + name + "'");
                logger.debug("Workload " + name + ", Error (resuming): " + e.getMessage(), e);
            }
        }

        logger.info("Workload execution finished");
    }
}
