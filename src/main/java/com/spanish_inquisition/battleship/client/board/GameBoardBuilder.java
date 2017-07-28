package com.spanish_inquisition.battleship.client.board;

import com.spanish_inquisition.battleship.common.AdjacentTilesCalc;
import com.spanish_inquisition.battleship.common.Styles;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Michal_Partacz
 * This class will make sure the board of buttons is properly initialized and with every event handlers needed.
 */
public class GameBoardBuilder {
    public static final int BOARD_SIZE_WITH_LABELS = 11;
    public static final double FIELD_SIZE = 30;
    private GridPane gridPane;
    private BoardController boardController;
    private Map<Integer, Label> verticalLabels = new HashMap<>();
    private Map<Integer, Label> horizontalLabels = new HashMap<>();

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
                    addVerticalLabelOnIndex(row);
                } else if (row == 0) {
                    addHorizontalLabelOnIndex(column);
                } else {
                    createNewBoardTileAndAddIt(column, row);
                }
            }
        }
        addCorrespondingLabelsToTiles();
    }

    private void addCorrespondingLabelsToTiles() {
            for (Map.Entry<Integer, BoardTile> entry : boardController.getBoardsIndexTiles().entrySet()) {
            BoardTile tile = entry.getValue();
            int boardIndex = entry.getKey();
            int[] tileCoordinates = AdjacentTilesCalc.translateIndexToCoordinates(boardIndex);
            tile.setLabels(horizontalLabels.get(tileCoordinates[1] + 1), verticalLabels.get(tileCoordinates[0] + 1));
            setButtonsHoverOverEvents(tile);
        }
    }

    private void createNewBoardTileAndAddIt(int absoluteXCoordinate, int absoluteYCoordinate) {
        int boardIndex = AdjacentTilesCalc.translateCoordinatesToIndex(absoluteXCoordinate - 1, absoluteYCoordinate - 1);
        BoardTile tile = new BoardTile(boardIndex);
        tile.setOnMouseClicked(getOnBoardTileClickedEvent(tile, boardController));
        Platform.runLater(() -> this.gridPane.add(tile, absoluteXCoordinate, absoluteYCoordinate));
        boardController.getGameBoard().addBoardTileToHashMap(boardIndex, tile);
        fillGridPaneHeightAndWidthForNode(tile);
    }

    private void addHorizontalLabelOnIndex(int rowIndex) {
        Label boardBoardTileLabel = buildCenteredLabelWithText(String.valueOf(rowIndex));
        Platform.runLater(() -> this.gridPane.add(boardBoardTileLabel, 0, rowIndex));
        fillGridPaneHeightAndWidthForNode(boardBoardTileLabel);
        horizontalLabels.put(rowIndex, boardBoardTileLabel);
    }

    private void addVerticalLabelOnIndex(int columnIndex) {
        Label boardBoardTileLabel = buildCenteredLabelWithText(String.valueOf((char) ('A' + columnIndex - 1)));
        Platform.runLater(() -> this.gridPane.add(boardBoardTileLabel, columnIndex, 0));
        fillGridPaneHeightAndWidthForNode(boardBoardTileLabel);
        verticalLabels.put(columnIndex, boardBoardTileLabel);
    }

    private void fillGridPaneHeightAndWidthForNode(Node node) {
        GridPane.setFillHeight(node, true);
        GridPane.setFillWidth(node, true);
    }

    private Label buildCenteredLabelWithText(String text) {
        Label boardBoardTileLabel = new Label(text);
        boardBoardTileLabel.setAlignment(Pos.CENTER);
        boardBoardTileLabel.setMaxSize(200, 200);
        return boardBoardTileLabel;
    }

    EventHandler<MouseEvent> getOnBoardTileClickedEvent(BoardTile tile, BoardController boardController) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //do something when the button is closed
            }
        };
    }

    private void setButtonsHoverOverEvents(BoardTile tile) {
        tile.setOnMouseEntered(event -> tile.setTileStyle(Styles.MOUSE_ENTERED, Styles.TEXT_LIME));
        tile.setOnMouseExited(event -> tile.setTileStyle(Styles.DEFAULT_TILE_COLOR, Styles.TEXT_BLACK));
    }
}
