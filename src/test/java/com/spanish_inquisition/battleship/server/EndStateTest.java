package com.spanish_inquisition.battleship.server;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EndStateTest {
    @Test
    public void checkIfGameIsNotRunning() {
        Assert.assertFalse(new EndState().isGameRunning());
    }

    @Test
    public void checkIfStateIsNotTransformingIntoAnotherState() {
        Assert.assertNull(new EndState().transform());
    }
}