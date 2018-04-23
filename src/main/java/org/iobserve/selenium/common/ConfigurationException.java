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
package org.iobserve.selenium.common;

/**
 * Thrown if problems during the creation of workloads arise.
 *
 * @author Marc Adolf
 *
 */
public class ConfigurationException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * New {@link ConfigurationException}.
     *
     * @param string
     *            The error message.
     */
    public ConfigurationException(final String string) {
        super(string);
    }

    public ConfigurationException(final Exception e) {
        super(e);
    }

}
