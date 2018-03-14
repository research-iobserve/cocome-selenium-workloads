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
package org.iobserve.selenium.tasks.jpetstore.buy;

import java.util.LinkedList;
import java.util.List;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.iobserve.selenium.tasks.fuzzy.properties.parameter.ListTaskParameter;
import org.iobserve.selenium.tasks.fuzzy.properties.parameter.VariableIntegerTaskParameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * One task to add a certain amount of one sort of fish to the shopping cart.
 *
 * @author Marc Adolf
 *
 */
public class AddFishToCartTask extends AbstractUserTask {

    private final VariableIntegerTaskParameter amount;
    private final ListTaskParameter<String> items;
    private final int itemPosition;

    /**
     * One task to add a certain amount of one sort of fish to the shopping cart.
     *
     * @param amount
     *            The amount of fish added in one task
     * @param itemPosition
     *            The position of the sort of fish in the list. Is set to the last position if the
     *            itemPosition exceeds the size of the list.
     */
    public AddFishToCartTask(final int amount, final int itemPosition) {
        this.amount = new VariableIntegerTaskParameter(1, 10, amount);
        this.itemPosition = itemPosition;

        final List<String> givenItems = new LinkedList<>();
        givenItems.add("FI-SW-01");
        givenItems.add("FI-SW-02");
        givenItems.add("FI-FW-01");
        givenItems.add("FI-FW-02");

        this.items = new ListTaskParameter<>(givenItems, itemPosition);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/jpetstore/actions/Catalog.action");
        final Boolean fuzzy = this.configuration.isFuzzy();
        final String item = this.items.getParameter(fuzzy);
        final int currentAmount = this.amount.getParameter(fuzzy);

        // buy fish
        for (int j = 0; j < currentAmount; j++) {
            driver.findElement(By.cssSelector("#QuickLinks > a > img")).click();
            driver.findElement(By.linkText(item)).click();
            driver.findElement(By.linkText("Add to Cart")).click();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {

        return "User buying " + this.amount + " fish at list position " + this.itemPosition + ".";
    }

}
