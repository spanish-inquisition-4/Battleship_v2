package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.boardcontroller.BoardController;
import com.spanish_inquisition.battleship.client.board.BoardTile;
import com.spanish_inquisition.battleship.common.AppLogger;
import com.spanish_inquisition.battleship.common.Styles;
import javafx.application.Platform;

import java.util.List;
import java.util.Map;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;

public class FleetInitializer {

    private BoardController boardController;
    private Map<Integer, BoardTile> boardTiles;
    private List<Integer> shipPlaces;

    public List<Integer> getShipPlaces() {
        return shipPlaces;
    }

    public FleetInitializer(BoardController boardController) {
        this.boardController = boardController;
    }

    public void setUpShips() {
        boardTiles = boardController.getBoardsIndexTiles();
        shipPlaces = Fleet.getRandomFleet().getShipPlaces();
        Platform.runLater(() -> shipPlaces.forEach(this::changeTileStyle));
    }

    private void changeTileStyle(int tileId) {
        BoardTile boardTile = boardTiles.get(tileId);
        String style = Styles.SHIP_PLACED_COLOR;
        AppLogger.logger.log(DEFAULT_LEVEL, "Setting new color of tile " + tileId + " to " + style);
        boardTile.setStyle(style);
    }
}