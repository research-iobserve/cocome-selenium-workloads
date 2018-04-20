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
package org.iobserve.selenium.workloads.config;

import java.util.HashMap;
import java.util.Map;

import org.iobserve.selenium.workloads.handling.WorkloadPlan;

/**
 * Contains basic informations for a {@link WorkloadPlan}.
 *
 * @author Marc Adolf
 *
 */
public class WorkloadConfiguration {

    private String baseUrl;
    private String pathWebDriver;
    private Map<String, Workload> workloads = new HashMap<>();

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

    public final Map<String, Workload> getWorkloads() {
        return this.workloads;
    }

    public final void setWorkloads(final Map<String, Workload> workloads) {
        this.workloads = workloads;
    }

}
