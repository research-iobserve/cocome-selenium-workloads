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

package org.iobserve.selenium.tasks;

import java.util.function.BiConsumer;

import org.openqa.selenium.WebDriver;

/**
 * Cosmetic Interface to have a clear distinction between task of user and other (system) tasks.
 *
 *
 * @author Marc Adolf
 * @author Sören Henning
 *
 */
public interface IUserTask extends BiConsumer<WebDriver, String> {

    /**
     *
     * @return The name of the task.
     */
    public String getName();
}
