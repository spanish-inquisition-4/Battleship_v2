package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.common.Header;

import java.util.List;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;
import static com.spanish_inquisition.battleship.server.BattleshipServer.requestBus;

public class BattleshipGame {
    List<ClientConnectionHandler> players;
    boolean gameIsRunning = true;

    public BattleshipGame(List<ClientConnectionHandler> clients) {
        players = clients;
    }

    public void proceed() {
        while(gameIsRunning) {
            for (ClientConnectionHandler player: players) {
                System.out.println("checking message from player " + player.getPlayerName());
                int playerId = player.getClientId();
                if(requestBus.haveMessageFromSender(playerId)){
                    String message = requestBus.getMessageFrom(playerId).getContent();
                    if(message.contains(Header.MOVE_REGULAR.name())) {
                        logger.log(DEFAULT_LEVEL, "Player clicked on field:" + message.substring(message.indexOf(":") + 1, message.length()));
                        gameIsRunning = false;
                    }
                }
            }
        }
    }
}
