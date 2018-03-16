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

/**
 * Task to change a given user information. Requires the user to be logged in.
 *
 * @author Marc Adolf
 *
 */
public class ChangeAccountInformationTask extends AbstractUserTask {
    private final Attribute attribute;

    public ChangeAccountInformationTask(final Attribute attributToChange) {
        this.attribute = attributToChange;
    }

    @Override
    public void executeTask(final WebDriver driver, final String baseUrl) {
        // TODO
        final String newValue = "Joe";

        String attributeString = "";

        // Ausreichend?
        final boolean checkBox = false;

        switch (this.attribute) {
        case FIRSTNAME:
            attributeString = "account.firstName";
            break;

        case LASTNAME:
            attributeString = "account.lastName";
            break;

        case EMAIL:
            attributeString = "account.email";
            break;

        case PHONE:
            attributeString = "account.phone";
            break;

        case ADDRESS1:
            attributeString = "account.address1";
            break;

        case ADDRESS2:
            attributeString = "account.address2";
            break;

        case CITY:
            attributeString = "account.city";
            break;

        case STATE:
            attributeString = "account.state";
            break;

        case ZIP:
            attributeString = "account.zip";
            break;

        case COUNTRY:
            attributeString = "account.country";
            break;

        default:
            break;
        }

        if (!checkBox) {
            driver.get(baseUrl + "/jpetstore/actions/Account.action?editAccountForm");
            driver.findElement(By.name(attributeString)).clear();
            driver.findElement(By.name(attributeString)).sendKeys(newValue);
            driver.findElement(By.name(attributeString)).click();
        }

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

    /**
     * The possible changeable fields in the account form.
     *
     * @author Marc Adolf
     *
     */
    public enum Attribute {
        FIRSTNAME, LASTNAME, EMAIL, PHONE, ADDRESS1, ADDRESS2, CITY, STATE, ZIP, COUNTRY
    }

}
