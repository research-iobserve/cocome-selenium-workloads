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

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a variable integer parameter with its range and the default value.
 *
 * @author Marc Adolf
 *
 */
public class VariableIntegerTaskParameter implements ITaskParameter<Integer> {

    private final int min;
    private final int max;
    private final int defaultValue;

    /**
     * Defines the boundaries and default value of an integer.
     *
     * @param min
     *            The minimum of the integer range.
     * @param max
     *            The maximum of the integer range.
     * @param defaultValue
     *            The default value, if no random parameter should be chosen.
     */
    public VariableIntegerTaskParameter(final int min, final int max, final int defaultValue) {
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.fuzzy.properties.ITaskParameter#getDefaultParameter()
     */
    @Override
    public Integer getDefaultParameter() {
        return this.defaultValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.fuzzy.properties.ITaskParameter#getFuzzyParameter()
     */
    @Override
    public Integer getFuzzyParameter() {
        // upper bound is exclusive -> +1
        return ThreadLocalRandom.current().nextInt(this.min, this.max + 1);
    }

    @Override
    public Integer getParameter(final Boolean fuzzy) {
        if (fuzzy) {
            return this.getFuzzyParameter();
        } else {
            return this.getDefaultParameter();
        }
    }

}
