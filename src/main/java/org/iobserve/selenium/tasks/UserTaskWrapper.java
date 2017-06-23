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

/**
 * Wraps {@link IUserTask UserTasks}. Enables the system to use func(config) and
 * func(config.getDriver(),config.getBaseUrl()) in the same way.
 *
 * @author Marc Adolf
 *
 */
public class UserTaskWrapper implements ISystemTask {

    private final IUserTask userTask;

    /**
     *
     * @param userTask
     *            The task that will be executed on accept().
     */
    public UserTaskWrapper(final IUserTask userTask) {
        this.userTask = userTask;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.ITask#accept(org.iobserve.selenium.workloads.config.
     * WorkloadConfiguration)
     */
    @Override
    public void accept(final WorkloadConfiguration config) {
        this.userTask.accept(config.getDriver(), config.getBaseUrl());
    }

    @Override
    public String getName() {
        return this.userTask.getName();
    }

}
