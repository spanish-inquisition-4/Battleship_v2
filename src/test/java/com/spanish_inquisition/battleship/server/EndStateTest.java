package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.server.game_states.EndState;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndStateTest {
    @Test
    public void checkIfGameIsNotRunning() {
        Assert.assertFalse(new EndState(null, null).isGameRunning());
    }

    @Test
    public void checkIfStateIsNotTransformingIntoAnotherState() {
        Assert.assertNull(new EndState(null, null).transform());
    }
}