package com.spanish_inquisition.battleship.common;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AdjacentFieldsCalcTest {

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

    @Test(dataProvider = "IndexesProvider")
    public void shouldTranslateIndexToCoordinates(int index, int[] expectedCoordinates){
        // When
        int[] calculatedCoordinates = AdjacentFieldsCalc.translateIndexToCoordinates(index);

        // Then
        Assert.assertEquals(expectedCoordinates[0], calculatedCoordinates[0]);
        Assert.assertEquals(expectedCoordinates[1], calculatedCoordinates[1]);
    }
}
