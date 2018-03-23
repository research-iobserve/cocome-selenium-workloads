<a href="https://travis-ci.org/research-iobserve/selenium-workloads"><img src="https://travis-ci.org/research-iobserve/selenium-workloads.svg?branch=master" alt="Build Status"></a>

# Selenium-experiment-workloads
This project ist meant to create different workloads for the Websites utilizing Selenium.
Currently,  this tool is tailored for the software systems CoCoME and JPetStore which we use as case studies in iObserve.

**This project is work in progress. Please report feature request, bugs etc. as issues in GitHub or to mad@informatik.uni-kiel.de** 

# Dependencies and Requirements

To use our workload drivers
1. Download phantom.js (http://phantomjs.org/download.html) Usually, the package in your Linux distribution won't suffice.
1. Checkout this repository
1. Build the repository with `./gradlew build`


# Execution of Workloads
The workloads are meant to be executed within scripts.
Therefore, the execution is configured with parameters to be able to adapt the workloads to the criteria relevant for our experiments.
There exist several required and optional parameters.

### Required Parameters
To execute the different workloads two parameters are required:
- `-phantomjs` to set the path to the phantomjs browser, <br> 
e.g. `-phantomjs /usr/lib/phantomjs/phantomjs` <br>
- `-workloads` to define the workloads that should be executed, <br>
e.g. `-workloads workload1,workload2` or `-workloads workload1  -workloads workload2` <br>

E.g. to execute the Test workload: <br>
`java -jar build/libs/selenium-experiment-workloads-1.0.jar -phantomjs
/usr/lib/phantomjs/phantomjs -workloads Test`

### Workload Overview
-print_workloads if used, simply prints all available workloads <br> 
E.g.: `java -jar build/libs/selenium-experiment-workloads-1.0.jar -print_workloads`

### Optional Parameters
-url sets the url of the tested service, default is https://172.17.0.2:8181 for the CoCoME frontend from our [experiment](https://github.com/research-iobserve/cocome-experiment) <br>
-runs the number of times each workload is repeated to increase statiscal relevance, default is 5 <br> 

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



