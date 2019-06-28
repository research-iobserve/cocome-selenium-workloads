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

package org.iobserve.selenium.behavior.properties.parameter;

import java.util.List;

/**
 * Represents a list of possible values for one parameter.
 *
 * @author Marc Adolf
 *
 * @param <T>
 *            The type of the elements in the list.
 */
public class ListTaskParameter<T> implements ITaskParameter<T> {

    private final List<T> items;
    private int defaultPosition;

    /**
     * Creates a variable parameter that saves the possible values in a list.
     *
     * @param items
     *            The list of possible values.
     * @param defaultPosition
     *            The position in the list of the default value.
     */
    public ListTaskParameter(final List<T> items, final int defaultPosition) {
        this.items = items;
        this.defaultPosition = defaultPosition;
        if (this.defaultPosition > this.items.size() - 1) {
            this.defaultPosition = this.items.size() - 1;
        }
        if (this.defaultPosition < 0) {
            this.defaultPosition = 0;
        }

    }

    @Override
    public T getSelectedParameter() {
        return this.items.get(this.defaultPosition);
    }

    public List<T> getAllParameters() {
        return this.items;
    }

    public void setItemPosition(final int itemPosition) {
        this.defaultPosition = itemPosition;
    }

}
