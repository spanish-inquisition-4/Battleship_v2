package com.spanish_inquisition.battleship.client.board;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static org.testng.Assert.assertEquals;

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
}