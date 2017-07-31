package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.server.fleet.FleetBuilder;
import com.spanish_inquisition.battleship.server.fleet.FleetValidator;

import java.util.LinkedList;
import java.util.List;

import static com.spanish_inquisition.battleship.server.BattleshipServer.SERVER_ID;

public class PlacingShipsState extends GameState {
    private List<Player> players;
    private MessageBus requestBus;

    public PlacingShipsState(List<Player> players, MessageBus requestBus) {
        this.players = players;
        this.requestBus = requestBus;
    }

    @Override
    public GameState transform() {
        List<Player> notReadyPlayers = new LinkedList<>(players);
        while(!notReadyPlayers.isEmpty()) {
            for(Player player : notReadyPlayers) {
                if(requestBus.haveMessageFromSender(player.getPlayerId())) {
                    resolvePlayerFleetValidation(player, notReadyPlayers);
                }
            }
        }
        return new EndState();
    }

    private void resolvePlayerFleetValidation(Player player, List<Player> notReadyPlayers) {
        if(setFleetIfIsValid(player)) {
            notReadyPlayers.remove(player);
            requestBus.addMessage(SERVER_ID, player.getPlayerId(), Header.FLEET_VALID.name());
        } else {
            requestBus.addMessage(SERVER_ID, player.getPlayerId(), Header.FLEET_INVALID.name());
        }
    }

    private boolean setFleetIfIsValid(Player player) {
        String fleetMessage = requestBus.getMessageFrom(player.getPlayerId()).getContent();
        if (fleetMessage.contains(Header.FLEET_REQUEST.name())) {
            if (FleetValidator.validate(fleetMessage)) {
                player.setFleet(new FleetBuilder().build(fleetMessage));
                return true;
            }
        }

        return false;
    }
}