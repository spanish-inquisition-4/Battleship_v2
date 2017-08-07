package com.spanish_inquisition.battleship.server.bus;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import com.spanish_inquisition.battleship.server.fleet.Ship;

public class MessageBuilder {
    private Integer senderId;
    private Integer recipientId;
    private String messageContent;

    public MessageBuilder(Integer senderId, Integer recipientId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public MessageBuilder buildResponseHitMessage(Integer field) {
        messageContent = Header.RESPONSE_HIT.name()
                + NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER
                + field
                + NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        return this;
    }

    public MessageBuilder buildResponseOpponentHitMessage(Integer field) {
        messageContent = Header.RESPONSE_OPPONENT_HIT.name()
                + NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER
                + field
                + NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        return this;
    }

    public MessageBuilder buildResponseMissMessage(Integer field) {
        messageContent = Header.RESPONSE_MISS.name()
                + NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER
                + field
                + NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        return this;
    }

    public MessageBuilder buildResponseOpponentMissMessage(Integer field) {
        messageContent = Header.RESPONSE_OPPONENT_MISS.name()
                + NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER
                + field
                + NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        return this;
    }

    public MessageBuilder buildResponseDestroyedShipMessage(Ship destroyedShip) {
        String shipPointsAsString = destroyedShip.pointsAsString();
        messageContent = Header.RESPONSE_DESTROYED_SHIP.name()
                + NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER
                + shipPointsAsString
                + NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        return this;
    }

    public MessageBuilder buildResponseOpponentDestroyedShipMessage(Ship destroyedShip) {
        String shipPointsAsString = destroyedShip.pointsAsString();
        messageContent = Header.RESPONSE_OPPONENT_DESTROYED_SHIP.name()
                + NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER
                + shipPointsAsString
                + NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        return this;
    }

    public Message getMessage() {
        return new Message(senderId, recipientId, messageContent);
    }
}
