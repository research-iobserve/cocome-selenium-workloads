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
package org.iobserve.selenium.tasks.cashier;

import org.iobserve.selenium.tasks.IUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * As (already logged in) cashier scan the same N times and pay with the defined amount.
 *
 * @author Marc Adolf
 *
 */
public class CSBuySingleItemNTimesWithCashTask implements IUserTask {
    private final int productBarCode;
    private final int numberOfItems;
    private final int cashToPay;

    /**
     * Creates the task in which the scans the same item N times nad pays with the given value. THe
     * number of items, the bar code and the payment are given. Currently its not possible to buy
     * more than 8 items in CoCoME.
     *
     * @param productBarCode
     *            The bar code of the item to buy.
     * @param numberOfItems
     *            The number of items that should be bought.
     * @param cashToPay
     *            The amount of cash that is given to the cashier at the end.
     */
    public CSBuySingleItemNTimesWithCashTask(final int productBarCode, final int numberOfItems, final int cashToPay) {
        this.productBarCode = productBarCode;
        this.numberOfItems = numberOfItems;
        this.cashToPay = cashToPay;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void accept(final WebDriver driver, final String baseUrl) {

        driver.get(baseUrl + "/cloud-web-frontend/faces/store/start_sale.xhtml");
        // add all items to the "shopping cart"
        for (int i = 0; i < this.numberOfItems; i++) {
            driver.findElement(By.id("j_idt42:barcodetext")).clear();
            driver.findElement(By.id("j_idt42:barcodetext")).sendKeys(Integer.toString(this.productBarCode));
            driver.findElement(By.name("j_idt42:j_idt66")).click();
        }
        // pay
        driver.findElement(By.cssSelector("input[name=\"j_idt42:j_idt53\"]")).clear();
        driver.findElement(By.cssSelector("input[name=\"j_idt42:j_idt53\"]"))
                .sendKeys(Integer.toString(this.cashToPay));
        driver.findElement(By.cssSelector("input[name=\"j_idt42:j_idt98\"]")).click();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Cashier buying " + this.numberOfItems + " items with barcode " + this.productBarCode + " paying with "
                + this.cashToPay + "Moneys or Potatoes";
    }

}
