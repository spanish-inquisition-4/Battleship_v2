package com.spanish_inquisition.battleship.server.game_states;

import com.spanish_inquisition.battleship.common.Header;
import com.spanish_inquisition.battleship.common.NetworkMessage;
import com.spanish_inquisition.battleship.server.ClientConnectionHandler;
import com.spanish_inquisition.battleship.server.bus.Message;
import com.spanish_inquisition.battleship.server.bus.MessageBus;
import com.spanish_inquisition.battleship.server.Player;
import com.spanish_inquisition.battleship.server.Players;
import com.spanish_inquisition.battleship.server.fleet.FleetBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

public class ShotStateTest {
    private Players players;
    private final int TEST_PORT = 5533;
    private final List<Integer> CLIENT_IDS = Arrays.asList(1, 2);
    private MessageBus messageBus;
    private ServerSocket serverSocket;
    private final String SERVER_ADDRESS = "localhost";

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

    @Test
    public void shouldReturnShotStateForMessageWithInvalidHeader() {
        GameState gameState = new ShotState(players, messageBus);
        int targetedPoint = 1;
        messageBus.addMessage(
                1,
                0,
                Header.FLEET_REQUEST.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
        );
        gameState = gameState.transform();
        Assert.assertEquals(ShotState.class, gameState.getClass());
    }

    @Test
    public void shouldReturnPlayerActionStateForMessageWithCorrectHeader() {
        GameState gameState = new ShotState(players, messageBus);
        Integer targetedPoint = 1;
        messageBus.addMessage(
                1,
                0,
                Header.MOVE_REGULAR.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
        );
        players.getOpponentOf(players.getCurrentPlayer()).setFleet(
                new FleetBuilder().build(
                        Header.FLEET_REQUEST.name() +
                                ":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];"
                ));
        gameState = gameState.transform();
        Assert.assertEquals(PlayerActionState.class, gameState.getClass());
    }

    @Test
    public void shouldSendAppropriateMessagesToClient() {
        GameState gameState = new ShotState(players, messageBus);
        players.getOpponentOf(players.getCurrentPlayer()).setFleet(
                new FleetBuilder().build(
                        Header.FLEET_REQUEST.name() +
                                ":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];"
                ));
        Integer targetedPoint = 9;
        messageBus.addMessage(
                1,
                0,
                Header.MOVE_REGULAR.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
        );
        gameState = gameState.transform();

        Message decideOnMove = messageBus.getMessageFrom(0);
        Message responseHit = messageBus.getMessageFrom(0);
        Message responseOpponentHit = messageBus.getMessageFrom(0);
        String decideOnMoveExpectedContent = Header.DECIDE_ON_MOVE.name();
        String responseHitExpectedContent =
                Header.RESPONSE_HIT.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
                ;
        String responseOpponentHitExpectedContent =
                Header.RESPONSE_OPPONENT_HIT.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
                ;
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(0, decideOnMove.getSender());
        soft.assertEquals(1, decideOnMove.getRecipient());
        soft.assertEquals(decideOnMoveExpectedContent, decideOnMove.getContent());
        soft.assertEquals(0, responseHit.getSender());
        soft.assertEquals(1, decideOnMove.getRecipient());
        soft.assertEquals(responseHitExpectedContent, responseHit.getContent());
        soft.assertEquals(0, responseOpponentHit.getSender());
        soft.assertEquals(1, responseOpponentHit.getRecipient());
        soft.assertEquals(responseOpponentHitExpectedContent, responseOpponentHit.getContent());
        soft.assertEquals(ShotState.class, gameState.getClass());
    }

    @Test
    public void shouldSendMessagesWithDestroyedShip() {
        GameState gameState = new ShotState(players, messageBus);
        players.getOpponentOf(players.getCurrentPlayer()).setFleet(
                new FleetBuilder().build(
                        Header.FLEET_REQUEST.name() +
                                ":[0,2,4,6,8,9,20,21,23,24,26,27,28,40,41,42,44,45,46,47];"
                ));
        Integer targetedPoint = 0;
        messageBus.addMessage(
                1,
                0,
                Header.MOVE_REGULAR.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
        );
        gameState = gameState.transform();
        for (int i = 0; i < 3; i++) {
            messageBus.getMessageFrom(0); //need to get to last two messages
        }
        Message responseDestroyedShip = messageBus.getMessageFrom(0);
        Message responseOpponentDestroyedShip = messageBus.getMessageFrom(0);
        String responseDestroyedShipExpectedContent =
                Header.RESPONSE_HIT.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
                ;
        String responseOpponentDestroyedShipExpectedContent =
                Header.RESPONSE_OPPONENT_HIT.name() +
                        NetworkMessage.RESPONSE_HEADER_SPLIT_CHARACTER +
                        targetedPoint +
                        NetworkMessage.RESPONSE_SPLIT_CHARACTER
                ;
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(0, responseDestroyedShip.getSender());
        soft.assertEquals(1, responseDestroyedShip.getRecipient());
        soft.assertEquals(responseDestroyedShipExpectedContent, responseDestroyedShip.getContent());
        soft.assertEquals(0, responseOpponentDestroyedShip.getSender());
        soft.assertEquals(2, responseOpponentDestroyedShip.getRecipient());
        soft.assertEquals(responseOpponentDestroyedShipExpectedContent, responseOpponentDestroyedShip.getContent());
        soft.assertEquals(ShotState.class, gameState.getClass());
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