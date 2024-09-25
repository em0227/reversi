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

    public List<List<String>> board = new ArrayList<>();

    public Board (List<Tile> tiles) {

        for (int i = 0; i < 8; i++) {
            board.add(new ArrayList<String>(Collections.nCopies(8, "")));
        }

        for (int i = 0; i < tiles.size(); i++) {
            Tile current = tiles.get(i);
            int row = current.getRowIndex();
            int col = current.getColumnIndex();
            TileColor color = current.getColor();
            board.get(row).set(col, color.toString());
        }
    }
}
