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
package org.iobserve.selenium.behavior.tasks.jpetstore.browse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.iobserve.selenium.behavior.properties.parameter.ListTaskParameter;
import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.iobserve.selenium.behavior.tasks.jpetstore.ECategory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Browses the given category to the first or a random (if fuzzy) product and tries to click on the
 * first item.
 *
 * @author Marc Adolf
 *
 */
public class ViewProductTask extends AbstractTask {

    private final ListTaskParameter<ECategory> category;

    /**
     * Creates a new task to visit a certain category and its products.
     *
     * @param category
     *            The category to visit.
     */
    @Parameters(names = { "category" })
    public ViewProductTask(final ECategory category) {
        super();
        final List<ECategory> categoryList = new LinkedList<>(Arrays.asList(ECategory.values()));
        this.category = new ListTaskParameter<>(categoryList, categoryList.indexOf(category));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#executeTask(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        final ECategory curentCategory = this.category.getSelectedParameter();
        final String categoryString = curentCategory.getCategoryString();
        final String productString = curentCategory.getProducts().getSelectedParameter();

        AbstractTask.LOGGER.info(String.format("%s[%d]: delay: %d category: %s product: %s ", this.getName(),
                this.threadId, activityDelay, categoryString, productString));

        driver.get(baseUrl + "/actions/Catalog.action");

        try {
            driver.findElement(By.xpath("//div[@id='QuickLinks']/" + categoryString + "/img")).click();
            this.sleep(activityDelay);
            driver.findElement(By.linkText(productString)).click();
            this.sleep(activityDelay);
            // since the item ids have a special pattern we can just iterate until we find the first
            // one
            this.clickItemElement(driver, activityDelay);
        } catch (final NoSuchElementException ex) {
            AbstractTask.LOGGER.error(String.format("Element could not be found: %s", ex.getLocalizedMessage()));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#getName()
     */
    @Override
    public String getName() {
        return this.behaviorModel.getContainer().getName() + "/View category "
                + this.category.getSelectedParameter().toString() + " and one of its products: ";
    }

    private void clickItemElement(final WebDriver driver, final long activityDelay) {
        final String baseString = "EST-";
        for (int i = 1; i < 30; i++) {
            AbstractTask.LOGGER
                    .debug(String.format("%s[%d]: Try to find %s %d", this.getName(), this.threadId, baseString, i));

            final List<WebElement> elements = driver.findElements(By.linkText(baseString + i));
            if (!elements.isEmpty()) {
                AbstractTask.LOGGER.debug("%s[%d]: Found element and will click on it: {}", this.getName(),
                        this.threadId, elements);

                elements.get(0).click();
                this.sleep(activityDelay);
                return;
            }

        }
    }

}
