package com.spanish_inquisition.battleship.client.board.boardcontroller;

import com.spanish_inquisition.battleship.client.board.BoardTile;
import com.spanish_inquisition.battleship.client.board.GameBoard;
import com.spanish_inquisition.battleship.client.board.GameBoardBuilder;
import com.spanish_inquisition.battleship.client.game.FleetInitializer;
import com.spanish_inquisition.battleship.client.game.Game;
import com.spanish_inquisition.battleship.client.game.ServerMessageCreator;
import com.spanish_inquisition.battleship.common.Styles;
import javafx.application.Platform;

import java.util.Map;

public class PlayerBoardController extends BoardController {
    private FleetInitializer fleetInitializer;

    public PlayerBoardController(GameBoard gameBoard, Game game) {
        super(gameBoard, game);
    }

    @Override
    public void buildBoard() {
        GameBoardBuilder gameBoardBuilder = new GameBoardBuilder(this);
        fleetInitializer = new FleetInitializer(this);
        gameBoardBuilder.fillTheBoardWithButtonsAndLabels();
    }

    public void placeShips() {
        Map<Integer, BoardTile> indexTiles = gameBoard.getIndexTiles();
        Platform.runLater(() -> indexTiles.forEach((integer, boardTile) -> boardTile.setTileStyle(Styles.DEFAULT_TILE_COLOR, Styles.TEXT_BLACK)));
        fleetInitializer.setUpShips();
    }

    public String getFleetMessageForServer() {
        return ServerMessageCreator.createFleetMessage(fleetInitializer.getShipPlaces());
    }
}
