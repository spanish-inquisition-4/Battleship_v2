package com.spanish_inquisition.battleship.client.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Fleet {

    SHIPS_ONE(Arrays.asList(20, 29, 61, 96, 16, 17, 45, 55, 80, 90, 13, 23, 33, 82, 83, 84, 58, 68, 78, 88)),

    SHIPS_TWO(Arrays.asList(1, 14, 20, 81, 56, 66, 58, 68, 86, 87, 33, 34, 44, 52, 62, 63, 8, 9, 19, 29)),

    SHIPS_THREE(Arrays.asList(8, 16, 73, 81, 10, 20, 28, 38, 68, 69, 12, 13, 23, 75, 85, 86, 51, 52, 53, 54));

    private List<Integer> shipPlaces;

    Fleet(List<Integer> shipPlaces) {
        this.shipPlaces = shipPlaces;
    }

    public List<Integer> getShipPlaces() {
        return shipPlaces;
    }

    public static Fleet getRandomFleet() {
        Random r = new Random();
        return values()[r.nextInt(values().length)];
    }
}