package com.emilywu.reversi.board;

import com.emilywu.reversi.tile.Tile;
import com.emilywu.reversi.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {
    //Board
    //constructor correctly set the tiles to board
    //isTherePiecesToBeFlipped
    //1. correctly return true if there are pieces to be flipped
    //2. correctly return false if there are no pieces to be flipped
    //3. correctly return false if the new tile is out of board
    //4.


    private Board board;


    @BeforeEach
    void setUp() {
        // Setup initial board with standard Reversi starting position
        List<Tile> initialTiles = new ArrayList<>();
        initialTiles.add(new Tile(3, 3, TileColor.WHITE));
        initialTiles.add(new Tile(3, 4, TileColor.BLACK));
        initialTiles.add(new Tile(4, 3, TileColor.BLACK));
        initialTiles.add(new Tile(4, 4, TileColor.WHITE));
        board = new Board(initialTiles);
    }


    @Test
    void testBoardInitialization() {
        // Check board size
        assertEquals(8, board.getBoard().size());
        assertEquals(8, board.getBoard().get(0).size());

        // Check initial pieces placement
        assertEquals(TileColor.WHITE, board.getBoard().get(3).get(3).getColor());
        assertEquals(TileColor.BLACK, board.getBoard().get(3).get(4).getColor());
        assertEquals(TileColor.BLACK, board.getBoard().get(4).get(3).getColor());
        assertEquals(TileColor.WHITE, board.getBoard().get(4).get(4).getColor());

        // Check empty spaces
        assertNull(board.getBoard().get(0).get(0));
    }

    @Test
    void testIsTherePiecesToBeFlipped() {
        // Valid move that can flip pieces
        Tile validMove = new Tile(3, 5, TileColor.WHITE);
        assertTrue(board.isTherePiecesToBeFlipped(validMove));

        // Invalid move that cannot flip pieces
        Tile invalidMove = new Tile(0, 0, TileColor.BLACK);
        assertFalse(board.isTherePiecesToBeFlipped(invalidMove));
    }

    @Test
    void testIsGameOver() {
        // Initial board is not game over
        assertFalse(board.isGameOver());

        // Fill the board completely
        List<Tile> fullBoardTiles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fullBoardTiles.add(new Tile(i, j, TileColor.BLACK));
            }
        }
        Board fullBoard = new Board(fullBoardTiles);
        assertTrue(fullBoard.isGameOver());
    }

    @Test
    void testWhoIsWinner() {
        // Create a board with more black pieces
        List<Tile> allBlackTiles = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                allBlackTiles.add(new Tile(i, j, TileColor.BLACK));
            }
        }

        Board boardWithMoreBlack = new Board(allBlackTiles);

        Map<String, Object> result = boardWithMoreBlack.whoIsWinner();
        assertEquals("BLACK", result.get("winner"));
        assertEquals(64, result.get("count"));

        // Test tie scenario

        List<Tile> tieTiles = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    tieTiles.add(new Tile(i, j, TileColor.WHITE));
                } else {
                    tieTiles.add(new Tile(i, j, TileColor.BLACK));
                }
            }
        }

        Board tieBoard = new Board(tieTiles);

        Map<String, Object> tieResult = tieBoard.whoIsWinner();
        assertEquals("TIE", tieResult.get("winner"));
        assertEquals(0, tieResult.get("count"));
    }

    @Test
    void testPossibleMoves() {
        // Test possible moves for black pieces in initial position
        List<List<Integer>> blackMoves = board.possibleMoves(TileColor.BLACK);

        // In standard Reversi, black has these valid moves in initial position
        List<List<Integer>> expectedMoves = Arrays.asList(
                Arrays.asList(2, 3),
                Arrays.asList(3, 2),
                Arrays.asList(4, 5),
                Arrays.asList(5, 4)
        );

        assertEquals(expectedMoves.size(), blackMoves.size());
        assertTrue(blackMoves.containsAll(expectedMoves));
    }

    @Test
    void testParseBoard() {
        List<List<String>> parsedBoard = board.parseBoard();

        // Check dimensions
        assertEquals(8, parsedBoard.size());
        assertEquals(8, parsedBoard.get(0).size());

        // Check initial pieces
        assertEquals("WHITE", parsedBoard.get(3).get(3));
        assertEquals("BLACK", parsedBoard.get(3).get(4));
        assertEquals("BLACK", parsedBoard.get(4).get(3));
        assertEquals("WHITE", parsedBoard.get(4).get(4));

        // Check empty spaces
        assertEquals("", parsedBoard.get(0).get(0));
    }

    @Test
    void testOutOfBoardScenarios() {
        Tile outOfBoundsTile = new Tile(8, 8, TileColor.BLACK);
        assertFalse(board.isTherePiecesToBeFlipped(outOfBoundsTile));

        Tile negativePositionTile = new Tile(-1, -1, TileColor.BLACK);
        assertFalse(board.isTherePiecesToBeFlipped(negativePositionTile));
    }
}
