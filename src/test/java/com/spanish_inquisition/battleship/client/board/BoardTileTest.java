package com.spanish_inquisition.battleship.client.board;

import com.spanish_inquisition.battleship.common.Styles;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class BoardTileTest {

    private final int INDEX_ONE = 1;

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void shouldSetLabels() {
        // Given
        BoardTile tile = new BoardTile(INDEX_ONE);
        Label horizontalLabel = new Label();
        Label verticalLabel = new Label();

        // When
        tile.setLabels(horizontalLabel, verticalLabel);

        // Then
        assertEquals(horizontalLabel, tile.horizontalLabel);
        assertEquals(verticalLabel, tile.verticalLabel);
    }



    @Test
    public void shouldSetTileStyle_withLabels() {
        BoardTile boardTile = new BoardTile(1);
        Label verticalLabel = new Label();
        Label horizontalLabel = new Label();
        boardTile.verticalLabel = verticalLabel;
        boardTile.horizontalLabel = horizontalLabel;
        Paint oldTextColor = Styles.TEXT_BLACK;
        assertEquals(horizontalLabel.getTextFill(),oldTextColor);
        assertEquals(verticalLabel.getTextFill(), oldTextColor);

        boardTile.verticalLabel = verticalLabel;
        boardTile.horizontalLabel = horizontalLabel;
        String oldStyle = Styles.DEFAULT_TILE_COLOR;
        String newStyle = Styles.RESPONSE_DESTROYED;
        assertTrue(boardTile.getStyle().contains(oldStyle));
        Paint newTextStyle = Styles.TEXT_LIME;
        boardTile.setTileStyle(newStyle, newTextStyle);
        assertEquals(horizontalLabel.getTextFill(), newTextStyle);
        assertEquals(verticalLabel.getTextFill(), newTextStyle);
        assertTrue(boardTile.getStyle().contains(newStyle));
    }

    @Test
    public void shouldSetTileLabelsStyles() {
        BoardTile boardTile = new BoardTile(1);
        Label verticalLabel = new Label();
        Label horizontalLabel = new Label();
        boardTile.verticalLabel = verticalLabel;
        boardTile.horizontalLabel = horizontalLabel;
        Paint oldTextColor = Styles.TEXT_BLACK;
        assertEquals(horizontalLabel.getTextFill(),oldTextColor);
        assertEquals(verticalLabel.getTextFill(), oldTextColor);
        Paint newStyle = Styles.TEXT_LIME;
        boardTile.setTileStyle(newStyle);
        assertEquals(horizontalLabel.getTextFill(), newStyle);
        assertEquals(verticalLabel.getTextFill(), newStyle);
    }

    @Test
    public void shouldSetTileLabelsStyles_nullValues() {
        BoardTile boardTile = new BoardTile(1);
        assertNull(boardTile.horizontalLabel);
        assertNull(boardTile.verticalLabel);
        Paint newTestStyle = Styles.TEXT_LIME;
        String colorStyle = Styles.DEFAULT_TILE_COLOR;
        boardTile.setTileStyle(colorStyle, newTestStyle);
    }

    @Test
    public void shouldReturnBoardIndex() {
        int boardIndex =5;
        BoardTile boardTile = new BoardTile(boardIndex);
        assertEquals(boardIndex, boardTile.getBoardIndex());
    }
}