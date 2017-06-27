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
package org.iobserve.selenium.tasks.enterprisemanager;

import org.iobserve.selenium.tasks.IUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * As the (already logged in) enterprise manager adds the second global product to the first shop of
 * the second enterprise. Price, min/max and cuurrent Stock are given.
 *
 * @author Marc Adolf
 *
 */
public class EMAddSecondProductToFirstStoreOfSecondEnterpriseTask implements IUserTask {
    private final float shopPrice;
    private final int stockMin;
    private final int stockMax;
    private final int stockCurrent;

    /**
     * Creates the task in which the enterprise manager adds a existing (2.) product to the first
     * shop of the second enterprise. Thereby, the shop price and details for the product stocks are
     * given.
     *
     * @param shopPrice
     *            The price for the product in this shop.
     * @param stockMin
     *            The minimum stock in this shop.
     * @param stockMax
     *            The maximum stock in this shop.
     * @param stockCurrent
     *            The starting stock in this shop.
     */
    public EMAddSecondProductToFirstStoreOfSecondEnterpriseTask(final float shopPrice, final int stockMin,
            final int stockMax, final int stockCurrent) {
        this.shopPrice = shopPrice;
        this.stockMin = stockMin;
        this.stockMax = stockMax;
        this.stockCurrent = stockCurrent;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void accept(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/enterprise/show_stores.xhtml");
        driver.findElement(By.linkText("Enterprises")).click();
        driver.findElement(By.xpath("//tr[2]/td[3]/a/img")).click();
        driver.findElement(By.xpath("//form[@id='j_idt37']/table/tbody/tr/td[4]/a[3]/img")).click();
        driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
        driver.findElement(
                By.cssSelector("tr.product-table-even-row > td..product-table-number-col > a.button > img.button"))
                .click();
        driver.findElement(By.cssSelector("img.button")).click();
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt48")).clear();
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt48")).sendKeys(Integer.toString(this.stockMin));
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt52")).clear();
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt52")).sendKeys(Integer.toString(this.stockMax));
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt56")).clear();
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt56")).sendKeys(Integer.toString(this.stockCurrent));
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt60")).clear();
        driver.findElement(By.name("j_idt37:j_idt38:0:j_idt60")).sendKeys(Float.toString(this.shopPrice));
        driver.findElement(By.cssSelector("img.button")).click();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Enterprise manager add 2. product to 1. shop of 2. enterprise with price: " + this.shopPrice
                + "\n+ Stock: min: " + this.stockMin + ", max: " + this.stockMax + ", current stock: "
                + this.stockCurrent;
    }

}
