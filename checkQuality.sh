#! /bin/bash

#create quality analisys
mvn clean install
mvn site
mvn sonar:sonar

#display results
firefox target/site/index.html -new-window http://localhost:9000
