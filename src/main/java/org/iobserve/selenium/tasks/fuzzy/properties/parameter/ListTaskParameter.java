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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListTaskParameter<T> implements ITaskParameter<T> {

    private final List<T> items;
    private int defaultPosition;

    public ListTaskParameter(final List<T> items, final int defaultPosition) {
        this.items = items;
        this.defaultPosition = defaultPosition;
        if (this.defaultPosition > (this.items.size() - 1)) {
            this.defaultPosition = this.items.size() - 1;
        }
        if (this.defaultPosition < 0) {
            this.defaultPosition = 0;
        }

    }

    @Override
    public T getDefaultParameter() {

        return this.items.get(this.defaultPosition);
    }

    @Override
    public T getFuzzyParameter() {
        if (this.items.isEmpty()) {
            // maybe change in the future
            return null;
        }

        final int randomPosition = ThreadLocalRandom.current().nextInt(0, this.items.size() - 1);
        return this.items.get(randomPosition);
    }

    @Override
    public T getParameter(final Boolean isFuzzy) {
        if (isFuzzy) {
            return this.getFuzzyParameter();
        } else {
            return this.getDefaultParameter();
        }
    }

}
