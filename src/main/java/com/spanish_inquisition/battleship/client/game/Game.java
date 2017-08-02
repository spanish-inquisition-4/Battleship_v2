package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.boardcontroller.BoardController;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 */
public class Game {
    BoardController playerBoardController;
    BoardController opponentBoardController;

    public void buildPlayersBoard(BoardController boardController) {
        logger.log(DEFAULT_LEVEL, "Building player's board");
        this.playerBoardController = boardController;
        boardController.buildPlayersBoard();
    }

    public void buildOpponentsBoard(BoardController boardController) {
        logger.log(DEFAULT_LEVEL, "Building opponent's board");
        this.opponentBoardController = boardController;
        boardController.buildOpponentsBoard();
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