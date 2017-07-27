package com.spanish_inquisition.battleship.common;

import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;

/**
 * @author Michal_Partacz
 */
public class AdjacentFieldsCalc {

    private static final int IMPORTED_BOARD_SIZE = GameBoardBuilder.BOARD_SIZE_WITH_LABELS -1;

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
