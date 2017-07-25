package com.spanish_inquisition.battleship.client.network;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Michal_Partacz
 */
@Test
public class SocketClientTest {

    @Test(expectedExceptions = IOException.class)
    public void testNewSocketClient_withoutServer() throws Exception {
        SocketClient socketClient = new SocketClient();
        socketClient.setUpStreamsAndOpenSocket();
    }

    @Test
    public void testNewSocketClient_withServer() throws Exception {
        DummyServer dummyServer = new DummyServer();
        new Thread(dummyServer::runThread).start();
        SocketClient socketClient = new SocketClient();
        socketClient.setUpStreamsAndOpenSocket();
    }

    class DummyServer {
        ServerSocket serverSocket;
        void runThread() {
            try {
                serverSocket = new ServerSocket(SocketClient.PORT);
                serverSocket.accept();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}