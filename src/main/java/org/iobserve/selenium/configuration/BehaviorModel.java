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
package org.iobserve.selenium.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration of a behavior including the repetition of its sub-behaviors, its parameters, and
 * the configuration of the sub-behaviors.
 *
 * @author Reiner Jung
 *
 */
public class BehaviorModel {

    String name;

    Repetition repetition;

    Double activityDelay; // in seconds

    Map<String, Object> parameters = new HashMap<>();

    List<BehaviorModel> subbehaviors = new ArrayList<>();

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Repetition getRepetition() {
        return this.repetition;
    }

    public final void setRepetition(final Repetition repetition) {
        this.repetition = repetition;
    }

    public final Map<String, Object> getParameters() {
        return this.parameters;
    }

    public final void setParameters(final Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public final List<BehaviorModel> getSubbehaviors() {
        return this.subbehaviors;
    }

    public final void setSubbehaviors(final List<BehaviorModel> subbehaviors) {
        this.subbehaviors = subbehaviors;
    }

    public void setActivityDelay(final double activityDelay) {
        this.activityDelay = activityDelay;
    }

    public Double getActivityDelay() {
        return this.activityDelay;
    }

}
