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
package org.iobserve.selenium.workloads.intensity;

import org.iobserve.selenium.workloads.config.IBehaviorModel;

/**
 * @author Reiner Jung
 *
 */
public interface IWorkloadState {

    /**
     * Remember the time when this workload was started.
     *
     * @param timestamp
     *            start timestamp
     */
    void startWorkloadProfile(final long timestamp);

    /**
     * Indicates whether a behavior should be started.
     *
     * @return returns true if a behavior should run
     */
    boolean startBehavior(final long timestamp);

    /**
     * Indicates whether the profile should be still active.
     *
     * @return true is the profile is complete
     */
    boolean isWorkloadProfileComplete(final long timestamp);

    void setBehavioModel(IBehaviorModel behaviorModel);

    IBehaviorModel getBehaviorModel();

    long getNextTrigger(final long timestamp);

    public long getCount();
}
