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
package org.iobserve.selenium.tasks.systemtasks;

import org.iobserve.selenium.tasks.ISystemTask;
import org.iobserve.selenium.workloads.config.WorkloadConfiguration;

/**
 *
 * Creates a new session through replacing the old driver in the given
 * {@link WorkloadConfiguration}.
 *
 * @author Marc Adolf
 *
 */
public class CreateNewSessionTask implements ISystemTask {
    private static final String NAME = "Create new session";

    @Override
    public void accept(final WorkloadConfiguration t) {
        t.newSession();

    }

    @Override
    public String getName() {
        return CreateNewSessionTask.NAME;
    }

}
