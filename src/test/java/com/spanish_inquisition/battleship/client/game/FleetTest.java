package com.spanish_inquisition.battleship.client.game;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FleetTest {

    @Test
    public void shouldGetRandomFleet() {
        // When
        Fleet selectedFleet = Fleet.getRandomFleet();

        // Then
        Assert.assertTrue(selectedFleet.equals(Fleet.SHIPS_ONE) || selectedFleet.equals(Fleet.SHIPS_TWO)
        || selectedFleet.equals(Fleet.SHIPS_THREE));
    }
}