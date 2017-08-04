package com.spanish_inquisition.battleship.server.bus;

import com.spanish_inquisition.battleship.server.bus.Message;
import com.spanish_inquisition.battleship.server.bus.MessageBus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MessageBusTest {

    private MessageBus messageBus;

    // region data providers
    @DataProvider
    private Object[][] gettingForRecipientMessageProvider() {
        return new Object[][]{
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        20, new Message(10, 20, "BBB")}
        };
    }

    @DataProvider
    private Object[][] gettingFromSenderMessageProvider() {
        return new Object[][]{
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        10, new Message(10, 20, "BBB")},
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        1, new Message(1, 2, "AAA")},
        };
    }

    @DataProvider
    private Object[][] checkingForSenderMessagesProvider() {
        return new Object[][]{
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        10, true},
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        1, true},
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        77, false},
        };
    }

    @DataProvider
    private Object[][] checkingForRecipientMessagesProvider() {
        return new Object[][]{
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        20, true},
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 20, "CCC"),
                        new Message(5, 36, "QWE")),
                        20, true},
                {Arrays.asList(
                        new Message(1, 2, "AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                        77, false},
        };
    }
    // endregion

    @BeforeMethod
    private void beforeMethod() {
        messageBus = new MessageBus();
    }

    @Test(dataProvider = "gettingForRecipientMessageProvider")
    public void shouldGetMessageForRecipient(List<Message> messages, int recipientId, Message expectedMessage) {
        // Given
        messages.forEach(message -> messageBus.addMessage(
                message.getSender(), message.getRecipient(), message.getContent()));
        // When
        Message getMessage = messageBus.getMessageFor(recipientId);

        // Then
        assertEquals(expectedMessage.getContent(), getMessage.getContent());
        assertEquals(expectedMessage.getSender(), getMessage.getSender());
        assertEquals(expectedMessage.getRecipient(), getMessage.getRecipient());
        assertFalse(messageBus.haveMessageForRecipient(recipientId));
    }

    @Test(dataProvider = "gettingFromSenderMessageProvider")
    public void shouldGetMessageForSender(List<Message> messages, int senderId, Message expectedMessage) {
        // Given
        messages.forEach(message -> messageBus.addMessage(
                message.getSender(), message.getRecipient(), message.getContent()));
        // When
        Message getMessage = messageBus.getMessageFrom(senderId);

        // Then
        assertEquals(expectedMessage.getContent(), getMessage.getContent());
        assertEquals(expectedMessage.getSender(), getMessage.getSender());
        assertEquals(expectedMessage.getRecipient(), getMessage.getRecipient());
        //assertFalse(messageBus.haveMessageFromSender(senderId));
    }

    @Test(dataProvider = "checkingForSenderMessagesProvider")
    public void shouldCheckForMessageFromSenderInBus(List<Message> messages, int senderId, boolean expected) {
        // Given
        messages.forEach(message -> messageBus.addMessage(
                message.getSender(), message.getRecipient(), message.getContent()));
        // When
        boolean found = messageBus.haveMessageFromSender(senderId);

        // Then
        assertEquals(expected, found);
    }

    @Test(dataProvider = "checkingForRecipientMessagesProvider")
    public void shouldCheckForMessageForRecipientInBus(List<Message> messages, int recipientId, boolean expected) {
        // Given
        messages.forEach(message -> messageBus.addMessage(
                message.getSender(), message.getRecipient(), message.getContent()));
        // When
        boolean found = messageBus.haveMessageForRecipient(recipientId);

        // Then
        assertEquals(expected, found);
    }
}