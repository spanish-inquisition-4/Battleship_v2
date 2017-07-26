package com.spanish_inquisition.battleship.client.board;

import com.spanish_inquisition.battleship.common.AdjacentFieldsCalc;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * @author Michal_Partacz
 * This class will make sure the board of buttons is properly initialized and with every event handlers needed.
 */
public class GameBoardBuilder {
    private static final int BOARD_SIZE_WITH_LABELS = 11;
    private static final double FIELD_SIZE = 10;
    private GridPane gridPane;
    private BoardController boardController;

    public GameBoardBuilder(BoardController boardController) {
        this.boardController = boardController;
        this.gridPane = boardController.getBoardGridPane();
    }

    public GameBoard buildGameBoard() {
        fillTheBoardWithButtonsAndLabels();
        return null;
    }

    private void fillTheBoardWithButtonsAndLabels() {
        for (int column = 0; column < BOARD_SIZE_WITH_LABELS; column++) {
            Platform.runLater(() -> gridPane.getRowConstraints().add(new RowConstraints(FIELD_SIZE)));
            for (int row = 0; row < BOARD_SIZE_WITH_LABELS; row++) {
                if (column == 0 && row == 0) {
                    Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(FIELD_SIZE)));
                } else if (column == 0) {
                    Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(FIELD_SIZE)));
                    addHorizontalLabelOnIndex(row);
                } else if (row == 0) {
                    addVerticalLabelOnIndex(column);
                } else {
                    createNewBoardTileAndAddIt(column, row);
                }
            }
        }
    }

    private void createNewBoardTileAndAddIt(int absoluteXCoordinate, int absoluteYCoordinate) {
        int boardIndex = AdjacentFieldsCalc.translateCoordinatesToIndex(absoluteXCoordinate - 1, absoluteYCoordinate - 1);
        BoardTile tile = new BoardTile(boardIndex);
        tile.setOnMouseClicked(getOnBoardTileClickedEvent(tile, boardController));
        Platform.runLater(() -> this.gridPane.add(tile, absoluteXCoordinate, absoluteYCoordinate));
        boardController.getGameBoard().addBoardTileToHashMap(boardIndex, tile);
        fillGridPaneHeightAndWidthForNode(tile);
    }

    private void addVerticalLabelOnIndex(int columnIndex) {
        Label boardBoardTileLabel = new Label(String.valueOf(columnIndex));
        Platform.runLater(() -> this.gridPane.add(boardBoardTileLabel, 0, columnIndex));
        fillGridPaneHeightAndWidthForNode(boardBoardTileLabel);
    }

    private void addHorizontalLabelOnIndex(int rowIndex) {
        Label boardBoardTileLabel = new Label(String.valueOf((char) ('A' + rowIndex - 1)));
        Platform.runLater(() -> this.gridPane.add(boardBoardTileLabel, rowIndex, 0));
        fillGridPaneHeightAndWidthForNode(boardBoardTileLabel);
    }

    private void fillGridPaneHeightAndWidthForNode(Node node) {
        GridPane.setFillHeight(node, true);
        GridPane.setFillWidth(node, true);
    }

    EventHandler<MouseEvent> getOnBoardTileClickedEvent(BoardTile tile, BoardController boardController) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //do something when the button is closed
            }
        };
    }
}
