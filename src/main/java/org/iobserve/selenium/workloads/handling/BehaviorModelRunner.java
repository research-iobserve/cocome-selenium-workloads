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
package org.iobserve.selenium.workloads.handling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iobserve.selenium.tasks.AbstractTask;
import org.iobserve.selenium.workloads.config.IBehaviorModel;

/**
 * @author Reiner Jung
 *
 */
public class BehaviorModelRunner implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(BehaviorModelRunner.class);

    private final IBehaviorModel model;
    private final AbstractTask task;

    public BehaviorModelRunner(final IBehaviorModel model, final AbstractTask task) {
        this.model = model;
        this.task = task;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        BehaviorModelRunner.LOGGER.debug("Running behavior {}", this.model.getName());
        // this.task.accept(this.model);
        BehaviorModelRunner.LOGGER.debug("done.");
    }

}
