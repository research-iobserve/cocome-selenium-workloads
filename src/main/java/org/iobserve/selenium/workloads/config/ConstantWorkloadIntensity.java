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
package org.iobserve.selenium.workloads.config;

/**
 * @author Reiner Jung
 *
 */
public class ConstantWorkloadIntensity implements IWorkloadIntensity {

    private int spawnPerSecond;
    private long duration; // time in seconds
    private String name;

    public ConstantWorkloadIntensity() {
        // empty, bean constructor
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getSpawnPerSecond() {
        return this.spawnPerSecond;
    }

    public final void setSpawnPerSecond(final int spawnPerSecond) {
        this.spawnPerSecond = spawnPerSecond;
    }

    public final long getDuration() {
        return this.duration;
    }

    public final void setDuration(final long duration) {
        this.duration = duration;
    }

}
