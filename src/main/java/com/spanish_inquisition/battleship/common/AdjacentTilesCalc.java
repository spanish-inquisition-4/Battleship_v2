package com.spanish_inquisition.battleship.common;

import com.spanish_inquisition.battleship.client.board.BoardTile;
import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Michal_Partacz
 */
public class AdjacentTilesCalc {

    private static final int IMPORTED_BOARD_SIZE = GameBoardBuilder.BOARD_SIZE_WITH_LABELS -1;

    public enum TileDirection {
        LEFT,
        RIGHT,
        ABOVE,
        BELOW,
        UPPER_LEFT,
        UPPER_RIGHT,
        LOWER_LEFT,
        LOWER_RIGHT
    }

    public static List<Integer> getBoardIndexesOfAllAdjacentTilesOf(Integer tile) {
        List<Integer> adjacentFields = new LinkedList<>();
        TileDirection[] enumValues = TileDirection.values();
        for (TileDirection tileDirection: enumValues) {
            int adjacentBoardIndex = getAdjacentTileIndex(tile, tileDirection);
            if(adjacentBoardIndex != -1) {
                adjacentFields.add(adjacentBoardIndex);
            }
        }
        return adjacentFields;
    }

    public static int getAdjacentTileIndex(BoardTile boardTile, TileDirection tileDirection) {
        return getAdjacentTileIndex(boardTile.getBoardIndex(), tileDirection);
    }

    public static int getAdjacentTileIndex(Integer boardIndex, TileDirection tileDirection) {
        switch (tileDirection) {
            case LEFT: return getBoardIndexOfTileToTheLeft(boardIndex);
            case RIGHT: return getBoardIndexOfTileToTheRight(boardIndex);
            case ABOVE: return getBoardIndexOfTileAbove(boardIndex);
            case BELOW: return getBoardIndexOfTileBelow(boardIndex);
            case UPPER_LEFT: return getBoardIndexOfUpperLeftTile(boardIndex);
            case UPPER_RIGHT: return getBoardIndexOfUpperRightTile(boardIndex);
            case LOWER_LEFT: return getBoardIndexOfLowerLeftTile(boardIndex);
            case LOWER_RIGHT: return getBoardIndexOfLowerRightTile(boardIndex);
            default: return -1;
        }
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

    static int getBoardIndexOfTileToTheLeft(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0] - 1, coordinates[1]);
    }

    static int getBoardIndexOfTileToTheRight(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0] + 1, coordinates[1]);
    }

    static int getBoardIndexOfTileAbove(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0], coordinates[1] - 1);
    }

    static int getBoardIndexOfTileBelow(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0], coordinates[1] + 1);
    }

    static int getBoardIndexOfUpperLeftTile(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0] - 1, coordinates[1] - 1);
    }

    static int getBoardIndexOfLowerLeftTile(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0] - 1, coordinates[1] + 1);
    }

    static int getBoardIndexOfUpperRightTile(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0] + 1, coordinates[1] - 1);
    }

    static int getBoardIndexOfLowerRightTile(Integer boardIndex) {
        int[] coordinates = translateIndexToCoordinates(boardIndex);
        return translateCoordinatesToIndex(coordinates[0] + 1, coordinates[1] + 1);
    }
}