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

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Specific task to search for 'Fish' and then click the first result.
 *
 * @author Marc Adolf
 *
 */
public class SearchAndClickProductTask extends AbstractTask {

    private final String searchKey;
    private final String productToClick;

    /**
     * Create task.
     *
     * @param searchKey
     *            search key
     * @param productToClick
     *            product to click
     */
    @Parameters(names = { "searchKey", "productToClick" })
    public SearchAndClickProductTask(final String searchKey, final String productToClick) {
        this.searchKey = searchKey;
        this.productToClick = productToClick;
    }

    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        AbstractTask.LOGGER.info(String.format("%s[%d]: delay: %d  key: %s  product: %s", this.getName(), this.threadId,
                activityDelay, this.searchKey, this.productToClick));

        driver.get(baseUrl + "/actions/Catalog.action");
        driver.findElement(By.name("keyword")).click();
        this.sleep(activityDelay);
        driver.findElement(By.name("keyword")).clear();
        driver.findElement(By.name("keyword")).sendKeys(this.searchKey);
        driver.findElement(By.name("searchProducts")).click();
        this.sleep(activityDelay);
        driver.findElement(By.linkText(this.productToClick)).click();
        this.sleep(activityDelay);
    }

    @Override
    public String getName() {
        return String.format("%s/Search", this.behaviorModel.getContainer().getName());
    }

}
