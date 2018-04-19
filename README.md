<a href="https://travis-ci.org/research-iobserve/selenium-workloads"><img src="https://travis-ci.org/research-iobserve/selenium-workloads.svg?branch=master" alt="Build Status"></a>

# Experiment Workloads utilizing Selenium

This project is meant to create different workloads for websites utilizing Selenium.
Currently,  this tool is tailored for the software systems CoCoME and JPetStore which we use as case studies in iObserve.

**This project is work in progress. Please report feature request, bugs etc. as issues in GitHub or to mad@informatik.uni-kiel.de** 

# Dependencies and Requirements

To use our workload drivers
1. Create a experiment setup directory `mkdir experiment`
1. Download phantom.js (http://phantomjs.org/download.html) Usually, the package in your Linux distribution won't suffice.
1. Extract the package, e.g., with `tar -xvjpf phantomjs-2.1.1-linux-x86_64.tar.bz2` in experiment
1. Checkout this repository alongside of `phantomjs-2.1.1-linux-x86_64`
1. Change into the workload directory `cd selenium-workloads`
1. Build the repository with `./gradlew build`
1. After the build you find a completed package of the software in ` build/distributions/selenium-experiment-workloads-1.0.tar|zip`
1. Unzip the package in your preferred location `$BASE` with `tar` or `unzip`

# Execution of Workloads

The workloads are meant to be executed within scripts.
Therefore, the execution is configured with parameters to be able to adapt the workloads to the criteria relevant for our experiments.
There exist several required and optional parameters.

To run the application call: `$BASE/bin/



### Required Parameters

To execute the different workloads two parameters are required:
- `-phantomjs` to set the path to the phantomjs browser, <br> 
e.g. `-phantomjs /usr/lib/phantomjs/phantomjs` <br>
- `-workloads` to define the workloads that should be executed, <br>
e.g. `-workloads workload1,workload2` or `-workloads workload1  -workloads workload2` <br>

### Workload Overview

-print_workloads if used, simply prints all available workloads <br> 
E.g.: `java -jar build/libs/selenium-experiment-workloads-1.0.jar -print_workloads`

### Optional Parameters

- `-url` sets the url of the tested service, default is https://172.17.0.2:8181 for the CoCoME frontend from our [experiment](https://github.com/research-iobserve/cocome-experiment)
- `-runs` the number of times each workload is repeated, default is 5

# Implementation of new Workloads

Each workload consists of several tasks that are build in the builder pattern.
Thereby, every task implements the implements the Java (Bi-)Consumer Interface and can be reused.
New user and system tasks ( like creating a new session) can easily be implemented.
The workloads contain these tasks and are designed to represent one complete run of the defined user actions.
Each new workload has to be put in the [WorkloadRegistry](https://github.com/research-iobserve/cocome-selenium-workloads/blob/master/src/main/java/org/iobserve/selenium/workloads/registry/WorkloadRegistry.java) to be able to be used!
Multiple workloads can be called at the application start.

## WorkloadConfiguration
Each Workload is executed with a certain configuartion.
In most cases, the configuration is build from the (default) parameters and set for each application call.
For special cases, like for static websites, the configuration can be manually set in the implementation of the workload. 
Preset configurations can currently not be overwritten by the parameters.



