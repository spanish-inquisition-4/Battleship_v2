package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.BoardController;
import javafx.embed.swing.JFXPanel;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Michal_Partacz
 */
@Test
public class GameTest {

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void testBuildPlayersBoard() throws Exception {
        Game game = new Game();
        final BoardController boardController = mock(BoardController.class);
        assertNull(game.boardController);
        game.buildPlayersBoard(boardController);
        assertNotNull(game.boardController);
    }
}