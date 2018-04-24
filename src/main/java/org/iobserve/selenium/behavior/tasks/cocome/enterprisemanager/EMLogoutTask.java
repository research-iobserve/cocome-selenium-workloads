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
package org.iobserve.selenium.behavior.tasks.cocome.enterprisemanager;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the logout of the (already logged in) enterprise manager in CoCoME.
 *
 * @author Marc Adolf
 *
 */
public class EMLogoutTask extends AbstractTask {

    /**
     * Default constructor. Necessary to annotate parameter names.
     */
    @Parameters(names = {})
    public EMLogoutTask() {
        // empty constructor
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#accept(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/store/show_stock.xhtml");
        driver.findElement(By.linkText("Logout")).click();
        this.sleep(activityDelay);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Enterprise manager - Logout ";
    }

}
