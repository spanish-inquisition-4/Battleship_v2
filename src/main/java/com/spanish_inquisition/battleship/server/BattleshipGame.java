package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.server.game_states.GameState;
import com.spanish_inquisition.battleship.server.game_states.PlacingShipsState;

import java.util.List;

public class BattleshipGame {
    private List<ClientConnectionHandler> clients;
    private MessageBus requestBus;

    public BattleshipGame(List<ClientConnectionHandler> clients, MessageBus requestBus) {
        this.clients = clients;
        this.requestBus = requestBus;
    }

    public void proceed() {
        Players players = new Players();
        for (ClientConnectionHandler client : clients) {
            players.addPlayer(new Player(client));
        }
        GameState currentState = new PlacingShipsState(players, requestBus);
        while (currentState.isGameRunning()) {
            currentState = currentState.transform();
        }
    }
}