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
package org.iobserve.selenium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.common.CommandlineArguments;
import org.iobserve.selenium.workloads.config.ConstantWorkloadIntensity;
import org.iobserve.selenium.workloads.config.IWorkloadIntensity;
import org.iobserve.selenium.workloads.config.Workload;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;
import org.iobserve.selenium.workloads.handling.AbstractWorkload;
import org.iobserve.selenium.workloads.handling.BehaviorModelRunner;
import org.iobserve.selenium.workloads.intensity.ConstantWorkloadState;
import org.iobserve.selenium.workloads.intensity.IWorkloadState;
import org.iobserve.selenium.workloads.registry.WorkloadRegistry;

/**
 * Generates workloads for CoCoME. Designed to be used with the iObserve Experiment. Uses Selenium
 * to execute the workloads.
 *
 * @author Christoph Dornieden
 * @author Marc Adolf
 * @author Reiner Jung
 */
public final class WorkloadGenerationMain {

    private static final Logger LOGGER = LogManager.getLogger(WorkloadGenerationMain.class);

    /**
     * Empty constructor.
     */
    private WorkloadGenerationMain() {
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

        if (CommandlineArguments.getPrintWorkloads()) {
            WorkloadGenerationMain.printAvailableWorkloads();
        } else {

            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            WorkloadConfiguration configuration;
            try {
                configuration = mapper.readValue(arguments.getConfigurationFile(), WorkloadConfiguration.class);
                if (configuration.getBaseUrl() == null) {
                    configuration.setBaseUrl(arguments.getBaseUrl());
                }
                if (configuration.getPathWebDriver() == null) {
                    configuration.setPathWebDriver(arguments.getPathWebDriver());
                }

                /** evaluate configuration. */
                if (configuration.getWorkloads() != null) {
                    WorkloadGenerationMain.LOGGER.info("Trying to execute following workloads: {}",
                            configuration.getWorkloads().keySet());
                    WorkloadGenerationMain.runWorkloads(WorkloadGenerationMain.setupWorkloads(configuration));
                }

                WorkloadGenerationMain.LOGGER.info("Workload execution finished.");
            } catch (final IOException e) {
                WorkloadGenerationMain.LOGGER.error("Execution failed due to {} error.", e.getMessage());
            }
        }
    }

    private static List<IWorkloadState> setupWorkloads(final WorkloadConfiguration configuration) {
        final long presentTime = new Date().getTime(); // time in ms

        final List<IWorkloadState> workloads = new ArrayList<>();

        for (final Workload workload : configuration.getWorkloads().values()) {
            final IWorkloadIntensity intensity = workload.getIntensity();
            if (intensity instanceof ConstantWorkloadIntensity) {
                final IWorkloadState state = new ConstantWorkloadState((ConstantWorkloadIntensity) intensity);
                state.startWorkloadProfile(presentTime);
                state.setBehavioModel(workload.getBehaviorModel());
                workloads.add(state);
            } else {
                WorkloadGenerationMain.LOGGER.error("Unknown workload intensity type: {}",
                        intensity.getClass().getCanonicalName());
            }
        }

        return workloads;
    }

    private static void runWorkloads(final List<IWorkloadState> workloads) {
        final ExecutorService executor = Executors.newFixedThreadPool(10);

        boolean repeat = false;

        do {
            repeat = false;
            long trigger = Long.MAX_VALUE;
            for (final IWorkloadState state : workloads) {
                WorkloadGenerationMain.LOGGER.debug("work on {}", state.getBehaviorModel().getName());
                final long presentTime = new Date().getTime();
                WorkloadGenerationMain.LOGGER.debug("time {}", presentTime);
                if (!state.isWorkloadProfileComplete(presentTime)) {
                    while (state.startBehavior(presentTime)) {
                        executor.submit(new BehaviorModelRunner(state.getBehaviorModel(), null));
                    }
                    final long potentialTrigger = state.getNextTrigger(presentTime);
                    if (trigger > potentialTrigger) {
                        trigger = potentialTrigger;
                    }
                    repeat = true;
                }
            }
            try {
                if (repeat) {
                    Thread.sleep(trigger - new Date().getTime());
                }
            } catch (final InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (repeat);

        for (final IWorkloadState state : workloads) {
            WorkloadGenerationMain.LOGGER.debug("{} had {} executions", state.getBehaviorModel().getName(),
                    state.getCount());
        }

        /*
         * for (final String name : workloads) { try { final AbstractWorkload workload =
         * WorkloadRegistry.getWorkloadInstanceByName(name);
         * WorkloadGenerationMain.LOGGER.info("Execute workload: {}", workload); // NOPMD
         * workload.assembleWorkloadTasks().execute(config); } catch (final
         * WorkloadNotCreatedException e) {
         * WorkloadGenerationMain.LOGGER.info("Could not create workload '{}'", name);
         * WorkloadGenerationMain.LOGGER.debug("Workload {}, Error (resuming): {}", name,
         * e.getMessage()); } } }
         */

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
