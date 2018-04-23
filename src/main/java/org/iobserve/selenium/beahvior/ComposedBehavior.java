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
package org.iobserve.selenium.beahvior;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.AbstractUserTask;
import org.iobserve.selenium.behavior.tasks.TaskRegistry;
import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.RandomGenerator;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Reiner Jung
 *
 */
public class ComposedBehavior extends AbstractTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComposedBehavior.class);

    public ComposedBehavior(final WebDriver driver, final String baseUrl, final BehaviorModel model) {
        super(driver, baseUrl, model);
    }

    private String name;

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractTask#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void execute() throws ConfigurationException {
        /** start with new session. */
        ComposedBehavior.LOGGER.debug("execute new session");
        this.getDriver().manage().deleteAllCookies();
        ComposedBehavior.LOGGER.debug("execute model");
        this.iterateBehavior(this.getBehaviorModel());
    }

    private void iterateBehavior(final BehaviorModel model) throws ConfigurationException {
        ComposedBehavior.LOGGER.debug("executing {}", model.getName());
        for (final BehaviorModel behavior : this.getBehaviorModel().getSubbehaviors()) {
            for (int i = 1; i < RandomGenerator.getRandomNumber(behavior.getRepetition().getMin(),
                    behavior.getRepetition().getMax()); i++) {
                if (behavior.getSubbehaviors().isEmpty()) {
                    final AbstractUserTask type = TaskRegistry.getWorkloadInstanceByName(behavior.getName(),
                            behavior.getParameters());
                    type.executeTask(this.getDriver(), this.getBaseUrl());
                } else {
                    this.iterateBehavior(behavior);
                }
            }
        }
    }

}
