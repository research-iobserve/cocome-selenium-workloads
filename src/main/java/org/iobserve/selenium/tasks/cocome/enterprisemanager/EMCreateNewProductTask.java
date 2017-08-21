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

import org.iobserve.selenium.tasks.IUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * As the (already logged in ) enterprise manager create a new product.
 *
 * @author Marc Adolf
 *
 */
public class EMCreateNewProductTask implements IUserTask {
    private final String productName;
    private final int productBarCode;
    private final float productPrice;

    /**
     * Creates the task in which the enterprise manager creates a new product with a given name,
     * price and barcode.
     *
     * @param productName
     *            The name of the new product.
     * @param productBarCode
     *            The barcode of the new product (should be unique).
     * @param productPrice
     *            The price of the new product.
     */
    public EMCreateNewProductTask(final String productName, final int productBarCode, final float productPrice) {
        this.productName = productName;
        this.productBarCode = productBarCode;
        this.productPrice = productPrice;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.function.BiConsumer#accept(java.lang.Object, java.lang.Object)
     */
    @Override
    public void accept(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/enterprise/show_enterprises.xhtml");
        driver.findElement(By.linkText("New Product")).click();
        driver.findElement(By.name("j_idt36:j_idt40")).clear();
        driver.findElement(By.name("j_idt36:j_idt40")).sendKeys(this.productName);
        driver.findElement(By.name("j_idt36:j_idt44")).clear();
        driver.findElement(By.name("j_idt36:j_idt44")).sendKeys(Integer.toString(this.productBarCode));
        driver.findElement(By.name("j_idt36:j_idt48")).clear();
        driver.findElement(By.name("j_idt36:j_idt48")).sendKeys(Float.toString(this.productPrice));
        driver.findElement(By.name("j_idt36:j_idt50")).click();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Enterprise manager creating product" + this.productName + " with barcode: " + this.productBarCode
                + ", with price: " + this.productPrice;
    }

}
