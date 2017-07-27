package com.spanish_inquisition.battleship.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBus {
    private Queue<Message> messageBus;

    public MessageBus() {
        messageBus = new ConcurrentLinkedQueue<>();
    }

    public void addMessage(int senderId, int recipientId, String message) {
        Message message1 = new Message(senderId, recipientId, message);
        messageBus.add(message1);
    }

    public Message getMessageFor(int recipientId) {
        Message msg = null;
        Iterator<Message> iterator = this.messageBus.iterator();
        while (iterator.hasNext()) {
            Message current = iterator.next();
            if(current.getRecipient() == recipientId) {
                msg = current;
                iterator.remove();
                break;
            }
        }
        return msg;
    }

    public boolean haveMessageFromSender(int senderId) {
        if(messageBus != null) {
            for (Message message : messageBus)
                if (message.getSender() == senderId) return true;
        }

        return false;
    }

    public boolean haveMessageForRecipient(int recipientId) {
        if(messageBus != null) {
            for (Message message : messageBus)
                if (message.getRecipient() == recipientId) return true;
        }

        return false;
    }

    public Message getMessageFromSender(int senderId) {
        List<Message> newList = new LinkedList<>();
        for (Message msg: messageBus){
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
