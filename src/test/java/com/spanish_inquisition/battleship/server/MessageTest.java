package com.spanish_inquisition.battleship.server;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessageTest {

    private final String content = "Test text";
    private final int sender = 1;
    private final int recipient = 2;
    private Message testMessage;

    @BeforeMethod
    private void beforeMethod() {
        testMessage = new Message(sender, recipient, content);
    }

    @Test
    public void shouldCheckIfMessageIsToRecipient() {
        // Then
        Assert.assertEquals(true, testMessage.isToRecipient(2));
        Assert.assertEquals(false, testMessage.isToRecipient(84));
    }

    @Test
    public void shouldGetMessageSender(){
        // When
        int getSender = testMessage.getSender();

        // Then
        Assert.assertEquals(sender, getSender);
    }

    @Test
    public void shouldGetMessageRecipient(){
        // When
        int getRecipient = testMessage.getRecipient();

        // Then
        Assert.assertEquals(recipient, getRecipient);
    }

    @Test
    public void shouldGetMessageContent(){
        // When
        String getContent = testMessage.getContent();

        // Then
        Assert.assertEquals(content, getContent);
    }
}