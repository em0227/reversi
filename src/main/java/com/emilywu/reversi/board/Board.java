package com.emilywu.reversi.board;

import com.emilywu.reversi.game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {

//    private Game game;

    private ArrayList<List<String>> board;
}
