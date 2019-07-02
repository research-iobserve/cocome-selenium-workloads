/***************************************************************************
 * Copyright (C) 2019 iObserve Project (https://www.iobserve-devops.net)
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
package org.iobserve.selenium.behavior.tasks.jpetstore.buy;

import java.util.List;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.iobserve.selenium.behavior.tasks.jpetstore.ECategory;
import org.iobserve.selenium.common.ConfigurationException;
import org.iobserve.selenium.common.RandomGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

/**
 * One task to add a certain amount of one sort of item to the shopping cart.
 *
 * @author Reiner Jung
 *
 */
public class AddItemsToCartTask extends AbstractTask {

    private final int amount;
    private final String item;
    private final ECategory category;

    /**
     * One task to add a certain amount of one sort of item to the shopping cart.
     *
     * @param amount
     *            The amount of cat added in one task
     * @param item
     *            The position of the sort of item in the list. Is set to the last position if the
     *            itemPosition exceeds the size of the list.
     * @param category
     *            product category
     * @throws ConfigurationException
     *             on configuration error
     */
    @Parameters(names = { "amount", "item", "category" })
    public AddItemsToCartTask(final int amount, final String item, final ECategory category)
            throws ConfigurationException {
        super();
        this.amount = amount;
        this.category = category;

        final List<String> parameters = category.getProducts().getAllParameters();

        final int itemPosition = parameters.indexOf(item);
        if (itemPosition == -1) {
            if ("random".equals(item)) {
                this.item = category.getProducts().getAllParameters()
                        .get(RandomGenerator.getRandomNumber(0, parameters.size() - 1));
            } else {
                throw new ConfigurationException(String.format(
                        "Item %s is not a valid item in %s and it is not the special value", item, category.name()));
            }
        } else {
            this.item = category.getProducts().getAllParameters().get(itemPosition);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay)
            throws NoSuchSessionException {
        AbstractTask.LOGGER.info(String.format("%s[%d]: delay: %d item: %s amount: %d ", this.getName(), this.threadId,
                activityDelay, this.item, this.amount));

        driver.get(baseUrl + "/actions/Catalog.action");

        for (int j = 0; j < this.amount; j++) {
            driver.findElement(By.xpath("//div[@id='QuickLinks']/" + this.category.getCategoryString() + "/img"))
                    .click();
            this.sleep(activityDelay);
            driver.findElement(By.linkText(this.item)).click();
            this.sleep(activityDelay);
            driver.findElement(By.linkText("Add to Cart")).click();
            this.sleep(activityDelay);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return this.behaviorModel.getContainer().getName() + "/Adding items to cart";
    }
}
