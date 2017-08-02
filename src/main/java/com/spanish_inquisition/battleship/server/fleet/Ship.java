package com.spanish_inquisition.battleship.server.fleet;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    List<Integer> shipPoints;
    List<Integer> damagedPoints;

    public Ship(List<Integer> shipPoints) {
        this.shipPoints = shipPoints;
        this.damagedPoints = new ArrayList<>();
    }

    public List<Integer> getShipPoints() {
        return shipPoints;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipPoints=" + shipPoints +
                '}';
    }

    public boolean gotHit(Integer targetedPoint) {
        if(shipPoints.contains(targetedPoint)) {
            damagedPoints.add(targetedPoint);
            shipPoints.remove(targetedPoint);
            return true;
        }
        return false;
    }

    public boolean isDestroyed() {
        return shipPoints.isEmpty();
    }

    public String pointsAsString() {
        return damagedPoints.toString();
    }
}