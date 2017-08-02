package com.spanish_inquisition.battleship.client.board.boardcontroller;

import com.spanish_inquisition.battleship.client.board.BoardTile;
import com.spanish_inquisition.battleship.client.board.GameBoard;
import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;
import com.spanish_inquisition.battleship.client.game.FleetInitializer;
import com.spanish_inquisition.battleship.client.game.ServerMessageCreator;
import com.spanish_inquisition.battleship.common.Styles;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.util.Map;

/**
 * @author Michal_Partacz
 * The method has a GameBoard object and mainly has methods which will operate on it.
 */
public abstract class BoardController {
    protected GameBoard gameBoard;

    public BoardController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public abstract void buildBoard();

    GridPane getBoardGridPane() {
        return this.gameBoard.getGridPane();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Map<Integer, BoardTile> getBoardsIndexTiles() {
        return gameBoard.getIndexTiles();
    }

    public String getMessageForServer() {
        return ServerMessageCreator.createFleetMessage(fleetInitializer.getShipPlaces());
    }
}
