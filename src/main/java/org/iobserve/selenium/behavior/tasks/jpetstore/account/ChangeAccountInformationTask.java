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
package org.iobserve.selenium.behavior.tasks.jpetstore.account;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Task to change a given user information. Requires the user to be logged in.
 *
 * @author Marc Adolf
 *
 */
public class ChangeAccountInformationTask extends AbstractTask {
    private static Set<String> allowedFavoriteCategories = new HashSet<>(
            Arrays.asList("DOGS", "CATS", "REPTILES", "BIRDS", "FISH"));
    private final Attribute attribute;
    private String value;
    private final String buttonToClick;

    /**
     * Creates a new task to change information of an already logged in user.
     *
     * @param attribute
     *            The field that should be changed.
     * @param value
     *            The new value for the changed field.
     */
    @Parameters(names = { "attribute", "value" })
    public ChangeAccountInformationTask(final Attribute attribute, final String value) {
        this.attribute = attribute;
        this.buttonToClick = this.attribute.getAttributeName();
        this.value = value;
    }

    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {

        final String attributeString = this.attribute.getAttributeName();
        driver.get(baseUrl + "/actions/Account.action?editAccountForm");

        if (this.attribute.equals(Attribute.LANGUAGE) || this.attribute.equals(Attribute.CATEGORY)) {
            // default is japanese.. maybe change for the future
            this.value = this.getDropDownOption();
            this.useDropDown(driver, attributeString);
        } else if (!(this.attribute.equals(Attribute.LIST_OPTION) || this.attribute.equals(Attribute.BANNER_OPTION))) {
            // the remaining options are only fields.
            this.fillAttributeFields(driver, attributeString);
        }

        // checkBox is just to click the button with the attribute string

        driver.findElement(By.name(this.buttonToClick)).click();
        this.sleep(activityDelay);
        driver.findElement(By.name("editAccount")).click();
        this.sleep(activityDelay);
    }

    private String getDropDownOption() {
        String localValue = this.value;

        if (this.attribute.equals(Attribute.CATEGORY)
                && !ChangeAccountInformationTask.allowedFavoriteCategories.contains(localValue)) {
            // default is cats.. maybe change in the future
            localValue = "CATS";
        } else if (this.attribute.equals(Attribute.LANGUAGE) && !"english".equals(this.value)) {
            // default is japanese
            localValue = "japanese";
        }

        return localValue;

    }

    private void fillAttributeFields(final WebDriver driver, final String attributeString) {
        driver.findElement(By.name(attributeString)).clear();
        driver.findElement(By.name(attributeString)).sendKeys(this.value);
    }

    private void useDropDown(final WebDriver driver, final String attributeString) {
        driver.findElement(By.name(attributeString)).click();
        driver.findElement(By.xpath("//option[@value='" + this.value + "']")).click();
    }

    @Override
    public String getName() {
        return "Set (or toggle) account information" + this.attribute + " to: " + this.value;
    }

    /**
     * The possible changeable fields in the account form.
     *
     * @author Marc Adolf
     *
     */
    public enum Attribute {
        FIRSTNAME("account.firstname"), LASTNAME("account.lastName"), EMAIL("account.email"), PHONE(
                "account.phone"), ADDRESS1("account.address1"), ADDRESS2("account.address2"), CITY(
                        "account.city"), STATE("account.state"), ZIP("account.zip"), COUNTRY(
                                "account.country"), LANGUAGE("account.languagePreference"), CATEGORY(
                                        "account.favouriteCategoryId"), LIST_OPTION(
                                                "account.listOption"), BANNER_OPTION("account.bannerOption");

        private String attributeName;

        private Attribute(final String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeName() {
            return this.attributeName;
        }

    }

}
