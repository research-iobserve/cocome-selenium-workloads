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
package org.iobserve.selenium.tasks.cocome.cashier;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Represents the login of the cashier in CoCoME.
 *
 * @author Marc Adolf
 *
 */
public class CSLoginTask extends AbstractUserTask {

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/login.xhtml");
        new Select(driver.findElement(By.id("j_idt10"))).selectByVisibleText("Cashier");
        driver.findElement(By.cssSelector("option[value=\"CASHIER\"]")).click();
        driver.findElement(By.name("j_idt21")).clear();
        driver.findElement(By.name("j_idt21")).sendKeys("2");
        driver.findElement(By.name("j_idt24")).clear();
        driver.findElement(By.name("j_idt24")).sendKeys("cashier");
        driver.findElement(By.name("j_idt27")).clear();
        driver.findElement(By.name("j_idt27")).sendKeys("cashier");
        driver.findElement(By.name("j_idt29")).click();
        driver.findElement(By.linkText("Cashdesk")).click();
        driver.findElement(By.name("j_idt37:j_idt40")).click();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Cashier - Login";
    }

}
