package com.spanish_inquisition.battleship.server;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.server.fleet.Ship;
import com.spanish_inquisition.battleship.server.game_states.GameState;
import com.spanish_inquisition.battleship.server.game_states.PlacingShipsState;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class PlacingShipsStateTest {
    Players players;
    private int TEST_PORT = 5550;
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
    public void testIfFleetsAreAssigned() {
        GameState pss = new PlacingShipsState(players, messageBus);
        messageBus.addMessage(1,0, Header.FLEET_REQUEST.name()+":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];");
        messageBus.addMessage(2,0, Header.FLEET_REQUEST.name()+":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];");
        pss.transform();

        List<List<Ship>> playersShips = new ArrayList<>();
        List<Player> playersList = players.getBothPlayers();
        for(int i = 0; i < 2; i++) {
            playersShips.add(playersList.get(i).getFleet().getShips());
        }

        for(List<Ship> playerShips : playersShips) {
            Assert.assertEquals(10, playerShips.size());
            Assert.assertEquals(1, playerShips.get(0).getShipPoints().size());
            Assert.assertEquals(1, playerShips.get(1).getShipPoints().size());
            Assert.assertEquals(1, playerShips.get(2).getShipPoints().size());
            Assert.assertEquals(1, playerShips.get(3).getShipPoints().size());
            Assert.assertEquals(2, playerShips.get(4).getShipPoints().size());
            Assert.assertEquals(2, playerShips.get(5).getShipPoints().size());
            Assert.assertEquals(2, playerShips.get(6).getShipPoints().size());
            Assert.assertEquals(3, playerShips.get(7).getShipPoints().size());
            Assert.assertEquals(3, playerShips.get(8).getShipPoints().size());
            Assert.assertEquals(4, playerShips.get(9).getShipPoints().size());
        }
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