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
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.initializeLogger;
import static com.spanish_inquisition.battleship.common.AppLogger.logger;

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
            logger.log(Level.WARNING, "could't create server socket", e);
        }
    }

    @Test
    public void shouldConnectWithTwoPlayers() {
        // Given
        new Thread(() -> assignSocketAndNameTo(client1, "Name 1")).run();
        new Thread(() -> assignSocketAndNameTo(client2, "Name 2")).run();
        // When
        BattleshipServer.connectWithPlayers(serverSocket);
        // Then
        Assert.assertEquals(BattleshipServer.NUMBER_OF_PLAYERS, BattleshipServer.clients.size());
    }

    @Test
    public void shouldReadNamesFromClients() throws InterruptedException {
        // Given
        new Thread(() -> assignSocketAndNameTo(client1, "Name 1")).run();
        new Thread(() -> assignSocketAndNameTo(client2, "Name 2")).run();
        BattleshipServer.connectWithPlayers(serverSocket);
        BattleshipServer.clients.get(0).join();
        BattleshipServer.clients.get(1).join();
        Assert.assertEquals(BattleshipServer.clients.get(0).name, "Name 1");
        Assert.assertEquals(BattleshipServer.clients.get(1).name, "Name 2");
    }

    private void assignSocketAndNameTo(Socket client, String nameString) {
        try {
            client = new Socket(SERVER_ADDRESS, TEST_PORT);
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            writer.println(nameString);
        } catch (IOException e) {
            logger.log(Level.WARNING, "could't connect to server", e);
        }
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
            if(client1 != null) client1.close();
            if(client2 != null) client2.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "could't close sockets", e);
        }
    }
}