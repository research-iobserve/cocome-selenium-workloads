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
package org.iobserve.selenium.tasks.jpetstore.buy;

import java.util.LinkedList;
import java.util.List;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.iobserve.selenium.tasks.fuzzy.properties.parameter.ListTaskParameter;
import org.iobserve.selenium.tasks.fuzzy.properties.parameter.VariableIntegerTaskParameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * One task to add a certain amount of one sort of birds to the shopping cart.
 *
 * @author Marc Adolf
 *
 */
public class AddBirdsToCartTask extends AbstractUserTask {

    private final VariableIntegerTaskParameter amount;
    private final ListTaskParameter<String> items;

    /**
     * One task to add a certain amount of one sort of birds to the shopping cart.
     *
     * @param amount
     *            The amount of birds added in one task
     * @param itemPosition
     *            The position of the sort of birds in the list. Is set to the last position if the
     *            itemPosition exceeds the size of the list.
     */
    public AddBirdsToCartTask(final int amount, final int itemPosition) {
        this.amount = new VariableIntegerTaskParameter(1, 10, amount);

        final List<String> givenItems = new LinkedList<>();
        givenItems.add("AV-CB-01");
        givenItems.add("AV-SB-02");

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
        driver.get(baseUrl + "actions/Catalog.action");
        final Boolean fuzzy = this.configuration.isFuzzy();
        final String item = this.items.getParameter(fuzzy);
        final int currentAmount = this.amount.getParameter(fuzzy);

        AbstractUserTask.LOGGER.info(String.format("%s: item: %s amount: %d ", this.getName(), item, currentAmount));

        for (int j = 0; j < currentAmount; j++) {
            driver.findElement(By.xpath("//div[@id='QuickLinks']/a[5]/img")).click();
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

        return "Adding birds to cart task";
    }

}
