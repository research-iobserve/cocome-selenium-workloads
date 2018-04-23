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

package org.iobserve.selenium.behavior.tasks;

import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.WorkloadConfiguration;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for the distinction between task of users and other (system) tasks. The
 * {@link WorkloadConfiguration configuration} is saved as a class member.
 *
 * @author Marc Adolf
 * @author Sören Henning
 *
 */
public abstract class AbstractUserTask {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserTask.class);

    protected BehaviorModel model;

    /**
     * Executes the defined task.
     *
     * @param driver
     *            the driver for the web browser.
     * @param baseUrl
     *            the URL where the side is hosted.
     */
    public abstract void executeTask(WebDriver driver, String baseUrl);

    /**
     *
     * @return The name of the task.
     */
    public abstract String getName();

    public BehaviorModel getBehaviorModel() {
        return this.model;
    }

    public void setBehaviorModel(final BehaviorModel model) {
        this.model = model;
    }

}
