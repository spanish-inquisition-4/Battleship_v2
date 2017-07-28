package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.common.AppLogger;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.spanish_inquisition.battleship.common.AppLogger.DEFAULT_LEVEL;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

class MessageBus {
    private Queue<Message> messageBus;

    MessageBus() {
        messageBus = new ConcurrentLinkedQueue<>();
        AppLogger.initializeLogger();
    }

    void addMessage(int senderId, int recipientId, String message) {
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

    Message getMessageFrom(int senderId) {
        Message firstSenderMessage = null;
        try {
            firstSenderMessage = messageBus.stream()
                    .filter(message -> message.isFromSender(senderId)).findFirst().get();
        } catch (NoSuchElementException e) {
            logger.log(DEFAULT_LEVEL, String.format("No message from sender with id: %d found in bus", senderId), e);
        }
        removeAllSenderMessagesFromBus(senderId);
        return firstSenderMessage;
    }

    private void removeAllSenderMessagesFromBus(int senderId) {
        messageBus.removeIf(message -> message.isFromSender(senderId));
    }

    boolean haveMessageFromSender(int senderId) {
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