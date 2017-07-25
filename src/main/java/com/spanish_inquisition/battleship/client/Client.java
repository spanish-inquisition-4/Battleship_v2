package com.spanish_inquisition.battleship.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Michal_Partacz
 * A main class for Client which will load the fxml file containing the UI's elements and shows them on the new window.
 * The class also contains information about the window's height and width
 */
public class Client extends Application {
    private static final int SCENE_WIDTH = 900;
    private static final int SCENE_HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("Battleships");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
