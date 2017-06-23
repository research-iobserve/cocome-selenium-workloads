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
package org.iobserve.selenium.workloads.config;

import java.util.concurrent.TimeUnit;

import org.iobserve.selenium.workloads.handling.WorkloadPlan;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Contains basic informations for a {@link WorkloadPlan}.
 *
 * @author Marc Adolf
 *
 */
public class WorkloadConfiguration {

    private final String baseUrl;
    private final int numberOfRuns;
    private final String pathWebDriver;
    private PhantomJSDriver driver;

    /**
     *
     * @param baseUrl
     *            The base URL of the used webservice.
     * @param numberOfRuns
     *            The number of repetitions of the {@link WorkloadPlan}.
     * @param pathWebDriver
     *            Path to the PhantomJS binaries
     */
    public WorkloadConfiguration(final String baseUrl, final int numberOfRuns, final String pathWebDriver) {
        this.baseUrl = baseUrl;
        this.numberOfRuns = numberOfRuns;
        this.pathWebDriver = pathWebDriver;
        this.createNewDriver();

    }

    private void createNewDriver() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, this.pathWebDriver);
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

    /**
     * Delete old driver and create a new one therefore creating a new session.
     */
    public final void newSession() {
        this.createNewDriver();
    }

    public final String getBaseUrl() {
        return this.baseUrl;
    }

    public final int getNumberOfRuns() {
        return this.numberOfRuns;
    }

    public final PhantomJSDriver getDriver() {
        return this.driver;
    }

}
