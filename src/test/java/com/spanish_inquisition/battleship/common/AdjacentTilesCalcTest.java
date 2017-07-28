package com.spanish_inquisition.battleship.common;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacentTilesCalcTest {

    @DataProvider(name = "IndexesProvider")
    private Object[][] indexesProvider() {
        return new Object[][] {
                {0, new int[] {0,0}},
                {1, new int[] {1,0}},
                {9, new int[] {9,0}},
                {10, new int[] {0,1}},
                {11, new int[] {1,1}},
                {38, new int[] {8,3}},
                {65, new int[] {5,6}},
                {99, new int[] {9,9}}
        };
    }

    @DataProvider(name = "CoordinatesProvider")
    public Object[][] coordinatesProvider(){
        return new Object[][] {
                {new int[] {-5, 3}, -1 },
                {new int[] {15, 3}, -1 },
                {new int[] {5, -3}, -1 },
                {new int[] {5, 13}, -1 },
                {new int[] {0, 10}, -1 },
                {new int[] {10, 0}, -1 },
                {new int[] {0, 0}, 0 },
                {new int[] {0, 9}, 90 },
                {new int[] {1, 1}, 11 },
                {new int[] {3, 6}, 63 },
                {new int[] {7, 5}, 57 },
                {new int[] {9, 9}, 99 },
        };
    }

    @DataProvider(name = "TileToTheLeftProvider")
    public Object[][] tileToTheLeftProvider() {
        return new Object[][]{
                {33, 32}, //random tile
                {3, 2}, //tile from first row
                {96, 95}, //tile from last row
                {10, -1}, //first tile in row
                {89, 88}, //last tile in row
        };
    }

    @DataProvider(name = "TileToTheRightProvider")
    public Object[][] tileToTheRightProvider() {
        return new Object[][]{
                {44, 45}, //random tile
                {2, 3}, //tile from first row
                {95, 96}, //tile from last row
                {10, 11}, //first tile in row
                {89, -1}, //last tile in row
        };
    }

    @DataProvider(name = "TileAboveProvider")
    public Object[][] tileAboveProvider() {
        return new Object[][]{
                {44, 34}, //random tile
                {2, -1}, //tile from first row
                {95, 85}, //tile from last row
                {10, 0}, //first tile in row
                {89, 79}, //last tile in row
        };
    }

    @DataProvider(name = "TileBelowProvider")
    public Object[][] tileBelowProvider() {
        return new Object[][]{
                {44, 54}, //random tile
                {2, 12}, //tile from first row
                {95, -1}, //tile from last row
                {10, 20}, //first tile in row
                {89, 99}, //last tile in row
        };
    }

    @DataProvider(name = "UpperLeftTileProvider")
    public Object[][] upperLeftTileProvider() {
        return new Object[][]{
                {44, 33}, //random tile
                {2, -1}, //tile from first row
                {95, 84}, //tile from last row
                {10, -1}, //first tile in row
                {89, 78}, //last tile in row
        };
    }

    @DataProvider(name = "LowerLeftTileProvider")
    public Object[][] lowerLeftTileProvider() {
        return new Object[][]{
                {44, 53}, //random tile
                {2, 11}, //tile from first row
                {95, -1}, //tile from last row
                {10, -1}, //first tile in row
                {89, 98}, //last tile in row
        };
    }

    @DataProvider(name = "UpperRightTileProvider")
    public Object[][] upperRightTileProvider() {
        return new Object[][]{
                {44, 35}, //random tile
                {2, -1}, //tile from first row
                {95, 86}, //tile from last row
                {10, 1}, //first tile in row
                {89, -1}, //last tile in row
        };
    }

    @DataProvider(name = "LowerRightTileProvider")
    public Object[][] lowerRightTileProvider() {
        return new Object[][]{
                {44, 55}, //random tile
                {2, 13}, //tile from first row
                {95, -1}, //tile from last row
                {10, 21}, //first tile in row
                {89, -1}, //last tile in row
        };
    }

    @DataProvider(name = "AdjacentTilesProvider")
    public Object[][] adjacentTilesProvider() {
        return new Object[][]{
                {44, new HashSet<>(Arrays.asList(33, 34, 35, 45, 55, 54, 53, 43))}, //random tile
                {2, new HashSet<>(Arrays.asList(1, 3, 11, 12, 13))}, //tile from first row
                {95, new HashSet<>(Arrays.asList(94, 96, 84, 85, 86))}, //tile from last row
                {10, new HashSet<>(Arrays.asList(0, 20, 1, 11, 21))}, //first tile in row
                {89, new HashSet<>(Arrays.asList(79, 99, 78, 88, 98))}, //last tile in row
                {0, new HashSet<>(Arrays.asList(1, 10, 11))}, //left upper corner
                {9, new HashSet<>(Arrays.asList(8, 18, 19))}, //left lower corner
                {90, new HashSet<>(Arrays.asList(80, 81, 91))}, //right upper corner
                {99, new HashSet<>(Arrays.asList(88, 89, 98))}, //right lower corner
        };
    }

    @Test(dataProvider = "IndexesProvider")
    public void shouldTranslateIndexToCoordinates(int index, int[] expectedCoordinates){
        // When
        int[] calculatedCoordinates = AdjacentTilesCalc.translateIndexToCoordinates(index);

        // Then
        Assert.assertEquals(expectedCoordinates[0], calculatedCoordinates[0]);
        Assert.assertEquals(expectedCoordinates[1], calculatedCoordinates[1]);
    }

    @Test(dataProvider = "CoordinatesProvider")
    public void shouldTranslateCoordinatesToIndex(int[] coordinates, int expectedIndex){
        // When
        int calculatedIndex = AdjacentTilesCalc.translateCoordinatesToIndex(coordinates[0],coordinates[1]);

        // Then
        Assert.assertEquals(calculatedIndex, expectedIndex);
    }

    @Test(dataProvider = "TileToTheLeftProvider")
    public void shouldGetBoardIndexOfTileToTheLeft(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfTileToTheLeft = AdjacentTilesCalc.getBoardIndexOfTileToTheLeft(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileToTheLeft, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "TileToTheRightProvider")
    public void shouldGetBoardIndexOfTileToTheRight(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfTileToTheRight = AdjacentTilesCalc.getBoardIndexOfTileToTheRight(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileToTheRight, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "TileAboveProvider")
    public void shouldGetBoardIndexOfTileAbove(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfTileAbove = AdjacentTilesCalc.getBoardIndexOfTileAbove(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileAbove, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "TileBelowProvider")
    public void shouldGetBoardIndexOfTileBelow(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfTileBelow = AdjacentTilesCalc.getBoardIndexOfTileBelow(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileBelow, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "UpperLeftTileProvider")
    public void shouldGetBoardIndexOfUpperLeftTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfUpperLeftTile = AdjacentTilesCalc.getBoardIndexOfUpperLeftTile(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfUpperLeftTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "LowerLeftTileProvider")
    public void shouldGetBoardIndexOfLowerLeftTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfLowerLeftTile = AdjacentTilesCalc.getBoardIndexOfLowerLeftTile(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfLowerLeftTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "UpperRightTileProvider")
    public void shouldGetBoardIndexOfUpperRightTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfUpperRightTile = AdjacentTilesCalc.getBoardIndexOfUpperRightTile(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfUpperRightTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "LowerRightTileProvider")
    public void shouldGetBoardIndexOfLowerRightTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //when
        int boardIndexOfLowerRightTile = AdjacentTilesCalc.getBoardIndexOfLowerRightTile(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfLowerRightTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "AdjacentTilesProvider")
    public void shouldGetBoardIndexesOfAllAdjacentTilesOf(int boardIndex, Set<Integer> expectedBoardIndexesOfAdjacentTiles) {
        //when
        List<Integer> boardIndexesOfAdjacentTilesList = AdjacentTilesCalc.getBoardIndexesOfAllAdjacentTilesOf(boardIndex);
        Set<Integer> boardIndexesOfAdjacentTiles = new HashSet<>(boardIndexesOfAdjacentTilesList);
        //then
        Assert.assertEquals(boardIndexesOfAdjacentTiles, expectedBoardIndexesOfAdjacentTiles);
    }
}