package com.emilywu.reversi.setup;

import com.emilywu.reversi.board.Board;
import com.emilywu.reversi.board.BoardRepository;
import com.emilywu.reversi.game.Game;
import com.emilywu.reversi.game.GameRepository;
import com.emilywu.reversi.game.GameState;
import com.emilywu.reversi.player.Player;
import com.emilywu.reversi.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private PlayerRepository playerRepository;
    private BoardRepository boardRepository;
    private GameRepository gameRepository;

    @Autowired
    public DataLoader(GameRepository gameRepository, BoardRepository boardRepository, PlayerRepository playerRepository) {

        this.gameRepository = gameRepository;
        this.boardRepository = boardRepository;
        this.playerRepository = playerRepository;
    }

    public void run(ApplicationArguments args) {
        ArrayList<List<String>> board = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            board.add(new ArrayList<String>(Collections.nCopies(8, "")));
        }

        Board gameBoard = new Board();
        gameBoard.setBoard(board);
        boardRepository.save(gameBoard);

        Player player1 = new Player();
        player1.setName("Emily");

        Player player2 = new Player();
        player2.setName("Andrew");

        playerRepository.save(player1);
        playerRepository.save(player2);

        Game newGame = new Game();
        newGame.setState(GameState.NEW);
        newGame.setBlackPlayer(player1);
        newGame.setWhitePlayer(player2);
        newGame.setCurrentPlayer(player1);
        newGame.setBoard(gameBoard);

        gameRepository.save(newGame);
    }
}