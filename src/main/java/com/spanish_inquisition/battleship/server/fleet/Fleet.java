package com.spanish_inquisition.battleship.server.fleet;

import java.util.List;

public class Fleet {
    private List<Ship> ships;

    public Fleet(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Ship> getShips() {
        return ships;
    }

    @Override
    public String toString() {
        return "Fleet{" +
                "ships=" + ships +
                '}';
    }

    public boolean hasNoShips() {
        return ships.isEmpty();
    }
}