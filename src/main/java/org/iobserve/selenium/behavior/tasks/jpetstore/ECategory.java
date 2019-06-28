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
package org.iobserve.selenium.behavior.tasks.jpetstore;

import java.util.Arrays;

import org.iobserve.selenium.behavior.properties.parameter.ListTaskParameter;

/**
 * Defines the clickable categories and information about their products.
 *
 * @author Marc Adolf
 *
 */
public enum ECategory {
    FISH("a[1]", "FI-SW-01", "FI-SW-02", "FI-FW-01", "FI-FW-02"), //
    DOGS("a[2]", "K9-BD-01", "K9-PO-02", "K9-DL-01", "K9-RT-01", "K9-RT-02", "K9-CW-01"), //
    REPTILES("a[3]", "RP-SN-01", "RP-LI-02"), //
    CATS("a[4]", "FL-DSH-01", "FL-DLH-02"), //
    BIRDS("a[5]", "AV-CB-01", "AV-SB-02");

    private String categoryString;
    private ListTaskParameter<String> products;

    private ECategory(final String categoryString, final String... products) {
        this.categoryString = categoryString;
        this.products = new ListTaskParameter<>(Arrays.asList(products), 0);
    }

    public String getCategoryString() {
        return this.categoryString;
    }

    public ListTaskParameter<String> getProducts() {
        return this.products;
    }
}