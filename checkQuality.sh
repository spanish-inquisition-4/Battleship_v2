#! /bin/bash

set -e # when any of the script fail, it will return the non-zer code immediately

#create quality analysis
mvn clean install
mvn site
mvn sonar:sonar

#display results
firefox http://localhost:9000 target/site/index.html &
