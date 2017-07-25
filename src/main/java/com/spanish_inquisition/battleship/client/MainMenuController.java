package com.spanish_inquisition.battleship.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Michal_Partacz
 * A controller for a fxml file placed in resources folder. The methods annotated with @FXML react to events specified
 * in the file's UI elements.
 */
public class MainMenuController {

    public Button featureButton;
    /**
     * This method is run automatically right after the fxml file's loaded
     */
    @FXML
    public void initialize() {}

    @FXML
    public void onFeatureButtonClicked() {
        System.out.println("Clicked!");
    }
}
