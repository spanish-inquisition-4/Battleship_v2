package com.spanish_inquisition.battleship.client.game;

import com.spanish_inquisition.battleship.client.board.boardcontroller.BoardController;
import com.spanish_inquisition.battleship.client.board.boardcontroller.OpponentBoardController;
import com.spanish_inquisition.battleship.client.board.boardcontroller.PlayerBoardController;
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
    public void shouldBuildPlayersBoard() {
        Game game = new Game();
        final PlayerBoardController boardController = mock(PlayerBoardController.class);
        assertNull(game.playerBoardController);
        game.buildPlayersBoard(boardController);
        assertNotNull(game.playerBoardController);
    }

    @Test
    public void shouldBuildOpponentsBoard() {
        Game game = new Game();
        final OpponentBoardController boardController = mock(OpponentBoardController.class);
        assertNull(game.opponentBoardController);
        game.buildOpponentsBoard(boardController);
        assertNotNull(game.opponentBoardController);
    }
}