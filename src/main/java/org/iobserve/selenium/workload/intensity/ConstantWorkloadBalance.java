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

import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.configuration.BehaviorModel;
import org.iobserve.selenium.configuration.ConstantWorkloadIntensity;

/**
 * Computes if a new behavior model should run for a given time stamp and keeps track of all started
 * instances.
 *
 * @author Reiner Jung
 *
 */
public class ConstantWorkloadBalance implements IWorkloadBalance {

    private static final long TIME_FRAME_FOR_INTENSITY = 1000; // one second
    private Long startTimestamp;
    private final long[] durations;
    private final long[] startMark;
    private final double intensity;
    private long mark;
    private double contingent;
    private BehaviorModel behaviorModel;
    private long count;
    private int startSelection;
    private long endPoint;
    private boolean wait = true;

    /**
     * Create new constant workload balance.
     *
     * @param intensity
     *            constant workload intensity to use
     * @throws ConfigurationException
     *             on configuration errors
     */
    public ConstantWorkloadBalance(final ConstantWorkloadIntensity intensity) throws ConfigurationException {
        if (intensity.getDelays() == null) {
            this.durations = new long[1];
            this.startMark = new long[1];
            this.startMark[0] = 0;
            this.durations[0] = intensity.getDurations()[0] * 1000;
            this.endPoint = this.durations[0];

            this.intensity = intensity.getSpawnPerSecond();
            this.count = 0;
        } else if (intensity.getDurations().length == intensity.getDelays().length) {
            this.durations = new long[intensity.getDurations().length];
            this.startMark = new long[intensity.getDurations().length];
            this.endPoint = 0;

            for (int i = 0; i < intensity.getDurations().length; i++) {
                this.durations[i] = intensity.getDurations()[i] * 1000;
                this.startMark[i] = this.endPoint;
                this.endPoint += this.durations[i] + intensity.getDelays()[i] * 1000;
            }
            this.intensity = intensity.getSpawnPerSecond();
            this.count = 0;
        } else {
            throw new ConfigurationException("number of delays does not match number of durations.");
        }
        this.startSelection = 0;
    }

    @Override
    public void startWorkloadProfile(final long timestamp) {
        this.startTimestamp = timestamp;
        this.mark = timestamp;
        this.contingent = this.intensity;
        this.startSelection = 0;
    }

    @Override
    public boolean startBehavior(final long timestamp) {
        if (this.startTimestamp != null) {
            if (this.startMark[this.startSelection] + this.startTimestamp <= timestamp
                    && this.startMark[this.startSelection] + this.startTimestamp
                            + this.durations[this.startSelection] > timestamp) {
                this.wait = false;
            } else {
                if (!this.wait) {
                    // switch from delay to duration
                    this.mark = this.startMark[this.startSelection] + this.startTimestamp;
                }
                this.wait = true;
                return false;
            }

            while (this.mark < timestamp) {
                this.contingent += this.intensity;
                this.mark += ConstantWorkloadBalance.TIME_FRAME_FOR_INTENSITY;
            }

            if (this.contingent > 0) {
                this.contingent--;
                this.count++;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean isWorkloadProfileComplete(final long timestamp) {
        return this.startTimestamp + this.endPoint < timestamp;
    }

    @Override
    public void setBehaviorModel(final BehaviorModel behaviorModel) {
        this.behaviorModel = behaviorModel;
    }

    @Override
    public BehaviorModel getBehaviorModel() {
        return this.behaviorModel;
    }

    @Override
    public long getNextTrigger(final long timestamp) {
        return timestamp + ConstantWorkloadBalance.TIME_FRAME_FOR_INTENSITY;
    }

    @Override
    public long getCount() {
        return this.count;
    }

}
