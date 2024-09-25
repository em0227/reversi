package com.emilywu.reversi.setup;

import com.emilywu.reversi.game.Game;
import com.emilywu.reversi.game.GameRepository;
import com.emilywu.reversi.game.GameState;
import com.emilywu.reversi.player.Player;
import com.emilywu.reversi.player.PlayerRepository;
import com.emilywu.reversi.tile.Tile;
import com.emilywu.reversi.tile.TileColor;
import com.emilywu.reversi.tile.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private TileRepository tileRepository;

    @Autowired
    public DataLoader(GameRepository gameRepository, PlayerRepository playerRepository, TileRepository tileRepository) {

        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.tileRepository = tileRepository;
    }

    public void run(ApplicationArguments args) {
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
        newGame.setCurrentPlayerId(player1.id);

        gameRepository.save(newGame);

        Tile tile1 = new Tile(3, 3, TileColor.BLACK);
        Tile tile2 = new Tile(3, 4, TileColor.WHITE);
        Tile tile3 = new Tile(4, 3, TileColor.BLACK);
        Tile tile4 = new Tile(4, 4, TileColor.WHITE);

        tile1.setGame(newGame);
        tile2.setGame(newGame);
        tile3.setGame(newGame);
        tile4.setGame(newGame);

        tileRepository.save(tile1);
        tileRepository.save(tile2);
        tileRepository.save(tile3);
        tileRepository.save(tile4);
    }
}