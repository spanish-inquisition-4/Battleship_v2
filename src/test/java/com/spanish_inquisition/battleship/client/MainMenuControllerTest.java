package com.spanish_inquisition.battleship.client;

import com.spanish_inquisition.battleship.client.game.Game;
import com.spanish_inquisition.battleship.client.network.SocketClient;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author Michal_Partacz
 */
public class MainMenuControllerTest {
    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void testOnFeatureButtonClicked() throws Exception {
        MainMenuController mainMenuController = getControllerInstance();
        mainMenuController.nameTextField.setText("DummyText");
        assertTrue(mainMenuController.playerNameVBox.isVisible());
        int threadCount = Thread.activeCount();
        mainMenuController.onFeatureButtonClicked();
        assertEquals(threadCount + 1, Thread.activeCount());
        assertFalse(mainMenuController.playerNameVBox.isVisible());
    }

    @Test
    public void testOnNameTextFieldEntered() throws Exception {
        MainMenuController mainMenuController = getControllerInstance();
        mainMenuController.nameTextField.setText("DummyText");
        assertTrue(mainMenuController.playerNameVBox.isVisible());
        int threadCount = Thread.activeCount();
        mainMenuController.onNameTextFieldEntered();
        assertEquals(threadCount + 1, Thread.activeCount());
        assertFalse(mainMenuController.playerNameVBox.isVisible());
    }

    @Test
    public void testOnFleetSetupButtonClicked() throws Exception {
        MainMenuController mainMenuController = getControllerInstance();
        mainMenuController.sendToServerButton.setVisible(false);
        mainMenuController.onFleetSetupButtonClicked();
        assertTrue(mainMenuController.sendToServerButton.isVisible());
    }

    @Test
    public void testOnSendToServerButtonClicked() throws Exception {
        SocketClient socketClient = mock(SocketClient.class);
        MainMenuController mainMenuController = getControllerInstance();
        mainMenuController.socketClient = socketClient;
        assertFalse(mainMenuController.fleetSetupButton.isDisabled());
        when(mainMenuController.game.getShipPlacementForServer()).thenReturn("");
        mainMenuController.onSendToServerButtonClicked();
        assertFalse(mainMenuController.fleetSetupButton.isVisible());
    }

    private MainMenuController getControllerInstance() {
        Game game = mock(Game.class);
        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.game = game;
        mainMenuController.playerNameVBox = new VBox();
        mainMenuController.playersGridPane = new GridPane();
        mainMenuController.opponentsGridPane = new GridPane();
        mainMenuController.mainBorderPane = new BorderPane();
        mainMenuController.fleetSetupButton = new Button();
        mainMenuController.sendToServerButton = new Button();
        mainMenuController.playersLabel = new Label();
        mainMenuController.gameStatusLabel = new Label();
        mainMenuController.nameTextField = new TextField();
        return mainMenuController;
    }

}