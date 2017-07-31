package com.spanish_inquisition.battleship.client;

import com.spanish_inquisition.battleship.client.board.BoardController;
import com.spanish_inquisition.battleship.client.board.GameBoard;
import com.spanish_inquisition.battleship.client.game.Game;
import com.spanish_inquisition.battleship.client.network.SocketClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    @FXML
    public VBox centralVBox;
    @FXML
    public TextField nameTextField;
    @FXML
    public Button featureButton;
    @FXML
    public HBox gameHBox;
    @FXML
    public VBox playersVBox;
    @FXML
    public Label playersLabel;
    @FXML
    public GridPane playersGridPane;
    @FXML
    public VBox opponentsVBox;
    @FXML
    public VBox playerNameVBox;
    @FXML
    public Label opponentsLabel;
    @FXML
    public GridPane opponentsGridPane;
    @FXML
    public Label gameStatusLabel;
    @FXML
    private VBox fleetSetupVBox;
    @FXML
    private Button fleetSetupButton;

    private SocketClient socketClient;
    private Game game;

    /**
     * This method is run automatically right after the fxml file's loaded
     */
    @FXML
    public void initialize() {
        try {
            this.socketClient = SocketClient.createSocketClientWithSocket();
        } catch (IOException e) {
            logger.log(Level.WARNING, "The client could not connect to the server", e);
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
        this.sendTextToSocketAndStartANewGame(this.nameTextField.getText());
    }

    @FXML
    public void onNameTextFieldEntered() {
        this.sendTextToSocketAndStartANewGame(this.nameTextField.getText());
    }

    private void sendTextToSocketAndStartANewGame(String text) {
        if(this.socketClient != null)
            this.socketClient.sendStringToServer(text);
        this.playerNameVBox.setVisible(false);
        runTheGame();
    }

    private void runTheGame() {
        this.playersLabel.setText("Set Up your ships");
        this.game = new Game();
        this.game.buildPlayersBoard(new BoardController(new GameBoard(this.playersGridPane)));
        this.fleetSetupButton.setVisible(true);
    }

    @FXML
    public void onFleetSetupButtonClicked(){
        this.game.placePlayersShips();
    }
}
