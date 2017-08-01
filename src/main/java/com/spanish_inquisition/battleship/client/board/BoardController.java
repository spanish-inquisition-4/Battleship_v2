package com.spanish_inquisition.battleship.client.board;

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
public class BoardController {
    private GameBoard gameBoard;
    private FleetInitializer fleetInitializer;

    public BoardController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void buildPlayersBoard() {
        GameBoardBuilder gameBoardBuilder = new GameBoardBuilder(this);
        fleetInitializer = new FleetInitializer(this);
        gameBoardBuilder.buildGameBoard();
    }

    GridPane getBoardGridPane() {
        return this.gameBoard.getGridPane();
    }

    GameBoard getGameBoard() {
        return gameBoard;
    }

    public Map<Integer, BoardTile> getBoardsIndexTiles() {
        return gameBoard.getIndexTiles();
    }

    public void placeShips() {
        Map<Integer, BoardTile> indexTiles = gameBoard.getIndexTiles();
        Platform.runLater(() -> indexTiles.forEach((integer, boardTile) -> boardTile.setTileStyle(Styles.DEFAULT_TILE_COLOR, Styles.TEXT_BLACK)));
        fleetInitializer.setUpShips();
    }

    public String getMessageForServer() {
        return ServerMessageCreator.createFleetMessage(fleetInitializer.getShipPlaces());
    }
}