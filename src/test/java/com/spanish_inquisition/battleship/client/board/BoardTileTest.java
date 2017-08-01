package com.spanish_inquisition.battleship.client.board;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class BoardTileTest {

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void shouldSetLabels(){
        // Given
        BoardTile tile = new BoardTile(1);
        Label horizontalLabel = new Label();
        Label verticalLabel = new Label();

        // When
        tile.setLabels(horizontalLabel, verticalLabel);

        // Then
        Assert.assertEquals(horizontalLabel, tile.horizontalLabel);
        Assert.assertEquals(verticalLabel, tile.verticalLabel);
    }
}
