package com.spanish_inquisition.battleship.client;

import com.spanish_inquisition.battleship.common.AppLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;

import static com.spanish_inquisition.battleship.common.AppLogger.logger;

/**
 * @author Michal_Partacz
 * A main class for Client which will load the fxml file containing the UI's elements and shows them on the new window.
 * The class also contains information about the window's height and width
 */
public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Battleships");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        AppLogger.initializeLogger();
        launch(args);
    }
}
