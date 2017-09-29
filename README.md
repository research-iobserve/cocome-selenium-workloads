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
-url sets the url of the tested service, default is https://172.17.0.2:8181 for the CoCoME frontend from our [[experiment | https://github.com/research-iobserve/cocome-experiment  ]]<br> 
-runs<br> 
-print_workloads <br> 
# Implementation of new Workloads
