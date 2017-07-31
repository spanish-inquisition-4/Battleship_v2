package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.server.fleet.Fleet;

public class Player {
    private ClientConnectionHandler client;
    private Fleet fleet;

    public Player(ClientConnectionHandler client) {
        this.client = client;
    }

    public int getPlayerId() {
        return client.getClientId();
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public Fleet getFleet() {
        return fleet;
    }
}
