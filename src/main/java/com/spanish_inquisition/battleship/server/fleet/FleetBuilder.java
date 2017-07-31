package com.spanish_inquisition.battleship.server.fleet;

import java.util.ArrayList;
import java.util.List;

public class FleetBuilder {
    public Fleet build(String fleetMessage) {
        List<Integer> shipsFields = FleetParser.parseMessageToIntegersList(fleetMessage);
        if (shipsFields.size() == 20) {
            return new Fleet(extractShipsFromList(shipsFields));
        } else {
            return null;
        }
    }

    private List<Ship> extractShipsFromList(List<Integer> shipsFields) {
        List<Ship> ships = new ArrayList<>();
        ships.addAll(createOneMastsFromFieldsList(shipsFields));
        ships.addAll(createTwoMastsFromFieldsList(shipsFields));
        ships.addAll(createThreeMastsFromFieldsList(shipsFields));
        ships.add(createFourMastFromFieldsList(shipsFields));
        return ships;
    }

    private List<OneMast> createOneMastsFromFieldsList(List<Integer> shipsFields) {
        List<OneMast> oneMasts = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            List<Integer> oneMastPoint = new ArrayList<>(1);
            oneMastPoint.add(shipsFields.get(0));
            oneMasts.add(new OneMast(oneMastPoint));
            shipsFields.remove(0);
        }
        return oneMasts;
    }

    private List<TwoMast> createTwoMastsFromFieldsList(List<Integer> shipsFields) {
        List<TwoMast> twoMasts = new ArrayList<>(3);
        for(int i = 0; i < 3; i++)
            twoMasts.add(extractNewTwoMast(shipsFields));

        return twoMasts;
    }

    private TwoMast extractNewTwoMast(List<Integer> shipsFields) {
        List<Integer> twoMastFields = new ArrayList<>(2);
        for(int i = 0; i < 2; i++) {
            twoMastFields.add(shipsFields.get(0));
            shipsFields.remove(0);
        }
        return new TwoMast(twoMastFields);
    }

    private List<ThreeMast> createThreeMastsFromFieldsList(List<Integer> shipsFields) {
        List<ThreeMast> threeMasts = new ArrayList<>(2);
        for(int i = 0; i < 2; i++)
            threeMasts.add(extractNewThreeMast(shipsFields));

        return threeMasts;
    }

    private ThreeMast extractNewThreeMast(List<Integer> shipsFields) {
        List<Integer> threeMastFields = new ArrayList<>(3);
        for(int i = 0; i < 3; i++) {
            threeMastFields.add(shipsFields.get(0));
            shipsFields.remove(0);
        }
        return new ThreeMast(threeMastFields);
    }

    private FourMast createFourMastFromFieldsList(List<Integer> shipsFields) {
        List<Integer> fourMastFields = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            fourMastFields.add(shipsFields.get(0));
            shipsFields.remove(0);
        }
        return new FourMast(fourMastFields);
    }
}
