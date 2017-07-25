# About

Java implementation of Battleship game (client-server architecture with simple GUI in JavaFX).<br />
See game rules: https://en.wikipedia.org/wiki/Battleship_game<br />
One additional feature is added (Nuke - see Game features)

# How to run the game?

It's not possible to run the game at this stage.

# Code quality verification:

- Run SonarQube server on default port (9000)
- Run **checkQuality.sh** <br />
This script will generate Checkstyle, Findbugs, JaCoCo and Sonar reports. 
It also opens index.html and sonar reports in Mozilla browser.

Reports will be created in:
- **/target/site/index.html** (Checkstyle, Findbugs report)
- **/target/site/jacoco/** (directory with JaCoCo reports)
- **/target/sonar/** (directory with sonar generated files)

# Functional requirements:

- One game only
- 10x10 board
- Fleet consists of: 4-mast ship, 2 3-mast ships, 3 2-mast ships and 4 1-mast ships.
- Winner has ships remaining while loser has none.
- Game messages have configurable target: console (System.out, System.err) or logs or external printer.
- We are bi-lingual: Polish and English are fine. In future we want to add more languages: messages are to be read from a file for chosen language. Choosing the language depends on configuration variable.

# Game features:

- Drawing the boards for a player (fleet board has player's fleet and where opponent shot, "seen" board has where player fired and what he has shot).
- Placing the fleet - diagonal placing is disallowed, only horizontal and vertical. Humans can place ships but they can also choose to randomize placement. Ships cannot touch (no adjacent field to a ship can have a ship). Ships can be partially vertical and partially horizontal, if they have the length.
- Firing the shot - choose a place, shoot. If you hit, you repeat the shot. You can repeat as many times as you hit.
- Hitting the ship - hit happens when place chosen has enemy ship. Mark this part of ship as hit, ask for another shot. One can repeat the shot into already hit (or even sunken) ship, but this doesn't give the right to another shot.
- Missing the ship - misses are marked on "seen" board. One can shoot twice in the same place if it's a miss.
- Sinking the ship - if all masts of a ship are hit, ship sinks. Once the ship has sunk, mark all adjacent fields as "missed", since none of them can have a ship anyway.
- Sinking last ship, that is, winning.
- Nuke - thrice per game player chooses a 3x3 area and "nukes" it, that is, all ships within take damage as if shot. This is done in addition to normal shot. Only 4-mast ship has nukes, so once they are sunk, nukes cannot be used.

# TODO List

[Trello playersBoard](https://trello.com/b/ONK9r1ZU/battleships)
