package com.spanish_inquisition.battleship.server;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.spanish_inquisition.battleship.common.AppLogger.initializeLogger;

public class BattleshipServerTestIT {
    private ServerSocket serverSocket;
    private final String SERVER_ADDRESS = "localhost";
    private final int TEST_PORT = 6661;
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
        BattleshipServer.connectWithPlayers(serverSocket);
        // Then
        Assert.assertEquals(2, BattleshipServer.clients.size());
    }

    @Test
    public void shouldReadNamesFromClients() throws InterruptedException {
        // Given
        new Thread(() -> {
            try {
                client1 = new Socket(SERVER_ADDRESS, TEST_PORT);
                PrintWriter writer = new PrintWriter(client1.getOutputStream(), true);
                writer.println("Name 1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();
        new Thread(() -> {
            try {
                client2 = new Socket(SERVER_ADDRESS, TEST_PORT);
                PrintWriter writer = new PrintWriter(client2.getOutputStream(), true);
                writer.println("Name 2");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();

        BattleshipServer.connectWithPlayers(serverSocket);

        BattleshipServer.clients.get(0).join();
        BattleshipServer.clients.get(1).join();
        Assert.assertEquals(BattleshipServer.clients.get(0).name, "Name 1");
        Assert.assertEquals(BattleshipServer.clients.get(1).name, "Name 2");
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