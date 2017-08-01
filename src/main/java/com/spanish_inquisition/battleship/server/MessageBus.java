package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.common.AppLogger;

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