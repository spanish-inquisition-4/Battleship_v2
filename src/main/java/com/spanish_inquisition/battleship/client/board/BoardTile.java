package com.spanish_inquisition.battleship.client.board;

import com.spanish_inquisition.battleship.common.Styles;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

/**
 * @author Michal_Partacz
 */
public class BoardTile extends Button {
    private static final double MAX_SIZE = 200;
    private int boardIndex;
    Label horizontalLabel;
    Label verticalLabel;

    BoardTile(int boardIndex) {
        this.boardIndex = boardIndex;
        setMaxSize(MAX_SIZE, MAX_SIZE);
        setStyle(Styles.DEFAULT_TILE_COLOR);
    }

    void setLabels(Label horizontalLabel, Label verticalLabel) {
        this.horizontalLabel = horizontalLabel;
        this.verticalLabel = verticalLabel;
    }

    public void setTileStyle(String style, Paint paint) {
        setStyle(style);
        if (horizontalLabel != null) {
            horizontalLabel.setTextFill(paint);
        }
        if (verticalLabel != null) {
            verticalLabel.setTextFill(paint);
        }
    }

    void setTileStyle(Paint paint) {
        if (horizontalLabel != null) {
            horizontalLabel.setTextFill(paint);
        }
        if (verticalLabel != null) {
            verticalLabel.setTextFill(paint);
        }
    }

    public int getBoardIndex() {
        return boardIndex;
    }
}