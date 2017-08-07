package com.spanish_inquisition.battleship.client.board.boardcontroller;

import com.spanish_inquisition.battleship.client.board.BoardTile;
import com.spanish_inquisition.battleship.client.board.GameBoard;
import com.spanish_inquisition.battleship.client.game.Game;
import com.spanish_inquisition.battleship.common.Styles;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Michal_Partacz
 */
public class BoardControllerTest {

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @Test
    public void testGetBoardGridPane() throws Exception {
        GameBoard gameBoard = mock(GameBoard.class);
        GridPane gridPane = new GridPane();
        when(gameBoard.getGridPane()).thenReturn(gridPane);
        BoardController boardController = new PlayerBoardController(gameBoard, new Game());
        assertEquals(gridPane, boardController.getBoardGridPane());
    }

    @Test
    public void testGetGameBoard() throws Exception {
        GameBoard gameBoard = mock(GameBoard.class);
        BoardController boardController = new PlayerBoardController(gameBoard, new Game());
        assertEquals(gameBoard, boardController.getGameBoard());
    }

    @Test
    public void testGetBoardsIndexTiles() throws Exception {
        GameBoard gameBoard = mock(GameBoard.class);
        Map<Integer, BoardTile> expectedMap = new HashMap<>();
        expectedMap.put(1, new BoardTile(1));
        expectedMap.put(2, new BoardTile(2));
        expectedMap.put(3, new BoardTile(3));
        when(gameBoard.getIndexTiles()).thenReturn(expectedMap);
        BoardController boardController = new PlayerBoardController(gameBoard, new Game());
        assertEquals(expectedMap, boardController.getBoardsIndexTiles());
    }

    @Test
    public void testGetGame() throws Exception {
        GameBoard gameBoard = mock(GameBoard.class);
        Game game = mock(Game.class);
        BoardController boardController = new PlayerBoardController(gameBoard, game);
        assertEquals(game, boardController.getGame());
    }

    @Test
    public void testSetGame() throws Exception {
        GameBoard gameBoard = mock(GameBoard.class);
        Game game = mock(Game.class);
        Game expectedGame = new Game();
        BoardController boardController = new PlayerBoardController(gameBoard, game);
        boardController.setGame(expectedGame);
        assertEquals(expectedGame, boardController.getGame());
    }

    @Test
    public void testColorBoardTile() throws Exception {
        GridPane gridPane = mock(GridPane.class);
        GameBoard gameBoard = new GameBoard(gridPane);
        BoardTile boardTile1 = new BoardTile(1);
        BoardTile boardTile2 = new BoardTile(2);
        String style1 = Styles.SHIP_PLACED_COLOR;
        String style2 = Styles.RESPONSE_DESTROYED;
        gameBoard.getIndexTiles().put(1, boardTile1);
        gameBoard.getIndexTiles().put(2, boardTile2);
        BoardController boardController = new PlayerBoardController(gameBoard, new Game());
        boardController.colorBoardTile(1, style1);
        boardController.colorBoardTile(2, style2);
        assertTrue(boardTile1.getStyle().contains(style1));
        assertTrue(boardTile2.getStyle().contains(style2));

    }

}