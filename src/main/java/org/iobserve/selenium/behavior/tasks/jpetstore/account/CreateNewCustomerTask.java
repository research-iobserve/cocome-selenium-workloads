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
package org.iobserve.selenium.behavior.tasks.jpetstore.account;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Creates a new customer. The parameters besides user name and password are not configurable.
 *
 * @author Marc Adolf
 *
 */
public class CreateNewCustomerTask extends AbstractTask {
    private final String username;
    private final String password;

    /**
     * Creates a new customer task with the given credentials.
     *
     * @param userName
     *            The user to be to be created.
     *
     * @param password
     *            The password used for the new account.
     */
    @Parameters(names = { "username", "password" })
    public CreateNewCustomerTask(final String userName, final String password) {
        this.username = userName;
        this.password = password;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#executeTask(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        driver.get(baseUrl + "/actions/Catalog.action");
        driver.findElement(By.linkText("Sign In")).click();
        this.sleep(activityDelay);
        driver.findElement(By.linkText("Register Now!")).click();
        this.sleep(activityDelay);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(this.username);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(this.password);
        driver.findElement(By.name("repeatedPassword")).clear();
        driver.findElement(By.name("repeatedPassword")).sendKeys(this.password);
        driver.findElement(By.name("account.firstName")).clear();
        driver.findElement(By.name("account.firstName")).sendKeys("New");
        driver.findElement(By.name("account.lastName")).clear();
        driver.findElement(By.name("account.lastName")).sendKeys("Customer");
        driver.findElement(By.name("account.email")).clear();
        driver.findElement(By.name("account.email")).sendKeys("new@customer.de");
        driver.findElement(By.name("account.phone")).clear();
        driver.findElement(By.name("account.phone")).sendKeys("12345");
        driver.findElement(By.name("account.address1")).clear();
        driver.findElement(By.name("account.address1")).sendKeys("Wohnort");
        driver.findElement(By.name("account.address2")).clear();
        driver.findElement(By.name("account.address2")).sendKeys("Geschäftssitz 3");
        driver.findElement(By.name("account.city")).clear();
        driver.findElement(By.name("account.city")).sendKeys("Kiel");
        driver.findElement(By.name("account.state")).clear();
        driver.findElement(By.name("account.state")).sendKeys("S-H");
        driver.findElement(By.name("account.zip")).clear();
        driver.findElement(By.name("account.zip")).sendKeys("24118");
        driver.findElement(By.name("account.country")).clear();
        driver.findElement(By.name("account.country")).sendKeys("Germany");
        driver.findElement(By.name("newAccount")).click();
        this.sleep(activityDelay);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#getName()
     */
    @Override
    public String getName() {
        return "Creates a new customer";
    }

}
