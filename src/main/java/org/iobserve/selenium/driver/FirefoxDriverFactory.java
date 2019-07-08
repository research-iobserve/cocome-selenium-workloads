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
package org.iobserve.selenium.driver;

import org.iobserve.selenium.configuration.WebDriverConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For some reason this does not work.
 *
 * @author Reiner Jung
 *
 */
public final class FirefoxDriverFactory implements IDriverFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(FirefoxDriverFactory.class);

    /**
     * Default constructor.
     */
    public FirefoxDriverFactory() {
        // empty, private factory constructor
    }

    @Override
    public WebDriver createNewDriver(final WebDriverConfiguration configuration) {
        return this.createNewDriver(configuration, 0);
    }

    private WebDriver createNewDriver(final WebDriverConfiguration configuration, final int level) {
        System.setProperty("webdriver.gecko.driver", configuration.getDriver());
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

        final FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        options.setLogLevel(FirefoxDriverLogLevel.ERROR);
        options.setProfile(new FirefoxProfile());
        options.setCapability("binary", configuration.getDriver());
        options.addArguments("-headless");
        options.addArguments("-marionette");

        try {
            return new FirefoxDriver(options);
        } catch (final org.openqa.selenium.WebDriverException e) {
            e.printStackTrace();
            if (level < 10) {
                return this.createNewDriver(configuration, level + 1);
            } else {
                return null;
            }
        }
    }
}
