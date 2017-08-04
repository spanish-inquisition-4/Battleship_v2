package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.server.ClientConnectionHandler;
import com.spanish_inquisition.battleship.server.bus.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.fleet.Ship;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.IntStream;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class PlacingShipsStateTest {
    private int TEST_PORT = 5559;
    private final int NO_OF_PLAYERS = 2;
    private final String SERVER_ADDRESS = "localhost";
    private final List<Integer> CLIENT_IDS = Arrays.asList(1, 2);
    private static final int ONE_MAST_SIZE = 1;
    private static final int TWO_MAST_SIZE = 2;
    private static final int THREE_MAST_SIZE = 3;
    private static final int FOUR_MAST_SIZE = 4;
    private static final int NO_OF_SHIPS = 10;
    private Players players;
    private MessageBus messageBus;
    private ServerSocket serverSocket;

    @DataProvider
    private Object[][] messageProvider() {
        return new Object[][]{
                {Arrays.asList(
                        Header.FLEET_REQUEST.name() + ":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];",
                        Header.FLEET_REQUEST.name() + ":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];"),
                        Arrays.asList(1, 2), 0}
        };
    }

    @BeforeTest
    public void initialize() {
        messageBus = new MessageBus();
        try {
            serverSocket = new ServerSocket(TEST_PORT);
        } catch (IOException e) {
            logger.log(Level.WARNING, "couldn't create server socket", e);
        }
        new Thread(() -> assignSocketAndNameTo("Name 1")).run();
        new Thread(() -> assignSocketAndNameTo("Name 2")).run();
        players = new Players();
        CLIENT_IDS.forEach(integer -> {
            ClientConnectionHandler handler = new ClientConnectionHandler(integer, messageBus);
            handler.start();
            try {
                handler.initializeSocket(serverSocket);
            } catch (IOException e) {
                logger.log(Level.WARNING, "couldn't connect with server", e);
            }
            players.addPlayer(new Player(handler));
        });
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

    @Test(dataProvider = "messageProvider")
    public void testIfFleetsAreAssigned(List<String> messages, List<Integer> senderIdList, int recipientId) {
        // Given
        GameState gameState = new PlacingShipsState(players, messageBus);
        IntStream.range(0, messages.size())
                .forEach(i -> messageBus.addMessage(senderIdList.get(i), recipientId, messages.get(i)));
        gameState.transform();

        // When
        List<Player> playersList = players.getBothPlayers();
        List<List<Ship>> playersShips = new ArrayList<>();
        for (int i = 0; i < NO_OF_PLAYERS; i++) {
            playersShips.add(playersList.get(i).getFleet().getShips());
        }

        // Then
        for (List<Ship> playerShips : playersShips) {
            SoftAssert soft = new SoftAssert();
            soft.assertEquals(NO_OF_SHIPS, playerShips.size());
            soft.assertEquals(ONE_MAST_SIZE, playerShips.get(0).getShipPoints().size());
            soft.assertEquals(ONE_MAST_SIZE, playerShips.get(1).getShipPoints().size());
            soft.assertEquals(ONE_MAST_SIZE, playerShips.get(2).getShipPoints().size());
            soft.assertEquals(ONE_MAST_SIZE, playerShips.get(3).getShipPoints().size());
            soft.assertEquals(TWO_MAST_SIZE, playerShips.get(4).getShipPoints().size());
            soft.assertEquals(TWO_MAST_SIZE, playerShips.get(5).getShipPoints().size());
            soft.assertEquals(TWO_MAST_SIZE, playerShips.get(6).getShipPoints().size());
            soft.assertEquals(THREE_MAST_SIZE, playerShips.get(7).getShipPoints().size());
            soft.assertEquals(THREE_MAST_SIZE, playerShips.get(8).getShipPoints().size());
            soft.assertEquals(FOUR_MAST_SIZE, playerShips.get(9).getShipPoints().size());
        }
    }

    @AfterTest
    public void closeConnections() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "couldn't close server", e);
        }
    }
}