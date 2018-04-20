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

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Reiner Jung
 *
 */
public class Workload {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
    IBehaviorModel behaviorModel;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
    IWorkloadIntensity intensity;
    String name;

    public Workload() {
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final IBehaviorModel getBehaviorModel() {
        return this.behaviorModel;
    }

    public final void setBehaviorModel(final BehaviorModel behaviorModel) {
        this.behaviorModel = behaviorModel;
    }

    public final IWorkloadIntensity getIntensity() {
        return this.intensity;
    }

    public final void setIntensity(final IWorkloadIntensity intensity) {
        this.intensity = intensity;
    }

}
