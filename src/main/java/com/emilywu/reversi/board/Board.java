package com.emilywu.reversi.board;

import com.emilywu.reversi.game.Game;
import com.emilywu.reversi.tile.Tile;
import com.emilywu.reversi.tile.TileColor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Board {

    public List<List<Tile>> board = new ArrayList<>();
    private List<Tile> tiles;

    public Board (List<Tile> tiles) {
        this.tiles = tiles;
        for (int i = 0; i < 8; i++) {
            board.add(new ArrayList<Tile>(Collections.nCopies(8, null)));
        }

        for (Tile current : tiles) {
            int row = current.getRowIndex();
            int col = current.getColumnIndex();
            board.get(row).set(col, current);
        }
    }

    public void isTherePiecesToBeFlipped (Tile newTile) {
        int newTileRow = newTile.getRowIndex();
        int newTileCol = newTile.getColumnIndex();
        TileColor newTileColor = newTile.getColor();
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0, -1}, {1,1}, {-1,-1}, {1,-1}, {-1, 1} };

        for (int[] direction : directions) {
            int rowDir = direction[0];
            int colDir = direction[1];
            if (outOfBoard(newTileRow + rowDir, newTileCol + colDir)) continue;
            if (board.get(newTileRow + rowDir).get(newTileCol + colDir) != null && !board.get(newTileRow + rowDir).get(newTileCol + colDir).getColor().equals(newTileColor)) {
                flipPieces(newTileRow + rowDir, newTileCol + colDir, rowDir, colDir, newTileColor);
            }
        }
    }

    private boolean flipPieces (int row, int col, int rowDir, int colDir, TileColor color) {
        if (outOfBoard(row, col) || board.get(row).get(col) == null) return false;
        if (board.get(row).get(col).getColor().equals(color)) return true;

        boolean result = flipPieces(row + rowDir, col +colDir, rowDir, colDir, color);
        if (result) {
            Tile tile = board.get(row).get(col);
            tile.setColor(color);
        }
        return result;
    }

    private boolean outOfBoard (int row, int col) {
        return row < 0 || row >= 8 || col < 0 || col >= 8;
    }

    public boolean isGameOver () {
        for (List<Tile> tileList : board) {
            for (Tile tile : tileList) {
                if (tile == null) return false;
            }
        }
        return true;
    }

    public List<List<String>> parseBoard () {
        List<List<String>> parsedBoard = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            parsedBoard.add(new ArrayList<>(Collections.nCopies(8, "")));
        }

        for (Tile current : this.tiles) {
            int row = current.getRowIndex();
            int col = current.getColumnIndex();
            String color = current.getColor().toString();
            parsedBoard.get(row).set(col, color);
        }

        return parsedBoard;
    }
}
