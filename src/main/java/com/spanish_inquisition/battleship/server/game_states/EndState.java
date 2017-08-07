package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.bus.MessageBus;

public class EndState extends GameState {
    EndState(Players players, MessageBus requestBus) {
        super(players, requestBus);
    }

    @Override
    public boolean isGameRunning() {
        return false;
    }

    @Override
    public GameState transform() {
        return null;
    }
}
