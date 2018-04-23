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

import org.iobserve.selenium.behavior.properties.parameter.VariableIntegerTaskParameter;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.openqa.selenium.WebDriver;

/**
 * Wraps {@link AbstractUserTask UserTasks}. Enables the system to use func(config) and
 * func(config.getDriver(),config.getBaseUrl()) in the same way.
 *
 * @author Marc Adolf
 *
 */
public class UserTaskWrapper extends AbstractTask {

    private final AbstractUserTask userTask;
    private final VariableIntegerTaskParameter maxRepeat;
    private WebDriver driver;
    private String baseUrl;

    /**
     * Normal, nonfuzzy user task. Executed exactly one time.
     *
     * @param userTask
     *            The task that will be executed on accept().
     */
    public UserTaskWrapper(final AbstractUserTask userTask, final WebDriver driver, final String baseUrl,
            final BehaviorModel model) {
        super(driver, baseUrl, model);
        this.userTask = userTask;
        this.maxRepeat = new VariableIntegerTaskParameter(1, 1, 1);
    }

    @Override
    public void execute() {
        this.userTask.setBehaviorModel(this.getBehaviorModel());
        this.userTask.executeTask(this.driver, this.baseUrl);
    }

    @Override
    public String getName() {
        return this.userTask.getName();
    }

    public int getRepetitions(final Boolean fuzzy) {
        return this.maxRepeat.getParameter();
    }

}
