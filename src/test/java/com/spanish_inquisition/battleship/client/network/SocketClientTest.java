package com.spanish_inquisition.battleship.client.network;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author Michal_Partacz
 */
@Test
public class SocketClientTest {
    @Test
    public void testSendStringToServer() throws Exception {
        byte[] emptyPayload = new byte[1001];
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(emptyPayload);

        SocketClient socketClient = new SocketClient();
        socketClient.socket = getSocketMockWithStreams(byteArrayInputStream, byteArrayOutputStream);
        socketClient.setUpStreamsOnSocket();
        assertEquals(byteArrayOutputStream.size(), 0);
        String stringToServer = "Hello server!";
        String stringExpectedFromServer = stringToServer + System.getProperty("line.separator");
        socketClient.sendStringToServer(stringToServer);
        assertNotEquals(byteArrayOutputStream.size(), 0);
        assertEquals(byteArrayOutputStream.toByteArray(), stringExpectedFromServer.getBytes(StandardCharsets.UTF_8));
    }

    @Test(expectedExceptions = IOException.class)
    public void testNewSocketClient_withoutServer() throws Exception {
        SocketClient.PORT = 4567;
        SocketClient.createSocketClientWithSocket();
    }

    public void testSimpleSocket() throws IOException {
        SocketClient socketClient = new SocketClient();
        assertNull(socketClient.input);
        assertNull(socketClient.output);
        socketClient.socket = getSocketMockWithStreams();
        socketClient.setUpStreamsOnSocket();
        assertNotNull(socketClient.input);
        assertNotNull(socketClient.output);
    }

    public void testReadUpdateFromServer() throws IOException {
        String expected = "Test String";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(expected.getBytes());
        SocketClient socketClient = new SocketClient();
        socketClient.socket = getSocketMockWithStreams(byteArrayInputStream);
        socketClient.setUpStreamsOnSocket();
        String stringFromServer = socketClient.readUpdateFromServer();
        assertEquals(stringFromServer, expected);
    }

    public void testSetUpStreamsOnSocket() throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.socket = getSocketMockWithStreams();
        assertNull(socketClient.output);
        assertNull(socketClient.input);
        socketClient.setUpStreamsOnSocket();
        assertNotNull(socketClient.output);
        assertNotNull(socketClient.input);
    }

    public void testCreateUpdatesThreadAndRunIt() throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.socket = getSocketMockWithStreams();
        socketClient.setUpStreamsOnSocket();
        int threadCount = Thread.activeCount();
        assertNull(socketClient.updatesThread);
        socketClient.createUpdatesThreadAndRunIt();
        assertNotNull(socketClient.updatesThread);
        assertEquals(threadCount + 1, Thread.activeCount());
        socketClient.closeTheSocketClient();
    }

    public void testSendStringToServer_nullPointer() {
        SocketClient socketClient = new SocketClient();
        socketClient.sendStringToServer("TestString");
    }

    public void testReadUpdateFromServer_socketNull() throws IOException {
        SocketClient socketClient = new SocketClient();
        assertEquals("", socketClient.readUpdateFromServer());
    }

    private Socket getSocketMockWithStreams(ByteArrayInputStream byteArrayInputStream,
                                            ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        final Socket socket = mock(Socket.class);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        return socket;
    }

    private Socket getSocketMockWithStreams(ByteArrayInputStream byteArrayInputStream) throws IOException {
        final Socket socket = mock(Socket.class);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        return socket;
    }

    private Socket getSocketMockWithStreams() throws IOException {
        byte[] emptyPayload = new byte[1001];
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(emptyPayload);
        return getSocketMockWithStreams(byteArrayInputStream, byteArrayOutputStream);
    }
}