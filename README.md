<a href="https://travis-ci.org/research-iobserve/selenium-workloads"><img src="https://travis-ci.org/research-iobserve/selenium-workloads.svg?branch=master" alt="Build Status"></a>

# Selenium-experiment-workloads
This project ist meant to create different workloads for the experiment with cocome utilizing Selenium 

# Execution of Workloads
The workloads are meant to be executed within scripts.
Therefore, the execution was planned to be controlled with paramters to be able to adapt the workloads to the needed criteria.
There exist several necessary and optional parameters.

### Required Parameters
To execute the different workloads two parameters are needed.

-phantomjs to set the path to the phantomjs browser, <br> 
e.g. `-phantomjs /usr/lib/phantomjs/` <br>
-workloads to define the workloads that should be executed, <br>
e.g. `-workloads workload1,workload2` or `-workloads workload1  -workloads workload2`

### Optional Parameters
-url sets the url of the tested service, default is https://172.17.0.2:8181 for the CoCoME frontend from our [experiment](https://github.com/research-iobserve/cocome-experiment) <br>
-runs the number of times each workload is repeated to increase statiscal relevance, default is 5 <br> 
-print_workloads if used, simply prints all available workloads <br> 

# Implementation of new Workloads

Each workload consists of several tasks that are build in the builder pattern.
Thereby, every task implements the implements the Java (Bi-)Consumer Interface and can be reused.
New user and system tasks ( like creating a new session) can easily be implemented.
The workloads contain these tasks and are designed to represent one complete run of the defined user actions.
Each new workload has to be entered in the [WorkloadRegistry](https://github.com/research-iobserve/cocome-selenium-workloads/blob/master/src/main/java/org/iobserve/selenium/workloads/registry/WorkloadRegistry.java) to be able to be used!
Multiple workloads can be called at the application start.

