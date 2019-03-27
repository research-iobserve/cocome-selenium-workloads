<a href="https://travis-ci.org/research-iobserve/selenium-workloads"><img src="https://travis-ci.org/research-iobserve/selenium-workloads.svg?branch=master" alt="Build Status"></a>

# Experiment Workloads utilizing Selenium

This project is meant to create different workloads for websites utilizing Selenium.
Currently,  this tool is tailored for the software systems PetStore which we use as case studies in iObserve.

**This project is work in progress. Please report feature request, bugs etc. as issues in GitHub**

# Dependencies and Requirements

Our workload driver is considered to be used alongside the JPetStore in experiments. We usually set up our experiments with one experiment main directory (here `experiment`), a `tools` directory for all parts of software, and a `workloads` directory for the workloads.

Currently, we use the chrome driver for selenium. If you want to can use other drivers (e.g. phantomjs which we used previously).

# Installation

Install the chrome driver for Selenium:
* Download it from `http://chromedriver.chromium.org/downloads`
* Copy it into your `tools` directory

To use our workload driver directly from this git repository:
1. Clone the repository `git clone https://github.com/research-iobserve/selenium-workloads.git`
1. Switch to the repository `cd selenium-workloads`
1. Build the repository with `./gradlew build`
1. After the build you find a completed package of the software in `build/distributions/selenium-experiment-workloads-1.0.tar|zip`
1. Unzip or untar the package in your preferred location, e.g., `experiment/tools`, with `tar` or `unzip`

# Preparation

You find a in the root directory of `selenium-workloads` an example workload configuration. You can make of copy of it and start using this for your workloads. Before first execution, you have to set up the web driver parameters. They look like this:
```
webDriverConfiguration:
  baseUrl: http://172.18.0.5:8080/jpetstore-frontend/
  type: org.iobserve.selenium.behavior.ChromeDriverFactory
  driver: experiments/tools/chromedriver
  timeout: 60000
```

1. The `baseUrl` is the URL of your JPetStore. The path `jpetstore-frontend` is the name used for the distributed variants of the JPetStore. The single service variants use `jpetstore`
1. The `type` of webdriver should be `ChromeDriverFactory` if you want to go with the Chrome driver (recommended).
1. The `driver` parameter must contain the path to the `chromedriver` executable.

In case you want to setup logging, use the following in your bash script or command line:
```
SELENIUM_EXPERIMENT_WORKLOADS_OPTS=-Dlog4j.configuration=file:///$BASE_DIR/log4j.cfg
```
Two log4j configurations are provided in this repository.

# Execution of Workloads

Execute the script with: `experiment/tools/selenium-experiment-workloads-1.0/bin/selenium-workloads -c your-workload.yaml`
Or in Windows: `experiment/tools/selenium-experiment-workloads-1.0/bin/selenium-workloads.bat -c your-workload.yaml`




