package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.server.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;

import java.util.List;

public abstract class GameState {
    protected Players players;
    protected MessageBus requestBus;

    protected GameState(Players players, MessageBus requestBus) {
        this.players = players;
        this.requestBus = requestBus;
    }


    public boolean isGameRunning() {
        return true;
    }

    public abstract GameState transform();
}
