package com.spanish_inquisition.battleship.client.board;

import com.spanish_inquisition.battleship.client.board.boardcontroller.PlayerBoardController;
import com.spanish_inquisition.battleship.client.game.Game;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import static org.testng.Assert.assertEquals;

public class GameBoardBuilderTest {

    private static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void shouldBuildGameBoard() throws InterruptedException {
        // Given
        Game game = new Game();
        GridPane pane = new GridPane();
        GameBoard board = new GameBoard(pane);
        PlayerBoardController controller = new PlayerBoardController(board, game);
        GameBoardBuilder boardBuilder = new GameBoardBuilder(controller);

        // When
        boardBuilder.buildGameBoard();
        waitForRunLater();

        // Then
        assertEquals(boardBuilder.gridPane.getColumnConstraints().size(),
                GameBoardBuilder.BOARD_SIZE_WITH_LABELS);
        assertEquals(boardBuilder.gridPane.getRowConstraints().size(),
                GameBoardBuilder.BOARD_SIZE_WITH_LABELS);
    }
}