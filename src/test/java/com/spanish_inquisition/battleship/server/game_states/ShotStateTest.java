package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.client.game.Game;
import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.server.ClientConnectionHandler;
import com.spanish_inquisition.battleship.server.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.fleet.FleetBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;
import static org.testng.Assert.*;

public class ShotStateTest {
    Players players;
    private int TEST_PORT = 5552;
    MessageBus messageBus;
    ServerSocket ss;
    String SERVER_ADDRESS = "localhost";

    @BeforeTest
    public void initialize() {
        messageBus = new MessageBus();
        try {
            ss = new ServerSocket(TEST_PORT);
        } catch (IOException e) {
            logger.log(Level.WARNING, "couldn't create server socket", e);
        }
        new Thread(() -> assignSocketAndNameTo("Name 1")).run();
        new Thread(() -> assignSocketAndNameTo("Name 2")).run();
        players = new Players();
        for(int i = 1; i < 3; i++) {
            ClientConnectionHandler cch = new ClientConnectionHandler(i, messageBus);
            cch.start();
            try {
                cch.initializeSocket(ss);
            } catch (IOException e) {
                logger.log(Level.WARNING, "couldn't connect with server", e);
            }
            players.addPlayer(new Player(cch));
        }
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
    public void testShootMessageWithInvalidHeader() {
        GameState ss = new ShotState(players, messageBus);
        messageBus.addMessage(1,0, Header.FLEET_REQUEST.name()+":1;");
        ss = ss.transform();
        Assert.assertEquals(ShotState.class, ss.getClass());
    }

    @Test
    public void testShootMessageWithValidHeader() {
        GameState ss = new ShotState(players, messageBus);
        messageBus.addMessage(1,0, Header.MOVE_REGULAR.name()+":1;");
        players.getOpponentOf(players.getCurrentPlayer()).setFleet(
                new FleetBuilder().build(
                        Header.FLEET_REQUEST.name() +
                                ":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];"
                ));
        ss = ss.transform();
        Assert.assertEquals(PlayerActionState.class, ss.getClass());
    }

    @AfterTest
    public void closeConnections(){
        try {
            ss.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "couldn't close server",e);
        }
    }
}