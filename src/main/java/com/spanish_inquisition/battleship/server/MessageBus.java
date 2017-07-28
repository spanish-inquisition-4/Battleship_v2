package com.spanish_inquisition.battleship.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class MessageBus {
    private Queue<Message> messageBus;

    MessageBus() {
        messageBus = new ConcurrentLinkedQueue<>();
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

    boolean haveMessageFromSender(int senderId) {
        if (messageBus != null) {
            for (Message message : messageBus)
                if (message.getSender() == senderId) return true;
        }
        return false;
    }

    boolean haveMessageForRecipient(int recipientId) {
        if (messageBus != null) {
            for (Message message : messageBus)
                if (message.isToRecipient(recipientId)) return true;
        }
        return false;
    }

    Message getMessageFromSender(int senderId) {
        List<Message> newList = new LinkedList<>();
        for (Message msg : messageBus) {
            if (msg.getSender() == senderId) {
                newList.add(msg);
            }
        }

        Message msg = null;
        if (!newList.isEmpty()) {
            msg = newList.get(0);
        }

        messageBus.removeAll(newList);
        return msg;
    }
}