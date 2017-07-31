package com.spanish_inquisition.battleship.server.fleet;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StringToStringArrayParserTest {
    @Test
    public void testIfStringIsParsedCorrectlyToArray() {
        String testString = "1[] ;,2";
        String[] parsedArray = StringToStringArrayParser.parse(testString);
        Assert.assertEquals(parsedArray.length, 2);
        Assert.assertEquals(parsedArray[0], "1");
        Assert.assertEquals(parsedArray[1], "2");
    }
}