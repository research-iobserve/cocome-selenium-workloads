#!/bin/bash

BINDIR=$(cd "$(dirname "$0")"; pwd)

SELENIUM_WORKLOAD_DRIVER="$BINDIR/build/libs/selenium-experiment-workloads-1.0.jar"
PHANTOMJS="$BINDIR/../phantomjs-2.1.1-linux-x86_64/bin/phantomjs"

java -jar "${SELENIUM_WORKLOAD_DRIVER}" -phantomjs "${PHANTOMJS}" -workloads $1 -fuzzy -runs 20

# end
