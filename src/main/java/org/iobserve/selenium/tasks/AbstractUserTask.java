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

package org.iobserve.selenium.tasks;

import org.iobserve.selenium.workloads.config.WorkloadConfiguration;
import org.openqa.selenium.WebDriver;

/**
 * Cosmetic interface for a clear distinction between task of users and other (system) tasks.
 *
 *
 * @author Marc Adolf
 * @author Sören Henning
 *
 */
public abstract class AbstractUserTask {

    protected WorkloadConfiguration configuration;

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

    public WorkloadConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(final WorkloadConfiguration configuration) {
        this.configuration = configuration;
    }

}
