package com.spanish_inquisition.battleship.client.board.boardcontroller;

import com.spanish_inquisition.battleship.client.board.GameBoard;
import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;

public class OpponentBoardController extends BoardController{
    public OpponentBoardController(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    public void buildBoard() {
        GameBoardBuilder gameBoardBuilder = new GameBoardBuilder(this);
        gameBoardBuilder.buildGameBoard();
    }
}
