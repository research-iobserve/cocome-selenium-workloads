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
package org.iobserve.selenium.tasks;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Represents a single or complex task for user roles of a opposite.
 *
 * @author Christoph Dornieden
 * @author Marc Adolf
 *
 */
public abstract class AbstractUserTask {
    private final int numberOfRuns;
    private PhantomJSDriver driver;
    private final String baseUrl;
    private final String pathDriver;

    /**
     * @param baseUrl
     *            Base URL of the visited website.
     * @param numberOfRuns
     *            Number of repetitions of the defined task(s).
     * @param pathDriver
     *            The webdriver used to execute the Selenium task(s)
     *
     */
    public AbstractUserTask(final String baseUrl, final int numberOfRuns, final String pathDriver) {
        this.baseUrl = baseUrl;
        this.numberOfRuns = numberOfRuns;
        this.pathDriver = pathDriver;
        this.createNewDriver();

    }

    /**
     * Executes the defined user task(s) and therefore generates the workload.
     */
    public final void generateUserBehavior() {
        for (int i = 0; i < this.numberOfRuns; i++) {
            this.executeWorkload();
            // clean existing sessions and create new one
            this.driver.quit();
            this.createNewDriver();
        }
    }

    /**
     * Defines the behavior and therefore the workload of a specific {@link AbstractUserTask
     * UserTask}. May vary from task to task.
     */
    protected abstract void executeWorkload();

    private void createNewDriver() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, this.pathDriver);
        capabilities.setCapability("acceptSslCerts", true);
        capabilities.setCapability("webSecurityEnabled", false);
        final String[] phantomJsArgs = { "--web-security=no", "--ignore-ssl-errors=yes" };
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJsArgs);
        this.driver = new PhantomJSDriver(capabilities);
        // this.driver.setLogLevel(Level.INFO);
        // this.driver = new HtmlUnitDriver();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.driver.manage().window().setSize(new Dimension(800, 600));
    }

    protected PhantomJSDriver getDriver() {
        return this.driver;
    }

    protected String getBaseUrl() {
        return this.baseUrl;
    }

}
