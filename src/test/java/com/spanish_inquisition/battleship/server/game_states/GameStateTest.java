package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.server.MessageBus;
import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.game_states.GameState;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class GameStateTest {
    @Test
    public void checkIfGameIsRunning() {
        Players players = mock(Players.class);
        MessageBus requestBus = mock(MessageBus.class);
        Assert.assertTrue(new GameState(players, requestBus) {
            @Override
            public GameState transform() {
                return null;
            }
        }.isGameRunning());
    }
}