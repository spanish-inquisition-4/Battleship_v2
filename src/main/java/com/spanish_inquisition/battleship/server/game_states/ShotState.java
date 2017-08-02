package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import com.spanish_inquisition.battleship.server.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.fleet.Ship;

import java.util.List;

import static com.spanish_inquisition.battleship.server.BattleshipServer.SERVER_ID;

public class ShotState extends GameState {
    public ShotState(Players players, MessageBus requestBus) {
        super(players, requestBus);
    }

    @Override
    public GameState transform() {
        Player currentPlayer = getCurrentPlayer();
        requestBus.addMessage(SERVER_ID, currentPlayer.getPlayerId(), Header.DECIDE_ON_MOVE.name());
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
                if(shotExecution(shotMessage, player) ) {
                    return false;
                }
                players.switchCurrentPlayer();
                return true;
            }
        }
        return false;
    }

    private boolean shotExecution(String messageContent, Player player) {
        Integer targetedPoint = Integer.valueOf(messageContent.substring(
                messageContent.indexOf(NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER) + 1,
                messageContent.indexOf(NetworkMessage.RESPONSE_SPLIT_CHARACTER)
        ));

        Player opponent = players.getOpponentOf(player);
        if(opponent.fleetGotHit(targetedPoint)) {
            notifyPlayersAboutHit(player, opponent, targetedPoint);
            if(opponent.gotDestroyedShip()) {
                notifyPlayersAboutDestroyedShip(player, opponent, opponent.pullDestroyedShip());
            }
            return true;
        }
        notifyPlayersAboutMiss(player, opponent, targetedPoint);

        return false;
    }

    private void notifyPlayersAboutDestroyedShip(Player player, Player opponent, Ship ship) {
        requestBus.addResponseDestroyedShipMessage(SERVER_ID, player.getPlayerId(), ship);
        requestBus.addResponseOpponentDestroyedShipMessage(SERVER_ID, opponent.getPlayerId(), ship);
    }

    private void notifyPlayersAboutHit(Player player, Player opponent, Integer targetedPoint) {
        requestBus.addResponseHitMessage(SERVER_ID, player.getPlayerId(), targetedPoint);
        requestBus.addResponseOpponentHitMessage(SERVER_ID, opponent.getPlayerId(), targetedPoint);
    }

    private void notifyPlayersAboutMiss(Player player, Player opponent, Integer targetedPoint) {
        requestBus.addResponseMissMessage(SERVER_ID, player.getPlayerId(), targetedPoint);
        requestBus.addResponseOpponentMissMessage(SERVER_ID, opponent.getPlayerId(), targetedPoint);
    }

    private Player getCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    private String getMessageContentFromPlayer(Player player) {
        return requestBus.getMessageFrom(player.getPlayerId()).getContent();
    }
}
