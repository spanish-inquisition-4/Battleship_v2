package com.spanish_inquisition.battleship.server.fleet;

import java.util.List;

public class Fleet {
    private List<Ship> ships;
    private Ship currentDestroyedShip;

    public Fleet(List<Ship> ships) {
        this.ships = ships;
        currentDestroyedShip = null;
    }

    public List<Ship> getShips() {
        return ships;
    }

    @Override
    public String toString() {
        return "Fleet{"
                + "ships="
                + ships
                + '}';
    }

    public boolean hasNoShips() {
        boolean noShips = true;
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                noShips = false;
            }
        }

        return noShips;
    }

    public boolean pointIsClaimedByFleet(Integer targetedPoint) {
        for (Ship ship : ships) {
            if (ship.gotHit(targetedPoint)) {
                if (ship.isDestroyed()) {
                    currentDestroyedShip = ship;
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasCurrentDestroyedShip() {
        return currentDestroyedShip != null;
    }

    public Ship pullDestroyedShip() {
        Ship pulledShip = currentDestroyedShip;
        currentDestroyedShip = null;
        return pulledShip;
    }
}