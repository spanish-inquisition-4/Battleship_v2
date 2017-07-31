package com.spanish_inquisition.battleship.client.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Fleet {

    SHIPS_ONE(Arrays.asList(13, 16, 17, 20, 23, 29, 33, 45, 55, 58, 61, 68, 78, 80, 82, 83, 84, 88, 90, 96)),

    SHIPS_TWO(Arrays.asList(1, 8, 9, 14, 19, 20, 29, 33, 34, 44, 52, 56, 68, 62, 63, 66, 68, 81, 86, 87)),

    SHIPS_THREE(Arrays.asList(8, 10, 12, 13, 16, 20, 23, 28, 38, 51, 52, 53, 54, 68, 69, 73, 75, 81, 85, 86));

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
