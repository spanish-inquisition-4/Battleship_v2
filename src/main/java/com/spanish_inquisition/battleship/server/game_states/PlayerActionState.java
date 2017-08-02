package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.server.MessageBus;
import com.spanish_inquisition.battleship.server.Players;

import static com.spanish_inquisition.battleship.server.BattleshipServer.SERVER_ID;

public class PlayerActionState extends GameState {

    public PlayerActionState(Players players, MessageBus requestBus) {
        super(players, requestBus);
    }

    @Override
    public GameState transform() {
        requestBus.addMessage(SERVER_ID, players.getCurrentPlayer().getPlayerId(), Header.PLAYER_TURN.name());
        requestBus.addMessage(SERVER_ID, players.getOpponentOf(players.getCurrentPlayer()).getPlayerId(), Header.OPPONENT_TURN.name());
        return new ShotState(players, requestBus);
    }
}