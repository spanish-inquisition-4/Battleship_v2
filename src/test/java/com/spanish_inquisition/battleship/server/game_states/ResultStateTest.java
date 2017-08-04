package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.server.bus.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class ResultStateTest {
    @Test
    public void testIfGoesToEndState() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        Players players = new Players(p1,p2);
        MessageBus messageBus = mock(MessageBus.class);
        Assert.assertEquals(EndState.class, new ResultState(players, messageBus).transform().getClass());
    }
}