package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.server.fleet.Fleet;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

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
        logger.log(DEFAULT_LEVEL, "Setting players fleet " + fleet.toString());
        this.fleet = fleet;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public boolean hasNoFleet() {
        return fleet.hasNoShips();
    }
}
