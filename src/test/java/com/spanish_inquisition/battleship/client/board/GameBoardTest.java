package com.spanish_inquisition.battleship.client.board;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class GameBoardTest {

    private final int INDEX_ONE = 1;
    private final int INDEX_TWO = 2;
    private final int MAP_SIZE = 2;

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void shouldAddTileToHashMap() {
        // Given
        GridPane mockGridPane = mock(GridPane.class);
        GameBoard gameBoard = new GameBoard(mockGridPane);

        BoardTile tile1 = new BoardTile(INDEX_ONE);
        BoardTile tile2 = new BoardTile(INDEX_TWO);

        // When
        gameBoard.addBoardTileToHashMap(INDEX_ONE, tile1);
        gameBoard.addBoardTileToHashMap(INDEX_TWO, tile2);

        // Then
        assertEquals(MAP_SIZE, gameBoard.getIndexTiles().size());
    }
}
