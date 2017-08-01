#! /bin/bash

set -e # when any of the script fail, it will return the non-zer code immediately

# run trello board
firefox https://trello.com/b/ONK9r1ZU/battleships &

#create quality analysis
mvn clean install
mvn site
mvn sonar:sonar

#display results
firefox -new-tab http://localhost:9000 -new-tab target/site/index.html &
