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
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for the distinction between task of users.
 *
 * @author Marc Adolf
 * @author Sören Henning
 *
 */
public abstract class AbstractTask {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTask.class);

    protected BehaviorModel behaviorModel;

    /**
     * Executes the defined task.
     *
     * @param driver
     *            the driver for the web browser.
     * @param baseUrl
     *            the URL where the side is hosted.
     * @param activityDelay
     *            delay between actions in milliseconds.
     */
    public abstract void executeTask(WebDriver driver, String baseUrl, long activityDelay);

    /**
     *
     * @return The name of the task.
     */
    public abstract String getName();

    public BehaviorModel getBehaviorModel() {
        return this.behaviorModel;
    }

    public void setBehaviorModel(final BehaviorModel behaviorModel) {
        this.behaviorModel = behaviorModel;
    }

    /**
     * Delay the execution by @{link activityDelay} milliseconds.
     *
     * @param activityDelay
     *            sleep time
     */
    protected void sleep(final long activityDelay) {
        try {
            AbstractTask.LOGGER.debug("Sleep {} ms.", activityDelay);
            Thread.sleep(activityDelay);
        } catch (final InterruptedException e) {
            AbstractTask.LOGGER.info("Thread sleep was interrupted.");
        }
    }

}
