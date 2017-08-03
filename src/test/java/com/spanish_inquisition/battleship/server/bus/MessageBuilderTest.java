package com.spanish_inquisition.battleship.server.bus;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import com.spanish_inquisition.battleship.server.fleet.Ship;
import com.spanish_inquisition.battleship.server.fleet.ThreeMast;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilderTest {
    private final int senderId = 0;
    private final int recipientId = 1;

    @Test
    public void shouldBuildResponseHitMessage() {
        Integer targetedField = 20;
        String expectedContent = "RESPONSE_HIT:20;";
        Message testMessage =
                new MessageBuilder(senderId, recipientId)
                        .buildResponseHitMessage(targetedField).getMessage();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(senderId, testMessage.getSender());
        soft.assertEquals(recipientId, testMessage.getRecipient());
        soft.assertEquals(expectedContent, testMessage.getContent());
    }

    @Test
    public void shouldBuildResponseOpponentHitMessage() {
        Integer targetedField = 20;
        String expectedContent = "RESPONSE_OPPONENT_HIT:20;";
        Message testMessage =
                new MessageBuilder(senderId, recipientId)
                        .buildResponseOpponentHitMessage(targetedField).getMessage();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(senderId, testMessage.getSender());
        soft.assertEquals(recipientId, testMessage.getRecipient());
        soft.assertEquals(expectedContent, testMessage.getContent());
    }

    @Test
    public void shouldBuildResponseMissMessage() {
        Integer targetedField = 20;
        String expectedContent = "RESPONSE_MISS:20;";
        Message testMessage =
                new MessageBuilder(senderId, recipientId)
                        .buildResponseMissMessage(targetedField).getMessage();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(senderId, testMessage.getSender());
        soft.assertEquals(recipientId, testMessage.getRecipient());
        soft.assertEquals(expectedContent, testMessage.getContent());
    }

    @Test
    public void shouldBuildResponseOpponentMissMessage() {
        Integer targetedField = 20;
        String expectedContent = "RESPONSE_OPPONENT_MISS:20;";
        Message testMessage =
                new MessageBuilder(senderId, recipientId)
                        .buildResponseMissMessage(targetedField).getMessage();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(senderId, testMessage.getSender());
        soft.assertEquals(recipientId, testMessage.getRecipient());
        soft.assertEquals(expectedContent, testMessage.getContent());
    }

    @Test
    public void shouldBuildResponseDestroyedShipMessage() {
        List<Integer> shipPoints = new ArrayList<>();
        shipPoints.add(1);
        shipPoints.add(2);
        shipPoints.add(3);
        Ship testShip = new ThreeMast(shipPoints);
        testShip.gotHit(1);
        testShip.gotHit(2);
        testShip.gotHit(3);
        Message testMessage =
                new MessageBuilder(senderId, recipientId)
                        .buildResponseDestroyedShipMessage(testShip).getMessage();
        String expectedContent =
                Header.RESPONSE_DESTROYED_SHIP.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                testShip.toString() +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(senderId, testMessage.getSender());
        soft.assertEquals(recipientId, testMessage.getRecipient());
        soft.assertEquals(expectedContent, testMessage.getContent());
    }

    @Test
    public void shouldBuildResponseOpponentDestroyedShipMessage() {
        List<Integer> shipPoints = new ArrayList<>();
        shipPoints.add(1);
        shipPoints.add(2);
        shipPoints.add(3);
        Ship testShip = new ThreeMast(shipPoints);
        testShip.gotHit(1);
        testShip.gotHit(2);
        testShip.gotHit(3);
        Message testMessage =
                new MessageBuilder(senderId, recipientId)
                        .buildResponseOpponentDestroyedShipMessage(testShip).getMessage();
        String expectedContent =
                Header.RESPONSE_OPPONENT_DESTROYED_SHIP.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                testShip.toString() +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(senderId, testMessage.getSender());
        soft.assertEquals(recipientId, testMessage.getRecipient());
        soft.assertEquals(expectedContent, testMessage.getContent());
    }
}