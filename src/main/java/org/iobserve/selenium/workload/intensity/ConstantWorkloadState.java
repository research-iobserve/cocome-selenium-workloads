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
package org.iobserve.selenium.workload.intensity;

import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.ConstantWorkloadIntensity;

/**
 * @author Reiner Jung
 *
 */
public class ConstantWorkloadState implements IWorkloadState {

    private Long startTimestamp = null;
    private final long duration;
    private final long intensity;
    private long mark;
    private final long period = 1000; // one second
    private long contingent;
    private BehaviorModel behaviorModel;
    private long count;

    public ConstantWorkloadState(final ConstantWorkloadIntensity intensity) {
        this.duration = intensity.getDuration() * 1000;
        this.intensity = intensity.getSpawnPerSecond();
        this.count = 0;
    }

    @Override
    public void startWorkloadProfile(final long timestamp) {
        this.startTimestamp = timestamp;
        this.mark = timestamp;
        this.contingent = this.intensity;
    }

    @Override
    public boolean startBehavior(final long timestamp) {
        if (this.startTimestamp != null) {
            if (this.mark + this.period > timestamp) {
                if (this.contingent > 0) {
                    this.contingent--;
                    this.count++;
                    return true;
                } else {
                    return false;
                }
            } else if (this.isWorkloadProfileComplete(timestamp)) {
                return false;
            } else {
                do {
                    this.mark += this.period;
                    this.contingent += this.intensity;
                } while (this.mark < timestamp);
                this.contingent--;
                this.count++;
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isWorkloadProfileComplete(final long timestamp) {
        return this.startTimestamp + this.duration < timestamp;
    }

    @Override
    public void setBehavioModel(final BehaviorModel behaviorModel) {
        this.behaviorModel = behaviorModel;
    }

    @Override
    public BehaviorModel getBehaviorModel() {
        return this.behaviorModel;
    }

    @Override
    public long getNextTrigger(final long timestamp) {
        return timestamp + this.period;
    }

    @Override
    public long getCount() {
        return this.count;
    }

}
