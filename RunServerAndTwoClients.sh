#!/bin/bash
mvn exec:java -Dexec.mainClass="com.spanish_inquisition.battleship.server.BattleshipServer" &
mvn exec:java -Dexec.mainClass="com.spanish_inquisition.battleship.client.Client" &
mvn exec:java -Dexec.mainClass="com.spanish_inquisition.battleship.client.Client" &