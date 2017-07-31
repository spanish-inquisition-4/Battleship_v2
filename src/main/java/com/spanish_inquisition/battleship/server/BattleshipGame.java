package com.spanish_inquisition.battleship.server;

import java.util.ArrayList;
import java.util.List;

public class BattleshipGame {
    private List<ClientConnectionHandler> clients;
    private MessageBus requestBus;

    public BattleshipGame(List<ClientConnectionHandler> clients, MessageBus requestBus) {
        this.clients = clients;
        this.requestBus = requestBus;
    }

    public void proceed() {
        List<Player> players = new ArrayList<>();
        for(ClientConnectionHandler client : clients) {
            players.add(new Player(client));
        }
        GameState currentState = new PlacingShipsState(players, requestBus);
        while(currentState.isGameRunning()){
            currentState = currentState.transform();
        }
    }
}