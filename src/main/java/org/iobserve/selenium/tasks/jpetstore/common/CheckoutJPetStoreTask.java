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
package org.iobserve.selenium.tasks.jpetstore.common;

import org.iobserve.selenium.tasks.IUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checks out the current content of the shopping cart and confirms the order. Needs the
 * {@link LoginJPetStoreTask} to be executed first.
 *
 * @author Marc Adolf
 *
 */
public class CheckoutJPetStoreTask implements IUserTask {

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void accept(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/jpetstore/actions/Catalog.action");

        driver.findElement(By.cssSelector("img[name=\"img_cart\"]")).click();

        driver.findElement(By.linkText("Proceed to Checkout")).click();

        driver.findElement(By.name("newOrder")).click();

        driver.findElement(By.linkText("Confirm")).click();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {

        return "Checkout current shopping cart";
    }

}
