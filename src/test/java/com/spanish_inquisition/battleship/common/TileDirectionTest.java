package com.spanish_inquisition.battleship.common;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

public class TileDirectionTest {

    @DataProvider
    private Object[][] tileToTheLeftProvider() {
        return new Object[][]{
                {33, 32}, //random tile
                {3, 2}, //tile from first row
                {96, 95}, //tile from last row
                {10, -1}, //first tile in row
                {89, 88}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] tileToTheRightProvider() {
        return new Object[][]{
                {44, 45}, //random tile
                {2, 3}, //tile from first row
                {95, 96}, //tile from last row
                {10, 11}, //first tile in row
                {89, -1}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] tileAboveProvider() {
        return new Object[][]{
                {44, 34}, //random tile
                {2, -1}, //tile from first row
                {95, 85}, //tile from last row
                {10, 0}, //first tile in row
                {89, 79}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] tileBelowProvider() {
        return new Object[][]{
                {44, 54}, //random tile
                {2, 12}, //tile from first row
                {95, -1}, //tile from last row
                {10, 20}, //first tile in row
                {89, 99}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] upperLeftTileProvider() {
        return new Object[][]{
                {44, 33}, //random tile
                {2, -1}, //tile from first row
                {95, 84}, //tile from last row
                {10, -1}, //first tile in row
                {89, 78}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] lowerLeftTileProvider() {
        return new Object[][]{
                {44, 53}, //random tile
                {2, 11}, //tile from first row
                {95, -1}, //tile from last row
                {10, -1}, //first tile in row
                {89, 98}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] upperRightTileProvider() {
        return new Object[][]{
                {44, 35}, //random tile
                {2, -1}, //tile from first row
                {95, 86}, //tile from last row
                {10, 1}, //first tile in row
                {89, -1}, //last tile in row
        };
    }

    @DataProvider
    private Object[][] lowerRightTileProvider() {
        return new Object[][]{
                {44, 55}, //random tile
                {2, 13}, //tile from first row
                {95, -1}, //tile from last row
                {10, 21}, //first tile in row
                {89, -1}, //last tile in row
        };
    }

    @Test(dataProvider = "tileToTheLeftProvider")
    public void shouldGetBoardIndexOfTileToTheLeft(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.LEFT;
        //when
        int boardIndexOfTileToTheLeft = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileToTheLeft, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "tileToTheRightProvider")
    public void shouldGetBoardIndexOfTileToTheRight(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.RIGHT;
        //when
        int boardIndexOfTileToTheRight = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileToTheRight, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "tileAboveProvider")
    public void shouldGetBoardIndexOfTileAbove(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.ABOVE;
        //when
        int boardIndexOfTileAbove = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileAbove, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "tileBelowProvider")
    public void shouldGetBoardIndexOfTileBelow(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.BELOW;
        //when
        int boardIndexOfTileBelow = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfTileBelow, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "upperLeftTileProvider")
    public void shouldGetBoardIndexOfUpperLeftTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.UPPER_LEFT;
        //when
        int boardIndexOfUpperLeftTile = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfUpperLeftTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "lowerLeftTileProvider")
    public void shouldGetBoardIndexOfLowerLeftTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.LOWER_LEFT;
        //when
        int boardIndexOfLowerLeftTile = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfLowerLeftTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "upperRightTileProvider")
    public void shouldGetBoardIndexOfUpperRightTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.UPPER_RIGHT;
        //when
        int boardIndexOfUpperRightTile = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfUpperRightTile, expectedBoardIndexOfAdjacentTile);
    }

    @Test(dataProvider = "lowerRightTileProvider")
    public void shouldGetBoardIndexOfLowerRightTile(int boardIndex, int expectedBoardIndexOfAdjacentTile) {
        //given
        TileDirection tileDirection = TileDirection.LOWER_RIGHT;
        //when
        int boardIndexOfLowerRightTile = tileDirection.getAdjacentTileIndex(boardIndex);
        //then
        Assert.assertEquals(boardIndexOfLowerRightTile, expectedBoardIndexOfAdjacentTile);
    }
}