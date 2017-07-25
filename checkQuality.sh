#! /bin/bash

#create quality analysis
mvn clean install
mvn site
mvn sonar:sonar

#display results
firefox target/site/index.html -new-window http://localhost:9000
