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
package org.iobserve.selenium.tasks.cocome.enterprisemanager;

import org.iobserve.selenium.tasks.IUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the login of the enterprise manager in CoCoME.
 *
 *
 * @author Marc Adolf
 *
 */
public final class EMLoginTask implements IUserTask {
    private final String name = "Enterprise manager - Login";

    @Override
    public void accept(final WebDriver driver, final String baseUrl) {
        driver.get(baseUrl + "/cloud-web-frontend/faces/login.xhtml");
        driver.findElement(By.name("j_idt27")).clear();
        driver.findElement(By.name("j_idt27")).sendKeys("enterprise");
        driver.findElement(By.name("j_idt24")).clear();
        driver.findElement(By.name("j_idt24")).sendKeys("enterprisemanager");
        driver.findElement(By.name("j_idt29")).click();
    }

    /**
     * Creates a single use instance of the defined task.
     *
     * @return A single use instance of the Task.
     */
    public static IUserTask create() {
        return new EMLoginTask();
    }

    @Override
    public String getName() {
        return this.name;
    }

}
