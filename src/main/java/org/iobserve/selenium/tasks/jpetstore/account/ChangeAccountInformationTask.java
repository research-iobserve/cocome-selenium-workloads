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
package org.iobserve.selenium.tasks.jpetstore.account;

import org.iobserve.selenium.tasks.AbstractUserTask;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangeAccountInformationTask extends AbstractUserTask {

    public ChangeAccountInformationTask() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        final String attribute = "account.firstName";
        final String newValue = "Joe";

        driver.get(baseUrl + "/jpetstore/actions/Account.action?editAccountForm");
        driver.findElement(By.name(attribute)).clear();
        driver.findElement(By.name(attribute)).sendKeys(newValue);
        driver.findElement(By.name(attribute)).click();

        // driver.findElement(By.name("account.lastName")).click();
        // driver.findElement(By.name("account.email")).click();
        // driver.findElement(By.name("account.phone")).click();
        // driver.findElement(By.name("account.address1")).click();
        // driver.findElement(By.name("account.address2")).click();
        // driver.findElement(By.name("account.city")).click();
        // driver.findElement(By.name("account.state")).click();
        // driver.findElement(By.name("account.zip")).click();
        // driver.findElement(By.name("account.country")).click();
        // driver.findElement(By.name("account.languagePreference")).click();
        // driver.findElement(By.xpath("//option[@value='english']")).click();
        // driver.findElement(By.name("account.languagePreference")).click();
        // new
        // Select(driver.findElement(By.name("account.languagePreference"))).selectByVisibleText("japanese");
        // driver.findElement(By.xpath("//option[@value='japanese']")).click();
        // driver.findElement(By.name("account.favouriteCategoryId")).click();
        // new
        // Select(driver.findElement(By.name("account.favouriteCategoryId"))).selectByVisibleText("FISH");
        // driver.findElement(By.xpath("//option[@value='FISH']")).click();
        // driver.findElement(By.name("account.favouriteCategoryId")).click();
        // new
        // Select(driver.findElement(By.name("account.favouriteCategoryId"))).selectByVisibleText("DOGS");
        // driver.findElement(By.xpath("//option[@value='DOGS']")).click();
        // driver.findElement(By.name("account.favouriteCategoryId")).click();
        // new
        // Select(driver.findElement(By.name("account.favouriteCategoryId"))).selectByVisibleText("REPTILES");
        // driver.findElement(By.xpath("//option[@value='REPTILES']")).click();
        // driver.findElement(By.name("account.favouriteCategoryId")).click();
        // new
        // Select(driver.findElement(By.name("account.favouriteCategoryId"))).selectByVisibleText("CATS");
        // driver.findElement(By.xpath("//option[@value='CATS']")).click();
        // driver.findElement(By.name("account.favouriteCategoryId")).click();
        // new
        // Select(driver.findElement(By.name("account.favouriteCategoryId"))).selectByVisibleText("BIRDS");
        // driver.findElement(By.xpath("//option[@value='BIRDS']")).click();
        // driver.findElement(By.name("account.listOption")).click();
        // driver.findElement(By.name("account.listOption")).click();
        // driver.findElement(By.name("account.bannerOption")).click();
        // driver.findElement(By.name("account.bannerOption")).click();
        // driver.findElement(By.name("editAccount")).click();

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
