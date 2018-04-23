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

import org.iobserve.selenium.behavior.tasks.AbstractUserTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Specific task to search for 'Fish' and then click the first result.
 *
 * @author Marc Adolf
 *
 */
public class SearchAndClickFishTask extends AbstractUserTask {

    @Parameters(names = {})
    public SearchAndClickFishTask() {
        // empty constructor
    }

    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        final String searchKey = "Fish";
        // TODO read Table and select one element.
        final String productToClick = "Fresh Water fish from China";

        driver.get(baseUrl + "actions/Catalog.action");
        driver.findElement(By.name("keyword")).click();
        driver.findElement(By.name("keyword")).clear();
        driver.findElement(By.name("keyword")).sendKeys(searchKey);
        driver.findElement(By.name("searchProducts")).click();
        driver.findElement(By.linkText(productToClick)).click();

    }

    @Override
    public String getName() {
        return "Search for Fish and click 'Fresh Water fish from China'";
    }

}
