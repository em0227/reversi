package com.emilywu.reversi.board;

import com.emilywu.reversi.tile.Tile;
import com.emilywu.reversi.tile.TileColor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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

    public boolean isTherePiecesToBeFlipped (Tile newTile) {
        boolean isValidMove = false;
        int newTileRow = newTile.getRowIndex();
        int newTileCol = newTile.getColumnIndex();
        TileColor newTileColor = newTile.getColor();
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0, -1}, {1,1}, {-1,-1}, {1,-1}, {-1, 1} };

        for (int[] direction : directions) {
            int rowDir = direction[0];
            int colDir = direction[1];
            if (outOfBoard(newTileRow + rowDir, newTileCol + colDir)) continue;
            if (board.get(newTileRow + rowDir).get(newTileCol + colDir) != null && !board.get(newTileRow + rowDir).get(newTileCol + colDir).getColor().equals(newTileColor)) {
                boolean result = flipPieces(newTileRow + rowDir, newTileCol + colDir, rowDir, colDir, newTileColor);
                if (result) isValidMove = true;
            }
        }

        return isValidMove;
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

    public Map<String, Object> whoIsWinner () {
        Map<String, Object> result = new HashMap<String, Object>();
        int blackCount = 0;
        int whiteCount = 0;
        for (List<Tile> tileList : board) {
            for (Tile tile : tileList) {
                if (tile.getColor().equals(TileColor.BLACK)) blackCount++;
                if (tile.getColor().equals(TileColor.WHITE)) whiteCount++;
            }
        }
        if (blackCount > whiteCount) {
            result.put("winner", "BLACK");
            result.put("count", blackCount - whiteCount);
        } else if (blackCount < whiteCount) {
            result.put("winner", "WHITE");
            result.put("count", whiteCount - blackCount);
        } else {
            result.put("winner", "TIE");
            result.put("count", 0);
        }
        return result;
    }

    public List<List<Integer>> possibleMoves (TileColor color) {
        //go thr each dir around current tile and check if there is empty tile
        //if there is, see if the opposite direction exist a peice with current's opposite color
        //if there is, add to the current's opposite color possible moves
        List<List<Integer>> possibleMoves = new ArrayList<>();

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0, -1}, {1,1}, {-1,-1}, {1,-1}, {-1, 1} };
        Map<List<Integer>, List<Integer>> oppositeDirMap = new HashMap<>();
        oppositeDirMap.put(Arrays.asList(1,0), Arrays.asList(-1,0));
        oppositeDirMap.put(Arrays.asList(-1,0), Arrays.asList(1,0));
        oppositeDirMap.put(Arrays.asList(0,1), Arrays.asList(0,-1));
        oppositeDirMap.put(Arrays.asList(0,-1), Arrays.asList(0,1));
        oppositeDirMap.put(Arrays.asList(1,1), Arrays.asList(-1,-1));
        oppositeDirMap.put(Arrays.asList(-1,-1), Arrays.asList(1,1));
        oppositeDirMap.put(Arrays.asList(1,-1), Arrays.asList(-1,1));
        oppositeDirMap.put(Arrays.asList(-1,1), Arrays.asList(1,-1));

        for (int i = 0; i < this.tiles.size(); i++) {
            Tile current = this.tiles.get(i);
            if (!current.getColor().equals(color)) {
                int row = current.getRowIndex();
                int col = current.getColumnIndex();
                List<List<Integer>> emptySpotsAndDir = new ArrayList<>();
                for (int[] direction : directions) {
                    int rowDir = direction[0];
                    int colDir = direction[1];
                    if (outOfBoard(row + rowDir, col + colDir)) continue;
                    if (board.get(row + rowDir).get(col + colDir) == null) {
                        emptySpotsAndDir.add(Arrays.asList(row + rowDir, col + colDir, rowDir, colDir));
                    }
                }

                for (List<Integer> emptySpot : emptySpotsAndDir) {
                    List<Integer> oppositeDir = oppositeDirMap.get(Arrays.asList(emptySpot.get(2), emptySpot.get(3)));
                    int rowDir = oppositeDir.get(0);
                    int colDir = oppositeDir.get(1);
                    int currentRow = emptySpot.get(0) + 2* rowDir;
                    int currentCol = emptySpot.get(1) + 2* colDir;
                    while (!outOfBoard(currentRow, currentCol) && board.get(currentRow).get(currentCol) != null) {
                        if (board.get(currentRow).get(currentCol).getColor().equals(color)) {
                            possibleMoves.add(Arrays.asList(emptySpot.get(0), emptySpot.get(1)));
                            break;
                        }
                        currentRow += rowDir;
                        currentCol += colDir;
                    }
                }

            }
        }

        return possibleMoves;
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
