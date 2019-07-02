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

import java.util.List;

import org.iobserve.selenium.behavior.tasks.AbstractTask;
import org.iobserve.selenium.behavior.tasks.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Task for viewing all orders of one account and clicking the first one.
 *
 * @author Marc Adolf
 *
 */
public class ViewOrderTask extends AbstractTask {

    /**
     * Default constructor. Necessary to annotate parameter names.
     */
    @Parameters(names = {})
    public ViewOrderTask() {
        // empty constructor
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#executeTask(org.openqa.selenium.WebDriver,
     * java.lang.String)
     */
    @Override
    public void executeTask(final WebDriver driver, final String baseUrl, final long activityDelay) {
        final String orderToClick = "1000";
        driver.get(baseUrl + "/actions/Account.action?editAccountForm=");
        driver.findElement(By.linkText("My Orders")).click();
        this.sleep(activityDelay);
        final List<WebElement> foundOrders = driver.findElements(By.linkText(orderToClick));
        if (!foundOrders.isEmpty()) {
            foundOrders.get(0).click();
        }
        this.sleep(activityDelay);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.iobserve.selenium.tasks.AbstractUserTask#getName()
     */
    @Override
    public String getName() {
        return this.behaviorModel.getContainer().getName() + "/View all orders and click the first one";
    }

}
