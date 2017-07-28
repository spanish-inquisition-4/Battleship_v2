package com.spanish_inquisition.battleship.server;

public class Message {
    private int sender;
    private int  recipient;
    private String content;

    public Message(int sender, int recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = message;
    }

    public int getSender() {
        return sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", message='" + content + '\'' +
                '}';
    }
}