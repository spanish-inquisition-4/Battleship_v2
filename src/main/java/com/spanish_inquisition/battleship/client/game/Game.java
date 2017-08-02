package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.BoardController;
import com.spanish_inquisition.battleship.client.network.ResponsesBus;
import com.spanish_inquisition.battleship.client.network.SocketClient;
import com.spanish_inquisition.battleship.common.NetworkMessage;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;
import static com.spanish_inquisition.battleship.common.Header.*;

/**
 * @author Michal_Partacz
 */
public class Game {
    BoardController boardController;
    SocketClient socketClient;
    ResponsesBus responsesBus;
    String name;
    private static final int GAME_LOOP_SLEEP = 100;

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

    public void runTheGame() throws InterruptedException, NullPointerException {
        game_loop:
        while (true) {
            Thread.sleep(GAME_LOOP_SLEEP);
            if (this.responsesBus.hasServerResponses()) {
                NetworkMessage message = this.responsesBus.getAServerResponse();
                if (message == null) {
                    continue;
                }
                switch (message.getHeader()) {
                    case PLAYER_TURN: { /*notify about his move */ }
                    case OPPONENT_TURN: { /* notify that it is not his turn */ }
                    case GAME_WON: {
                        break game_loop;/*notify the player he won or lost */
                    }
                }
                if (isResponseFieldChanging(message.getHeader())) {
                    // make changes to the opponent's board
                }
            }
        }

    }

    public void acceptPlayersName(String name) {
        this.name = name;
        if (socketClient != null) {
            socketClient.sendStringToServer(name);
        }
    }
}