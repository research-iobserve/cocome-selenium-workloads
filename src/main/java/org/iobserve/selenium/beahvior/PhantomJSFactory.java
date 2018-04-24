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
package org.iobserve.selenium.beahvior;

import java.util.concurrent.TimeUnit;

import org.iobserve.selenium.configuration.PhantomConfiguration;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Reiner Jung
 *
 */
public class PhantomJSFactory {

    public static PhantomJSDriver createNewDriver(final PhantomConfiguration configuration) {
        final PhantomJSDriver driver;

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability("takesScreenshot", false);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, configuration.getPath());
        capabilities.setCapability("acceptSslCerts", true);
        capabilities.setCapability("webSecurityEnabled", false);
        final String[] phantomJsArgs = { "--web-security=no", "--ignore-ssl-errors=yes" };
        // "--debug=true"
        // "--webdriver-loglevel=DEBUG"
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomJsArgs);
        driver = new PhantomJSDriver(capabilities);
        // driver.setLogLevel(Level.ALL);
        // this.driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(configuration.getTimeout(), TimeUnit.MILLISECONDS);
        driver.manage().window().setSize(new Dimension(800, 600));

        return driver;
    }

}
