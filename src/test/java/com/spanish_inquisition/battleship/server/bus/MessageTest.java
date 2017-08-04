package com.spanish_inquisition.battleship.server.bus;

import com.spanish_inquisition.battleship.server.bus.Message;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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
        assertEquals(true, testMessage.isToRecipient(2));
        assertEquals(false, testMessage.isToRecipient(84));
    }

    @Test
    public void shouldCheckIfMessageIsFromServer() {
        // Then
        assertEquals(true, testMessage.isFromSender(1));
        assertEquals(false, testMessage.isFromSender(84));
    }

    @Test
    public void shouldGetMessageSender() {
        // When
        int getSender = testMessage.getSender();

        // Then
        assertEquals(sender, getSender);
    }

    @Test
    public void shouldGetMessageRecipient() {
        // When
        int getRecipient = testMessage.getRecipient();

        // Then
        assertEquals(recipient, getRecipient);
    }

    @Test
    public void shouldGetMessageContent() {
        // When
        String getContent = testMessage.getContent();

        // Then
        assertEquals(content, getContent);
    }
}