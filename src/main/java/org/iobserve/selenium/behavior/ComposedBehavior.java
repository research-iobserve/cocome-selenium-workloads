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
package org.iobserve.selenium.behavior;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.TaskRegistry;
import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.common.RandomGenerator;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.Repetition;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Execution processor for a composed behavior.
 *
 * @author Reiner Jung
 *
 */
public class ComposedBehavior {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComposedBehavior.class);

    private final WebDriver driver;
    private final String baseUrl;
    private final BehaviorModel model;

    private final double activityDelay;

    private final String name;

    /**
     * Composed behavior executor.
     *
     * @param driver
     *            web driver to use
     * @param baseUrl
     *            base url
     * @param activityDelay
     *            activity delay
     * @param model
     *            behavior model to execute
     */
    public ComposedBehavior(final WebDriver driver, final String baseUrl, final double activityDelay,
            final BehaviorModel model) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.model = model;
        this.activityDelay = activityDelay;

        this.name = model.getName();
    }

    public String getName() {
        return this.name;
    }

    protected WebDriver getDriver() {
        return this.driver;
    }

    protected String getBaseUrl() {
        return this.baseUrl;
    }

    protected BehaviorModel getBehaviorModel() {
        return this.model;
    }

    /**
     * Returns the number of repetitions for this behavior.
     *
     * @return repetition
     */
    public int getRepetitions() {
        final Repetition repetition = this.model.getRepetition();
        return RandomGenerator.getRandomNumber(repetition.getMin(), repetition.getMax());
    }

    /**
     * Execute the behavior.
     *
     * @throws ConfigurationException
     *             on configuration errors
     */
    public void execute() throws ConfigurationException {
        ComposedBehavior.LOGGER.debug("behavior {} [{}]: start behavior", this.name, Thread.currentThread().getId());
        /** start with new session. */
        this.getDriver().manage().deleteAllCookies();
        this.iterateBehavior(this.getBehaviorModel(),
                this.getActivityDelay(this.activityDelay, this.model.getActivityDelay()));
        ComposedBehavior.LOGGER.debug("behavior {} [{}]: stop behavior", this.name, Thread.currentThread().getId());
    }

    private void iterateBehavior(final BehaviorModel localModel, final double localActivityDelay)
            throws ConfigurationException {
        ComposedBehavior.LOGGER.debug("behavior {} [{}]: iterating", this.name, Thread.currentThread().getId());
        for (final BehaviorModel behavior : this.getBehaviorModel().getSubbehaviors()) {
            ComposedBehavior.LOGGER.debug("behavior {} [{}]: sub {}", this.name, Thread.currentThread().getId(),
                    behavior.getName());
            if (behavior.getRepetition() != null) {
                final int repetitions = RandomGenerator.getRandomNumber(behavior.getRepetition().getMin(),
                        behavior.getRepetition().getMax());
                ComposedBehavior.LOGGER.debug("behavior {} [{}]: sub {} [{},{}]", this.name,
                        Thread.currentThread().getId(), behavior.getName(), behavior.getRepetition().getMin(),
                        behavior.getRepetition().getMax());
                for (int i = 0; i < repetitions; i++) {
                    ComposedBehavior.LOGGER.debug("behavior {} [{}]: sub {} i {}", this.name,
                            Thread.currentThread().getId(), behavior.getName(), i);
                    this.executeBehavior(behavior, localActivityDelay);
                }
            } else {
                this.executeBehavior(behavior, localActivityDelay);
            }
        }
        ComposedBehavior.LOGGER.debug("behavior {} [{}]: end iterating", this.name, Thread.currentThread().getId());
    }

    private void executeBehavior(final BehaviorModel behavior, final double localActivityDelay)
            throws ConfigurationException {
        if (behavior.getSubbehaviors().isEmpty()) {
            final AbstractTask type = TaskRegistry.getWorkloadInstanceByName(behavior.getName(),
                    behavior.getParameters());
            ComposedBehavior.LOGGER.debug("behavior {} [{}]: delay {} task {}", this.name,
                    Thread.currentThread().getId(),
                    this.getActivityDelay(localActivityDelay, behavior.getActivityDelay()), type.getName());

            try {
                type.executeTask(this.getDriver(), this.getBaseUrl(),
                        (long) (this.getActivityDelay(localActivityDelay, behavior.getActivityDelay()) * 1000));
            } catch (final NoSuchSessionException ex) {
                ComposedBehavior.LOGGER.error("behavior {} [{}]: Session is missing in task {}.", this.name,
                        Thread.currentThread().getId(), type.getName());
            }
        } else {
            this.iterateBehavior(behavior, localActivityDelay);
        }
    }

    private double getActivityDelay(final double parentActivityDelay, final Double localActivityDelay) {
        if (localActivityDelay != null) {
            return localActivityDelay;
        } else {
            return parentActivityDelay;
        }
    }

}
