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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runnable used to represent one behavior execution.
 *
 * @author Reiner Jung
 *
 */
public class BehaviorModelRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BehaviorModelRunnable.class);

    private final ComposedBehavior behavior;

    /**
     * Create an runnable to execute the behavior.
     *
     * @param behavior
     *            behavior executor
     */
    public BehaviorModelRunnable(final ComposedBehavior behavior) {
        this.behavior = behavior;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        BehaviorModelRunnable.LOGGER.debug("Running behavior {}", this.behavior.getName());
        try {
            this.behavior.execute();
        } catch (final ConfigurationException e) {
            BehaviorModelRunnable.LOGGER.error("Behavior execution for '{}' failed. Cause: {}", this.behavior.getName(),
                    e.getMessage());
        }
        BehaviorModelRunnable.LOGGER.debug("done.");
    }

}
