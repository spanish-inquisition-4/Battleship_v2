package com.spanish_inquisition.battleship.client;

import com.spanish_inquisition.battleship.common.AppLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

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
        setUpPortAndIpInController(loader);
        primaryStage.setTitle("Battleships");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        AppLogger.initializeLogger();
        launch(args);
    }

    private void setUpPortAndIpInController(FXMLLoader loader) {
        Parameters parameters = getParameters();
        List<String> argsRaw = parameters.getRaw();
        MainMenuController mainMenuController = loader.getController();
        String serverIP = argsRaw.isEmpty() ? "localhost" : argsRaw.get(0);
        mainMenuController.setHostIP(serverIP);
        if (argsRaw.size() > 1) {
            int port = Integer.parseInt(argsRaw.get(1));
            mainMenuController.setPort(port);
        }
    }
}
