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

import java.util.concurrent.TimeUnit;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * Represents the login of the enterprise manager in cocome.
 *
 * @author Marc Adolf
 *
 */
public class EMLogin extends AbstractUserTask {

    /**
     * See {@link AbstractUserTask#AbstractUserTask(String, int, String)}.
     *
     * @param baseUrl
     *            Base URL of the visited website.
     *
     * @param numberOfRuns
     *            Number of repetitions for each task.
     * @param pathDriver
     *            Path to the PhantomJS binaries.
     *
     */
    public EMLogin(final String baseUrl, final int numberOfRuns, final String pathDriver) {
        super(baseUrl, numberOfRuns, pathDriver);
        this.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Override
    protected void executeWorkload() {
        final PhantomJSDriver driver = this.getDriver();
        driver.get(this.getBaseUrl() + "/cloud-web-frontend/faces/login.xhtml");
        driver.findElement(By.name("j_idt27")).clear();
        driver.findElement(By.name("j_idt27")).sendKeys("enterprise");
        driver.findElement(By.name("j_idt24")).clear();
        driver.findElement(By.name("j_idt24")).sendKeys("enterprisemanager");
        driver.findElement(By.name("j_idt29")).click();
    }
}