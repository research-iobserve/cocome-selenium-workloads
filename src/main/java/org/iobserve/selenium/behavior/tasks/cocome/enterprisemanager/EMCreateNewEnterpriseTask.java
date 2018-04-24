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
 * The (already logged in) enterprise manager creates a new enterprise.
 *
 * @author Marc Adolf
 *
 */
public class EMCreateNewEnterpriseTask extends AbstractTask {

    private final String enterpriseName;

    /**
     * Creates the task in which the enterprise manager creates a new enterprise with a given name.
     *
     * @param enterpriseName
     *            The name of the new enterprise.
     */
    @Parameters(names = { "enterpriseName" })
    public EMCreateNewEnterpriseTask(final String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.function.BiConsumer#accept(java.lang.Object, java.lang.Object)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/enterprise/show_enterprises.xhtml");
        driver.findElement(By.linkText("New Enterprise")).click();
        this.sleep(activityDelay);
        driver.findElement(By.name("j_idt36:j_idt40")).clear();
        driver.findElement(By.name("j_idt36:j_idt40")).sendKeys(this.enterpriseName);
        driver.findElement(By.name("j_idt36:j_idt42")).click();
        this.sleep(activityDelay);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.IUserTask#getName()
     */
    @Override
    public String getName() {
        return "Enterprise manager creating enterprise " + this.enterpriseName;
    }

}
