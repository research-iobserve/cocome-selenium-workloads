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
package org.iobserve.selenium.workloadgeneration;

import org.iobserve.selenium.common.CommandlineArguments;

import com.beust.jcommander.JCommander;

/**
 * Generates workloads for CoCoME. Designed to be used with the iObserve Experiment. Uses Selenium
 * to execute the workloads.
 *
 * @author Christoph Dornieden
 * @author Marc Adolf
 */
public class WorkloadGeneration {

    public static void main(final String[] args) {

        final CommandlineArguments arguments = new CommandlineArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        System.out.println(arguments.getPathPhantomjs());

        // TODO enable screenshots
        // TODO better way to choose different workloads.
        // TODO using the same session for multiple tasks

        // final AbstractWorkloadPlan workload = new
        // TestWorkloadPlan(CommandlineArguments.getBaseUrl(),
        // CommandlineArguments.getNumberOfRuns(), arguments.getPathPhantomjs());
        // workload.executeWorkloadPlan();

        System.out.println("Finished");

    }
}
