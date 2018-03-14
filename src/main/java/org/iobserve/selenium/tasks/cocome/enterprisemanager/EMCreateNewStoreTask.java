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
package org.iobserve.selenium.tasks.cocome.enterprisemanager;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Creates a new store with a given name and a given location to the second enterprise in the list.
 *
 * @author Marc Adolf
 *
 */
public class EMCreateNewStoreTask extends AbstractUserTask {
    private final String storeName;
    private final String storeLocation;

    /**
     * Creates the task in which the enterprise manager creates a new st9ore for the second
     * enterpise with a given name and location.
     *
     * @param storeName
     *            The name of the new store.
     * @param storeLocation
     *            The location of the new store.
     */
    public EMCreateNewStoreTask(final String storeName, final String storeLocation) {
        this.storeName = storeName;
        this.storeLocation = storeLocation;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/enterprise/create_product.xhtml");
        driver.findElement(By.linkText("Enterprises")).click();
        driver.findElement(By.xpath("//tr[2]/td[3]/a[2]/img")).click();
        driver.findElement(By.name("j_idt37:j_idt41")).clear();
        driver.findElement(By.name("j_idt37:j_idt41")).sendKeys(this.storeName);
        driver.findElement(By.name("j_idt37:j_idt45")).clear();
        driver.findElement(By.name("j_idt37:j_idt45")).sendKeys(this.storeLocation);
        driver.findElement(By.name("j_idt37:j_idt47")).click();

    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Enterprise manager creating new store for second enterprise with name: " + this.storeName
                + " and location: " + this.storeLocation;
    }

}
