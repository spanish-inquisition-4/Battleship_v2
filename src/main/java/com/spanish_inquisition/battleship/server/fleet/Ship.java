package com.spanish_inquisition.battleship.server.fleet;

import java.util.List;

public abstract class Ship {
    List<Integer> shipPoints;

    public Ship(List<Integer> shipPoints) {
        this.shipPoints = shipPoints;
    }

    public List<Integer> getShipPoints() {
        return shipPoints;
    }
}
