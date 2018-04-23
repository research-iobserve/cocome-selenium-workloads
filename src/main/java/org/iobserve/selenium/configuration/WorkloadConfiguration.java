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

    private String baseUrl;
    private String pathWebDriver;
    private List<Workload> workloads = new ArrayList<>();
    private Map<String, BehaviorModel> behaviors = new HashMap<>();

    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public final String getPathWebDriver() {
        return this.pathWebDriver;
    }

    public final void setPathWebDriver(final String pathWebDriver) {
        this.pathWebDriver = pathWebDriver;
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

}
