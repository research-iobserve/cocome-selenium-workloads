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
package org.iobserve.selenium.common;

import com.beust.jcommander.Parameter;

/**
 * Parse command line arguments. Uses JCommander.
 *
 * @author Marc Adolf
 *
 */
public class CommandlineArguments {

    @Parameter(names = "-phantomjs", description = "Path to the PhantomJS binaries", required = true)
    private String pathPhantomjs;

    @Parameter(names = "-url", description = "base url for the selenium workloads")
    private static String baseUrl = "https://172.17.0.2:8181";

    @Parameter(names = "-runs", description = " number of times a (repeatable) workload is executed")
    private static int numberOfRuns = 5;

    public String getPathPhantomjs() {
        return this.pathPhantomjs;
    }

    public static String getBaseUrl() {
        return CommandlineArguments.baseUrl;
    }

    public static int getNumberOfRuns() {
        return CommandlineArguments.numberOfRuns;
    }

}
