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
    private BattleshipServer battleshipServer;
    private ServerSocket serverSocket;
    private final String SERVER_ADDRESS = "localhost";
    private final int TEST_PORT = 5660;

    @BeforeClass
    public void beforeClass() {
        initializeLogger();
    }

    @BeforeMethod
    public void beforeMethod() {
        battleshipServer = new BattleshipServer();
        try {
            serverSocket = new ServerSocket(TEST_PORT);
        } catch (IOException e) {
            logger.log(Level.WARNING, "could't create server socket", e);
        }
    }

    @Test
    public void shouldConnectWithTwoPlayers() {
        // Given
        new Thread(() -> assignSocketAndNameTo("Name 1")).run();
        new Thread(() -> assignSocketAndNameTo("Name 2")).run();
        // When
        battleshipServer.connectWithPlayers(serverSocket);
        // Then
        Assert.assertEquals(battleshipServer.NUMBER_OF_PLAYERS, battleshipServer.clients.size());
    }

    @Test
    public void shouldReadNamesFromClients() throws InterruptedException {
        // Given
        new Thread(() -> assignSocketAndNameTo("Name 1")).run();
        new Thread(() -> assignSocketAndNameTo("Name 2")).run();
        battleshipServer.connectWithPlayers(serverSocket);
        battleshipServer.clients.get(0).disconnect();
        battleshipServer.clients.get(1).disconnect();
        battleshipServer.clients.get(0).join();
        battleshipServer.clients.get(1).join();
        Assert.assertEquals(battleshipServer.clients.get(0).name, "Name 1");
        Assert.assertEquals(battleshipServer.clients.get(1).name, "Name 2");
    }

    private void assignSocketAndNameTo(String nameString) {
        try {
            Socket theClient = new Socket(SERVER_ADDRESS, TEST_PORT);
            PrintWriter writer = new PrintWriter(theClient.getOutputStream(), true);
            writer.println(nameString);
            theClient.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "could't connect to server", e);
        }
    }

    @Test
    public void shouldCreateServerSocket() {
        int CREATION_TEST_PORT = 22202;
        // When
        ServerSocket testServerSocket = battleshipServer.createServerSocket(CREATION_TEST_PORT);
        // Then
        Assert.assertNotNull(testServerSocket);
    }

    @AfterMethod
    public void afterMethod() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "could't close sockets", e);
        }
    }
}