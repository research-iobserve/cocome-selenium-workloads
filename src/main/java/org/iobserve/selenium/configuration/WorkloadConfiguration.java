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
package org.iobserve.selenium.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains basic informations for a {@link BehaviorBuilder}.
 *
 * @author Marc Adolf
 *
 */
public class WorkloadConfiguration {

    private WebDriverConfiguration webDriverConfiguration;

    private List<Workload> workloads = new ArrayList<>();

    private Map<String, BehaviorModel> behaviors = new HashMap<>();

    private double activityDelay; // in seconds

    private Integer threadLimit;

    public final double getActivityDelay() {
        return this.activityDelay;
    }

    public final void setActivityDelay(final double activityDelay) {
        this.activityDelay = activityDelay;
    }

    public final WebDriverConfiguration getWebDriverConfiguration() {
        return this.webDriverConfiguration;
    }

    public final void setWebDriverConfiguration(final WebDriverConfiguration webDriverConfiguration) {
        this.webDriverConfiguration = webDriverConfiguration;
    }

    public final List<Workload> getWorkloads() {
        return this.workloads;
    }

    public final void setWorkloads(final List<Workload> workloads) {
        this.workloads = workloads;
    }

    public final Map<String, BehaviorModel> getBehaviors() {
        return this.behaviors;
    }

    public void setBehaviors(final Map<String, BehaviorModel> behaviors) {
        this.behaviors = behaviors;
    }

    public Integer getThreadLimit() {
        return this.threadLimit;
    }

    public void setThreadLimit(final Integer threadLimit) {
        this.threadLimit = threadLimit;
    }

}
