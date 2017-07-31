package com.spanish_inquisition.battleship.server;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GameStateTest {
    @Test
    public void checkIfGameIsRunning() {
        Assert.assertTrue(new GameState() {
            @Override
            public GameState transform() {
                return null;
            }
        }.isGameRunning());
    }
}