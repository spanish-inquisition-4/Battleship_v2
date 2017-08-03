package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.boardcontroller.OpponentBoardController;
import com.spanish_inquisition.battleship.client.board.boardcontroller.PlayerBoardController;
import com.spanish_inquisition.battleship.client.network.ResponsesBus;
import com.spanish_inquisition.battleship.client.network.SocketClient;
import com.spanish_inquisition.battleship.common.NetworkMessage;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;
import static com.spanish_inquisition.battleship.common.Header.RESPONSE_OPPONENT_DESTROYED_SHIP;
import static com.spanish_inquisition.battleship.common.Header.RESPONSE_OPPONENT_HIT;
import static com.spanish_inquisition.battleship.common.Header.RESPONSE_OPPONENT_MISS;
import static com.spanish_inquisition.battleship.common.Header.isResponseFieldChanging;

/**
 * @author Michal_Partacz
 */
public class Game {
    SocketClient socketClient;
    ResponsesBus responsesBus;
    String name;
    private static final int GAME_LOOP_SLEEP = 100;
    PlayerBoardController playerBoardController;
    OpponentBoardController opponentBoardController;


    public void buildPlayersBoard(PlayerBoardController boardController) {
        logger.log(DEFAULT_LEVEL, "Building player's board");
        this.playerBoardController = boardController;
        boardController.buildBoard();
    }

    public void buildOpponentsBoard(OpponentBoardController boardController) {
        logger.log(DEFAULT_LEVEL, "Building opponent's board");
        this.opponentBoardController = boardController;
        boardController.buildBoard();
    }

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
        this.responsesBus = socketClient.getResponsesBus();
    }

    public void placePlayersShips() {
        //TODO: consider lazy initialization in loggers since they all accept Suppliers
        logger.log(DEFAULT_LEVEL, "Setting fleet for player");
        playerBoardController.placeShips();
    }

    public String getShipPlacementForServer(){
        return playerBoardController.getMessageForServer();
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
                    case PLAYER_TURN: { /*notify about his move */

                    }
                    case DECIDE_ON_MOVE: {
                        // wait for target point from player
                    }
                    case RESPONSE_HIT: {
                        // recolor targeted point
                    }
                    case RESPONSE_MISS: {
                        // recolor targeted point
                    }
                    case RESPONSE_DESTROYED_SHIP: {
                        // recolor destroyed ship points
                    }
                    case OPPONENT_TURN: {
                        /* notify that it is not his turn */
                        NetworkMessage resultMessage = this.responsesBus.getAServerResponse();
                        while(!(resultMessage.getHeader() == RESPONSE_OPPONENT_HIT
                                || resultMessage.getHeader() == RESPONSE_OPPONENT_MISS )) {
                           resultMessage = this.responsesBus.getAServerResponse();
                        }
                        // recolor targeted point
                        NetworkMessage destroyedShipMessage = this.responsesBus.getAServerResponse();
                        if (destroyedShipMessage.getHeader() == RESPONSE_OPPONENT_DESTROYED_SHIP) {
                            // recolor destroyed ship points
                        }
                    }
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
