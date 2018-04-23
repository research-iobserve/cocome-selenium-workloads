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

import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Reiner Jung
 *
 */
public class BehaviorModelRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BehaviorModelRunner.class);

    private final BehaviorModel model;
    private final ComposedBehavior behavior;

    public BehaviorModelRunner(final BehaviorModel model, final ComposedBehavior behavior) {
        this.model = model;
        this.behavior = behavior;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        BehaviorModelRunner.LOGGER.debug("Running behavior {}", this.model.getName());
        try {
            this.behavior.execute();
        } catch (final ConfigurationException e) {
            BehaviorModelRunner.LOGGER.error("Behavior execution for '{}' failed. Cause: {}", this.model.getName(),
                    e.getMessage());
        }
        BehaviorModelRunner.LOGGER.debug("done.");
    }

}
