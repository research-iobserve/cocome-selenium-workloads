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

import org.iobserve.selenium.tasks.fuzzy.properties.parameter.VariableIntegerTaskParameter;
import org.iobserve.selenium.workloads.config.IBehaviorModel;

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

    /**
     * Normal, nonfuzzy user task. Executed exactly one time.
     *
     * @param userTask
     *            The task that will be executed on accept().
     */
    public UserTaskWrapper(final AbstractUserTask userTask) {
        this.userTask = userTask;
        this.maxRepeat = new VariableIntegerTaskParameter(1, 1, 1);
    }

    /**
     * User task with potential fuzzy behavior (repetitions). If fuzzy, executed at least one time.
     *
     * @param userTask
     *            The task that will be executed on accept().
     * @param maxFuzzyRepeats
     *            The upper bound of the possible repetitions. Set to 1 if < 1.
     *
     */
    public UserTaskWrapper(final AbstractUserTask userTask, final int maxFuzzyRepeats) {
        this.userTask = userTask;
        int max = maxFuzzyRepeats;
        if (max < 1) {
            max = 1;
        }
        this.maxRepeat = new VariableIntegerTaskParameter(1, max, 1);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.ITask#accept(org.iobserve.selenium.workloads.config.
     * WorkloadConfiguration)
     */
    @Override
    public void accept(final IBehaviorModel model) {
        this.userTask.setBehaviorModel(model);
        this.userTask.executeTask(model.getDriver(), model.getBaseUrl());
    }

    @Override
    public String getName() {
        return this.userTask.getName();
    }

    @Override
    public int getRepetitions(final Boolean fuzzy) {
        return this.maxRepeat.getParameter(fuzzy);
    }

}
