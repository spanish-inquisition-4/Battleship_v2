#!/bin/bash
mvn clean install
java -jar target/server.jar &
echo Server running
java -jar target/client.jar &
echo First client running
java -jar target/client.jar &
echo Second client running

