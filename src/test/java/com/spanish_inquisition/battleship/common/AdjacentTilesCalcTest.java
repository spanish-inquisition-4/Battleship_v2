package com.spanish_inquisition.battleship.common;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjacentTilesCalcTest {

    @DataProvider
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

    @DataProvider
    private Object[][] coordinatesProvider(){
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

    @DataProvider
    private Object[][] adjacentTilesProvider() {
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

    @Test(dataProvider = "indexesProvider")
    public void shouldTranslateIndexToCoordinates(int index, int[] expectedCoordinates){
        // When
        int[] calculatedCoordinates = AdjacentTilesCalc.translateIndexToCoordinates(index);

        // Then
        Assert.assertEquals(expectedCoordinates[0], calculatedCoordinates[0]);
        Assert.assertEquals(expectedCoordinates[1], calculatedCoordinates[1]);
    }

    @Test(dataProvider = "coordinatesProvider")
    public void shouldTranslateCoordinatesToIndex(int[] coordinates, int expectedIndex){
        // When
        int calculatedIndex = AdjacentTilesCalc.translateCoordinatesToIndex(coordinates[0],coordinates[1]);

        // Then
        Assert.assertEquals(calculatedIndex, expectedIndex);
    }

    @Test(dataProvider = "adjacentTilesProvider")
    public void shouldGetBoardIndexesOfAllAdjacentTilesOf(int boardIndex, Set<Integer> expectedBoardIndexesOfAdjacentTiles) {
        //when
        List<Integer> boardIndexesOfAdjacentTilesList = AdjacentTilesCalc.getBoardIndexesOfAllAdjacentTilesOf(boardIndex);
        Set<Integer> boardIndexesOfAdjacentTiles = new HashSet<>(boardIndexesOfAdjacentTilesList);
        //then
        Assert.assertEquals(boardIndexesOfAdjacentTiles, expectedBoardIndexesOfAdjacentTiles);
    }
}