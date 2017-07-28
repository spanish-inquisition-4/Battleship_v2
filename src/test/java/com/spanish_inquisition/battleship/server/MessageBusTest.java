package com.spanish_inquisition.battleship.server;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class MessageBusTest {

    private MessageBus messageBus;

    @DataProvider(name = "MessageProvider")
    private Object[][] messageProvider() {
        return new Object[][] {
                {Arrays.asList(
                        new Message(1,2,"AAA"),
                        new Message(10, 20, "BBB"),
                        new Message(1, 2, "CCC"),
                        new Message(5, 36, "QWE")),
                20, new Message(10, 20, "BBB")}
        };
    }

    @BeforeMethod
    private void beforeMethod() {
        messageBus = new MessageBus();
    }

    @Test(dataProvider = "MessageProvider")
    public void shouldGetMessageForRecipient(List<Message> messages, int recipientId, Message expectedMessage) {
        // Given
        messages.forEach(message -> messageBus.addMessage(
                message.getSender(), message.getRecipient(), message.getContent()));
        // When
        Message getMessage = messageBus.getMessageFor(recipientId);

        // Then
        Assert.assertEquals(expectedMessage.getContent(), getMessage.getContent());
        Assert.assertEquals(expectedMessage.getSender(), getMessage.getSender());
        Assert.assertEquals(expectedMessage.getRecipient(), getMessage.getRecipient());
        Assert.assertFalse(messageBus.haveMessageForRecipient(recipientId));
    }
}