package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.common.AppLogger;
import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import com.spanish_inquisition.battleship.server.fleet.Ship;

import java.util.Iterator;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBus {
    private Queue<Message> messageBus;

    public MessageBus() {
        messageBus = new ConcurrentLinkedQueue<>();
        AppLogger.initializeLogger();
    }

    public void addMessage(int senderId, int recipientId, String message) {
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    public void addResponseHitMessage(int senderId, int recipientId, Integer field) {
        String message = Header.RESPONSE_HIT.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                field +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    public void addResponseOpponentHitMessage(int senderId, int recipientId, Integer field) {
        String message = Header.RESPONSE_OPPONENT_HIT.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                field +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    public void addResponseMissMessage(int senderId, int recipientId, Integer field) {
        String message = Header.RESPONSE_MISS.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                field +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    public void addResponseOpponentMissMessage(int senderId, int recipientId, Integer field) {
        String message = Header.RESPONSE_OPPONENT_MISS.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                field +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    public void addResponseDestroyedShipMessage(int senderId, int recipientId, Ship destroyedShip) {
        String shipPointsAsString = destroyedShip.pointsAsString();
        String message = Header.RESPONSE_DESTROYED_SHIP.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                shipPointsAsString +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    public void addResponseOpponentDestroyedShipMessage(int senderId, int recipientId, Ship destroyedShip) {
        String shipPointsAsString = destroyedShip.pointsAsString();
        String message = Header.RESPONSE_OPPONENT_DESTROYED_SHIP.name() +
                NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                shipPointsAsString +
                NetworkMessage.RESPONSE_SPLIT_CHARACTER;
        Message newMessage = new Message(senderId, recipientId, message);
        messageBus.add(newMessage);
    }

    Message getMessageFor(int recipientId) {
        Message msg = null;
        Iterator<Message> iterator = messageBus.iterator();
        while (iterator.hasNext()) {
            Message current = iterator.next();
            if (current.isToRecipient(recipientId)) {
                msg = current;
                iterator.remove();
                break;
            }
        }
        return msg;
    }

    public Message getMessageFrom(int senderId) {
        Optional<Message> optionalMessage;
        optionalMessage = messageBus.stream()
                .filter(message -> message.isFromSender(senderId)).findFirst();
        removeAllSenderMessagesFromBus(senderId);
        return optionalMessage.orElse(new Message(0, 0, ""));
    }

    private void removeAllSenderMessagesFromBus(int senderId) {
        messageBus.removeIf(message -> message.isFromSender(senderId));
    }

    public boolean haveMessageFromSender(int senderId) {
        boolean isFromSender = false;
        if (messageBus != null) {
            isFromSender = messageBus.stream()
                    .anyMatch(message -> message.isFromSender(senderId));
        }
        return isFromSender;
    }

    boolean haveMessageForRecipient(int recipientId) {
        boolean isForRecipient = false;
        if (messageBus != null) {
            isForRecipient = messageBus.stream()
                    .anyMatch(message -> message.isToRecipient(recipientId));
        }
        return isForRecipient;
    }

}