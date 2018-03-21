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
package org.iobserve.selenium.tasks.fuzzy.properties.parameter;

/**
 *
 * Represents one variable parameter with its possible manifestations and the default value.
 *
 * @author Marc Adolf
 *
 * @param <T>
 *            The inner type of the parameter, e.g., Integer or String
 */
public interface ITaskParameter<T> {

    /**
     * Returns the default parameter for deterministic experiment executions.
     *
     * @return The default parameter.
     */
    T getDefaultParameter();

    /**
     * Gives a random value from the defined parameter range.
     *
     * @return A random value of this parameter.
     */
    T getFuzzyParameter();

    /**
     * Retrieves a value of this parameter depending if it should be fuzzy or not.
     *
     * @param fuzzy
     *            True, if the value should be fuzzy.
     * @return The computed value for this parameter.
     */
    T getParameter(Boolean fuzzy);

}
