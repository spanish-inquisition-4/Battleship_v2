package com.spanish_inquisition.battleship.client.board.boardcontroller;

import com.spanish_inquisition.battleship.client.board.GameBoard;
import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;
import com.spanish_inquisition.battleship.client.game.Game;

public class OpponentBoardController extends BoardController{

    public OpponentBoardController(GameBoard gameBoard, Game game) {
        super(gameBoard, game);
    }

    @Override
    public void buildBoard() {
        GameBoardBuilder gameBoardBuilder = new GameBoardBuilder(this);
        gameBoardBuilder.fillTheBoardWithButtonsAndLabels();
    }

    public void setBoardDisabled(boolean disable) {
        gameBoard.getGridPane().setDisable(disable);
    }
}
