package com.spanish_inquisition.battleship.client.board;

import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal_Partacz
 * The class which holds a grid pane of buttons and has methods which provide easier access to them
 */
public class GameBoard {
    private GridPane gridPane;
    private Map<Integer, BoardTile> indexTiles = new HashMap<>();

    public GameBoard(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    void addBoardTileToHashMap(int boardIndex, BoardTile tile) {
        this.indexTiles.put(boardIndex, tile);
    }

    public Map<Integer, BoardTile> getIndexTiles() {
        return indexTiles;
    }
}