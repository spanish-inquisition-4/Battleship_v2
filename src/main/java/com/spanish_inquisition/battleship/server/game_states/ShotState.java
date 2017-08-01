package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.common.Header;
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
        Player currentPlayer = getCurrentPlayer();
        if(!shootIfPlayerSentValidMessage(currentPlayer)) {
            return this;
        }
        return !didPlayerWon(currentPlayer) ?
                new PlayerActionState(players, requestBus) : new ResultState(players, requestBus);
    }

    private boolean didPlayerWon(Player player) {
        return players.getOpponentOf(player).hasNoFleet();
    }

    private boolean shootIfPlayerSentValidMessage(Player player) {
        if (requestBus.haveMessageFromSender(player.getPlayerId())) {
            String shotMessage = getMessageContentFromPlayer(player);
            if (shotMessage.contains(Header.MOVE_REGULAR.name())) {
                //TODO: implement shoot event, notify players
                players.switchCurrentPlayer();
                return true;
            }
        }
        return false;
    }

    private Player getCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    private String getMessageContentFromPlayer(Player player) {
        return requestBus.getMessageFrom(player.getPlayerId()).getContent();
    }
}
