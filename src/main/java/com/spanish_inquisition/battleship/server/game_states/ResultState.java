package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.server.bus.MessageBus;
import com.spanish_inquisition.battleship.server.Players;

public class ResultState extends GameState {
    public ResultState(Players players, MessageBus requestBus) {
        super(players, requestBus);
    }

    @Override
    public GameState transform() {
        //TODO: notify players about match result
        //if this state is invoked, right now opponent of current player won
        return new EndState(players, requestBus);
    }
}
