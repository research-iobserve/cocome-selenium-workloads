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
package org.iobserve.selenium.behavior.tasks.jpetstore.common;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Logs in with a given pair of credentials.
 *
 * @author Marc Adolf
 *
 */
public class LoginJPetStoreTask extends AbstractTask {
    private final String username;
    private final String password;

    /**
     * Creates a login task with the given credentials.
     *
     * @param username
     *            The user to be logged in.
     *
     * @param password
     *            The password used to log in.
     */
    @Parameters(names = { "username", "password" })
    public LoginJPetStoreTask(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        driver.get(baseUrl + "/actions/Catalog.action");
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys(this.username);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(this.password);
        driver.findElement(By.name("signon")).click();
        this.sleep(activityDelay);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return this.behaviorModel.getContainer().getName() + "/Log in " + this.username;
    }

}