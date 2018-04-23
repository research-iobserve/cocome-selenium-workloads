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

import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.RandomGenerator;
import org.iobserve.selenium.configuration.Repetition;
import org.openqa.selenium.WebDriver;

/**
 * A basic task in a workload plan.
 *
 * @author Marc Adolf
 *
 */
public abstract class AbstractTask { // implements Consumer<IBehaviorModel> {

    private final WebDriver driver;
    private final String baseUrl;
    private final BehaviorModel model;

    public AbstractTask(final WebDriver driver, final String baseUrl, final BehaviorModel model) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.model = model;
    }

    /**
     *
     * @return The name of the task.
     */
    public abstract String getName();

    protected WebDriver getDriver() {
        return this.driver;
    }

    protected String getBaseUrl() {
        return this.baseUrl;
    }

    protected BehaviorModel getBehaviorModel() {
        return this.model;
    }

    public int getRepetitions() {
        final Repetition repetition = this.model.getRepetition();
        return RandomGenerator.getRandomNumber(repetition.getMin(), repetition.getMax());
    }

    public abstract void execute() throws ConfigurationException;
}
