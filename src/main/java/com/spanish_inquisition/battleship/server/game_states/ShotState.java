package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.server.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;

import java.util.List;

public class ShotState extends GameState {
    public ShotState(Players players, MessageBus requestBus) {
        super(players, requestBus);
    }

    @Override
    public GameState transform() {
        if (requestBus.haveMessageFromSender(players.getCurrentPlayer().getPlayerId())) {
            //TODO get message &
        }
        return null;
    }
}
