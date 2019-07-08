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

import java.util.logging.Level;

import org.iobserve.selenium.configuration.WebDriverConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Reiner Jung
 *
 */
public final class ChromeDriverFactory implements IDriverFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(ChromeDriverFactory.class);

    /**
     * Default constructor.
     */
    public ChromeDriverFactory() {
        // empty, private factory constructor
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.beahvior.IDriverFactory#createNewDriver(org.iobserve.selenium.
     * configuration.WebDriverConfiguration)
     */
    @Override
    public WebDriver createNewDriver(final WebDriverConfiguration configuration) {
        return this.createNewDriver(configuration, 0);
    }

    private WebDriver createNewDriver(final WebDriverConfiguration configuration, final int level) {
        System.setProperty("webdriver.chrome.driver", configuration.getDriver());

        ChromeDriverFactory.LOGGER.debug("driver location {}", configuration.getDriver());

        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.setCapability("detach", false);

        try {
            final RemoteWebDriver driver = new ChromeDriver(options);
            driver.setLogLevel(Level.ALL);

            return driver;
        } catch (final org.openqa.selenium.WebDriverException ex) {
            if (level < 10) {
                try {
                    Thread.sleep(1000);
                    return this.createNewDriver(configuration, level + 1);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        }
    }

}
