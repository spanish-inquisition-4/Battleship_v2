package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.BoardController;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 */
public class Game {
    BoardController boardController;

    public void buildPlayersBoard(BoardController boardController) {
        logger.log(DEFAULT_LEVEL, "Building players board");
        this.boardController = boardController;
        boardController.buildPlayersBoard();
    }

    public void placePlayersShips() {
        logger.log(DEFAULT_LEVEL, "Setting fleet for player");
        boardController.placeShips();
    }

    public String getShipPlacementForServer(){
        return boardController.getMessageForServer();
    }
}