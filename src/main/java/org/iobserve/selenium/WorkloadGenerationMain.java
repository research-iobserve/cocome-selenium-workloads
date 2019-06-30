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
import java.util.concurrent.atomic.AtomicInteger;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.iobserve.selenium.behavior.BehaviorModelRunnable;
import org.iobserve.selenium.behavior.ComposedBehavior;
import org.iobserve.selenium.behavior.IDriverFactory;
import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.TaskRegistry;
import org.iobserve.selenium.common.CommandlineArguments;
import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.ConstantWorkloadIntensity;
import org.iobserve.selenium.configuration.IWorkloadIntensity;
import org.iobserve.selenium.configuration.Workload;
import org.iobserve.selenium.configuration.WorkloadConfiguration;
import org.iobserve.selenium.workload.intensity.ConstantWorkloadBalance;
import org.iobserve.selenium.workload.intensity.IWorkloadBalance;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates workloads for CoCoME. Designed to be used with the iObserve Experiment. Uses Selenium
 * to execute the workloads.
 *
 * @author Christoph Dornieden
 * @author Marc Adolf
 * @author Reiner Jung
 */
public final class WorkloadGenerationMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadGenerationMain.class);

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

            final WorkloadConfiguration configuration;
            try {
                configuration = mapper.readValue(arguments.getConfigurationFile(), WorkloadConfiguration.class);
                if (configuration.getWebDriverConfiguration() == null) {
                    WorkloadGenerationMain.LOGGER.error("Missing webdriver configuration");
                    return;
                }

                if (arguments.getBaseUrl() != null) {
                    configuration.getWebDriverConfiguration().setBaseUrl(arguments.getBaseUrl());
                }
                if (arguments.getPathWebDriver() != null) {
                    configuration.getWebDriverConfiguration().setDriver(arguments.getPathWebDriver());
                }

                /** evaluate configuration. */
                if (configuration.getWorkloads() != null) {
                    final String names = configuration.getWorkloads().stream().map(w -> w.getName())
                            .reduce((a, b) -> a + ", " + b).get();
                    WorkloadGenerationMain.LOGGER.info("Executing workloads: {}", names);
                    WorkloadGenerationMain.runWorkloads(WorkloadGenerationMain.setupWorkloads(configuration),
                            configuration);
                } else {
                    WorkloadGenerationMain.LOGGER.error("Configuration does not contain any workloads.");
                }

                WorkloadGenerationMain.LOGGER.info("Workload execution finished.");
            } catch (final IOException e) {
                WorkloadGenerationMain.LOGGER.error("Execution failed due to {} error.", e.getMessage());
            }
        }
    }

    private static List<IWorkloadBalance> setupWorkloads(final WorkloadConfiguration configuration) {
        final long presentTime = new Date().getTime(); // time in ms

        final List<IWorkloadBalance> workloads = new ArrayList<>();

        for (final Workload workload : configuration.getWorkloads()) {
            final IWorkloadIntensity intensity = workload.getIntensity();
            if (intensity instanceof ConstantWorkloadIntensity) {
                try {
                    final IWorkloadBalance state = new ConstantWorkloadBalance((ConstantWorkloadIntensity) intensity);
                    state.startWorkloadProfile(presentTime);

                    final BehaviorModel behaviorModel = configuration.getBehaviors().get(workload.getName());
                    if (behaviorModel == null) {
                        WorkloadGenerationMain.LOGGER.error("Behavior {} cannot be found.", workload.getName());
                    }
                    state.setBehaviorModel(behaviorModel);
                    workloads.add(state);
                } catch (final ConfigurationException e) {
                    WorkloadGenerationMain.LOGGER.error("Configuration error in workload intensity for Behavior {}.",
                            workload.getName(), e);
                }
            } else {
                WorkloadGenerationMain.LOGGER.error("Unknown workload intensity type: {}",
                        intensity.getClass().getCanonicalName());
            }
        }

        return workloads;
    }

    private static void runWorkloads(final List<IWorkloadBalance> workloads,
            final WorkloadConfiguration configuration) {

        try {
            final Class<?>[] parameters = null;
            final IDriverFactory driverFactory = InstantiationFactory.create(IDriverFactory.class,
                    configuration.getWebDriverConfiguration().getType(), parameters);
            WorkloadGenerationMain.runWorkloadsWithDriver(driverFactory, workloads, configuration);
        } catch (final ConfigurationException e) {
            WorkloadGenerationMain.LOGGER.error("Instanitation error for the driver {}",
                    configuration.getWebDriverConfiguration().getType());
        }
    }

    /**
     * run workload with the specified driver.
     *
     * @param driverFactory
     *            the driver object
     * @param workloads
     *            the workload
     * @param configuration
     *            the configuration
     */
    private static void runWorkloadsWithDriver(final IDriverFactory driverFactory,
            final List<IWorkloadBalance> workloads, final WorkloadConfiguration configuration) {
        final ExecutorService executor = Executors.newFixedThreadPool(10);
        final AtomicInteger activeUsers = new AtomicInteger(0);

        boolean repeat = false;

        do { /** repeat starting new workloads as long as the workload profile suggests it. */
            repeat = false;
            long trigger = Long.MAX_VALUE;
            for (final IWorkloadBalance state : workloads) {
                final long presentTime = new Date().getTime();
                if (!state.isWorkloadProfileComplete(presentTime)) {
                    while (state.startBehavior(presentTime)) {
                        final WebDriver driver = driverFactory
                                .createNewDriver(configuration.getWebDriverConfiguration());
                        executor.submit(new BehaviorModelRunnable(
                                new ComposedBehavior(driver, configuration.getWebDriverConfiguration().getBaseUrl(),
                                        configuration.getActivityDelay(), state.getBehaviorModel()),
                                activeUsers));
                    }
                    /** determine the lowest next trigger time for the loop. */
                    final long potentialTrigger = state.getNextTrigger(presentTime);
                    if (trigger > potentialTrigger) {
                        trigger = potentialTrigger;
                    }
                    repeat = true;
                }
            }
            try {
                if (repeat) {
                    Thread.sleep(WorkloadGenerationMain.calculateDelay(trigger, new Date().getTime()));
                }
            } catch (final InterruptedException e) {
                WorkloadGenerationMain.LOGGER.info("Sleep interrupted in main workload scheduler.");
            }
        } while (repeat);

        try {
            while (activeUsers.get() > 0) {
                WorkloadGenerationMain.LOGGER.debug("Still running users {}", activeUsers.get());
                Thread.sleep(1000);
            }
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WorkloadGenerationMain.LOGGER.debug("Run complete. Waiting for executor shutdown.");
        executor.shutdown();

        for (final IWorkloadBalance state : workloads) {
            WorkloadGenerationMain.LOGGER.info("{} had {} executions", state.getBehaviorModel().getName(),
                    state.getCount());
        }
    }

    private static long calculateDelay(final long trigger, final long time) {
        final long result = trigger - time;
        if (result > 0) {
            return result;
        } else {
            return 0;
        }
    }

    private static void printAvailableWorkloads() {
        final Map<String, Class<? extends AbstractTask>> registeredWorkloads = TaskRegistry.getRegisteredWorkloads();

        String output = "Following workloads are registered:";

        for (final Entry<String, Class<? extends AbstractTask>> e : registeredWorkloads.entrySet()) {
            output += "\n";
            output += "Task name: ";
            output += e.getKey();
            output += ", Corresponding class: ";
            output += e.getValue().getCanonicalName();
        }
        System.out.println(output); // NOPMD -> creates output for command line
    }
}
