package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.boardcontroller.BoardController;
import com.spanish_inquisition.battleship.client.board.boardcontroller.OpponentBoardController;
import com.spanish_inquisition.battleship.client.board.boardcontroller.PlayerBoardController;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 */
public class Game {
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

    public void placePlayersShips() {
        //TODO: consider lazy initialization in loggers since they all accept Suppliers
        logger.log(DEFAULT_LEVEL, "Setting fleet for player");
        playerBoardController.placeShips();
    }

    public String getShipPlacementForServer(){
        return playerBoardController.getMessageForServer();
    }
}