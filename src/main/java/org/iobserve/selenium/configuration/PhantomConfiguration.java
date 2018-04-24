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

/**
 * @author Reiner Jung
 *
 */
public class PhantomConfiguration {
    private String baseUrl;

    private String path;

    private long timeout; // in milliseconds

    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public final void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public final String getPath() {
        return this.path;
    }

    public final void setPath(final String path) {
        this.path = path;
    }

    public final long getTimeout() {
        return this.timeout;
    }

    public final void setTimeout(final long timeout) {
        this.timeout = timeout;
    }

}
