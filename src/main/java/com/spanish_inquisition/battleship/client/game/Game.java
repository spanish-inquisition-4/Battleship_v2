package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.BoardController;
import com.spanish_inquisition.battleship.client.network.ResponsesBus;
import com.spanish_inquisition.battleship.client.network.SocketClient;
import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 */
public class Game {
    BoardController boardController;
    SocketClient socketClient;
    ResponsesBus responsesBus;
    String name;

    public void buildPlayersBoard(BoardController boardController) {
        logger.log(DEFAULT_LEVEL, "Building players board");
        this.boardController = boardController;
        boardController.buildPlayersBoard();
    }

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        this.responsesBus = socketClient.getResponsesBus();
    }

    public void placePlayersShips() {
        logger.log(DEFAULT_LEVEL, "Setting fleet for player");
        boardController.placeShips();
    }

    public String getShipPlacementForServer() {
        return boardController.getMessageForServer();
    }

    public void runTheGame() {
        if (this.socketClient != null) {
            while (true) {
                waitInThisThreadFor(100);
                if (this.responsesBus.hasServerResponses()) {
                    NetworkMessage message = this.responsesBus.getAServerResponse();
                    System.out.println("s = " + message);
                    if (message == null) {
                        continue;
                    }
                    if (message.getHeader() == Header.PLAYER_TURN) {
                        //notify about his move
                    } else if (message.getHeader() == Header.OPPONENT_TURN) {
                        // notify that it is not his turn
                    } else if (Header.isResponseFieldChanging(message.getHeader())) {
                        // make changes to the opponent's board
                    } else if (message.getHeader() == Header.GAME_WON) {
                        break;
                        // notify the player he won or lost
                    }
                }
            }
        }
    }

    private void waitInThisThreadFor(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void acceptPlayersName(String name) {
        this.name = name;
        if (socketClient != null) {
            socketClient.sendStringToServer(name);
        }
    }
}