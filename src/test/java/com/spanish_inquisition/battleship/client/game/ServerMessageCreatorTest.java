package com.spanish_inquisition.battleship.client.game;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ServerMessageCreatorTest {

    @Test
    public void shouldCreateMessageForServer(){
        // Given
        List<Integer> shipPlaces = Arrays.asList(1,2,3,4,5,6,7);

        // When
        String message = ServerMessageCreator.createFleetMessage(shipPlaces);

        // Then
        Assert.assertEquals("FLEET_REQUEST:[1,2,3,4,5,6,7];", message);
    }
}