package com.spanish_inquisition.battleship.server;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static com.spanish_inquisition.battleship.common.AppLogger.defaultLevel;
import static com.spanish_inquisition.battleship.common.AppLogger.initializeLogger;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class BattleshipServerTestIT {
    private ServerSocket serverSocket;
    private final String SERVER_ADDRESS = "localhost";
    private final int TEST_PORT = 6660;
    private Socket client1;
    private Socket client2;

    @BeforeClass
    public void beforeClass() {
        initializeLogger();
    }

    @BeforeMethod
    public void beforeMethod() {
        try {
            serverSocket = new ServerSocket(TEST_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldConnectWithTwoPlayers() {
        // Given
        new Thread(() -> {
            try {
                client1 = new Socket(SERVER_ADDRESS, TEST_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();
        new Thread(() -> {
            try {
                client2 = new Socket(SERVER_ADDRESS, TEST_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();
        // When
        List<Socket> testClients = BattleshipServer.connectWithPlayers(serverSocket);
        // Then
        Assert.assertEquals(2, testClients.size());
    }

    @Test
    public void shouldCreateServerSocket() {
        // When
        ServerSocket testServerSocket = BattleshipServer.createServerSocket();
        // Then
        Assert.assertNotNull(testServerSocket);
    }

    @AfterMethod
    public void afterMethod() {
        try {
            serverSocket.close();
            client1.close();
            client2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}