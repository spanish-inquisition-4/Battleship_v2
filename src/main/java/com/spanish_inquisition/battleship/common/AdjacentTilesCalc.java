package com.spanish_inquisition.battleship.common;

import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Michal_Partacz
 */
public class AdjacentTilesCalc {

    private static final int IMPORTED_BOARD_SIZE = GameBoardBuilder.BOARD_SIZE_WITH_LABELS -1;
    private static final int OUT_OF_BOARD_INDEX = -1;

    public static List<Integer> getBoardIndexesOfAllAdjacentTilesOf(Integer tile) {
        List<Integer> adjacentFields = new LinkedList<>();
        TileDirection[] enumValues = TileDirection.values();
        for (TileDirection tileDirection: enumValues) {
            int adjacentBoardIndex = getAdjacentTileIndex(tile, tileDirection);
            if(adjacentBoardIndex != OUT_OF_BOARD_INDEX) {
                adjacentFields.add(adjacentBoardIndex);
            }
        }
        return adjacentFields;
    }

    public static int getAdjacentTileIndex(Integer boardIndex, TileDirection tileDirection) {
        return tileDirection.getAdjacentTileIndex(boardIndex);
    }

    public static int translateCoordinatesToIndex(int xCoordinate, int yCoordinate) {
        if (xCoordinate < 0 || xCoordinate > IMPORTED_BOARD_SIZE - 1 || yCoordinate < 0 || yCoordinate > IMPORTED_BOARD_SIZE - 1) {
            return -1;
        }
        return IMPORTED_BOARD_SIZE * (yCoordinate) + (xCoordinate);
    }

    public static int[] translateIndexToCoordinates(int index) {
        int[] coordinates = new int[2];
        coordinates[0] = (index % IMPORTED_BOARD_SIZE); //x coordinate
        coordinates[1] = (index / IMPORTED_BOARD_SIZE); //y coordinate
        return coordinates;
    }
}