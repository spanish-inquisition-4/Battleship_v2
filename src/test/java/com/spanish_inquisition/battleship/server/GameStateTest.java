package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.server.game_states.GameState;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GameStateTest {
    @Test
    public void checkIfGameIsRunning() {
        Assert.assertTrue(new GameState(null, null) {
            @Override
            public GameState transform() {
                return null;
            }
        }.isGameRunning());
    }
}