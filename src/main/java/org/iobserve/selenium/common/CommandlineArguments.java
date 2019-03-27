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

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

/**
 * Parse command line arguments. Uses JCommander.
 *
 * @author Marc Adolf
 * @author Reiner Jung
 *
 */
public class CommandlineArguments {

    @Parameter(names = { "-p",
            "--print-workloads" }, description = "The name(s) of the workload(s) that should be executed.", help = true)
    private static Boolean printWorkloads = false;

    @Parameter(names = { "-u", "--url" }, description = "Base url for the selenium workloads")
    private String baseUrl;

    @Parameter(names = { "-c",
            "--workload-characterization" }, description = "Configuration file for the workload characterization.", required = true, converter = FileConverter.class)
    private File configuration;

    @Parameter(names = { "-d", "--web-driver" }, description = "Path to the Phantom JS driver", required = false)
    private String pathWebDriver;

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public final File getConfigurationFile() {
        return this.configuration;
    }

    public static Boolean getPrintWorkloads() {
        return CommandlineArguments.printWorkloads;
    }

    public String getPathWebDriver() {
        return this.pathWebDriver;
    }

}
