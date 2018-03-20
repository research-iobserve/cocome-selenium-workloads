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
package org.iobserve.selenium.tasks.jpetstore.browse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.iobserve.selenium.tasks.fuzzy.properties.parameter.ListTaskParameter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Browses the given category to the first or a random (if fuzzy) product and tries to click on the
 * first item.
 *
 * @author Marc Adolf
 *
 */
public class ViewProductTask extends AbstractUserTask {

    private final ListTaskParameter<Category> category;

    /**
     * Creates a new task to visit a certain category and its products.
     *
     * @param category
     *            The category to visit.
     */
    public ViewProductTask(final Category category) {
        final List<Category> categoryList = new LinkedList<>(Arrays.asList(Category.values()));
        this.category = new ListTaskParameter<>(categoryList, categoryList.indexOf(category));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#executeTask(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        final Category curentCategory = this.category.getParameter(this.configuration.isFuzzy());
        final String categoryString = curentCategory.getCategoryString();
        final String productString = curentCategory.getProducts().getParameter(this.configuration.isFuzzy());
        driver.get(baseUrl + "actions/Catalog.action");
        driver.findElement(By.xpath("//div[@id='SidebarContent']/" + categoryString + "/img")).click();
        driver.findElement(By.linkText(productString)).click();
        // since the item ids have a special pattern we can just iterate until we find the first one
        this.clickItemElement(driver);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#getName()
     */
    @Override
    public String getName() {
        return "View category " + this.category.toString() + " and one of its products: ";
    }

    private void clickItemElement(final WebDriver driver) {
        final String baseString = "EST-";
        for (int i = 1; i < 30; i++) {
            final List<WebElement> elements = driver.findElements(By.linkText(baseString + i));
            if (!elements.isEmpty()) {
                elements.get(0).click();
                return;
            }

        }
    }

    /**
     * Defines the clickable categories and information about their products.
     *
     * @author Marc Adolf
     *
     */
    public enum Category {
        FISH("a", "FI-SW-01", "FI-SW-02", "FI-FW-01", "FI-FW-02"), DOGS("a[2]", "K9-BD-01", "K9-PO-02", "K9-DL-01",
                "K9-RT-01", "K9-RT-02", "K9-CW-01"), REPTILES("a[3]", "RP-SN-01",
                        "RP-LI-02"), CATS("a[4]", "FL-DSH-01", "FL-DLH-02"), BIRDS("a[5]", "AV-CB-01", "AV-SB-02");

        private String categoryString;
        private ListTaskParameter<String> products;

        private Category(final String categoryString, final String... products) {
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

}
