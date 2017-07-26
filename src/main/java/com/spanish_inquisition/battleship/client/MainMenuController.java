package com.spanish_inquisition.battleship.client;

import com.spanish_inquisition.battleship.client.network.SocketClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 * A controller for a fxml file placed in resources folder. The methods annotated with @FXML react to events specified
 * in the file's UI elements.
 */
public class MainMenuController {
    public Button featureButton;
    public Label gameStatusLabel;
    public TextField nameTextField;
    public VBox centralVBox;

    public SocketClient socketClient;

    /**
     * This method is run automatically right after the fxml file's loaded
     */
    @FXML
    public void initialize() {
        this.socketClient = new SocketClient();
        try {
            this.socketClient.setUpStreamsAndOpenSocket();
        } catch (IOException e) {
            logger.log(Level.WARNING, "The client could not connect to the server: " + e.getMessage());
            gameStatusLabel.setText("I could not connect to the server :(");
        }
        initializeElementsAfterServerConnection();
    }

    private void initializeElementsAfterServerConnection() {
        this.centralVBox.setVisible(true);
    }

    /**
     * After the button is clicked the information entered in the textField is sent to the server
     */
    @FXML
    public void onFeatureButtonClicked() {
        this.sendTextToSocket(this.nameTextField.getText());
    }

    private void sendTextToSocket(String text) {
        this.socketClient.sendStringToServer(text);
    }

    @FXML
    public void onNameTextFieldEntered() {
        this.sendTextToSocket(this.nameTextField.getText());
    }
}
