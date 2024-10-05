package com.emilywu.reversi.game;

import com.emilywu.reversi.board.Board;
import com.emilywu.reversi.game.dto.GameBoardDto;
import com.emilywu.reversi.game.dto.UpdateGameRequestDto;
import com.emilywu.reversi.player.Player;
import com.emilywu.reversi.player.PlayerRepository;
import com.emilywu.reversi.tile.Tile;
import com.emilywu.reversi.tile.TileColor;
import com.emilywu.reversi.tile.TileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TileRepository tileRepository;
    @Autowired
    private PlayerRepository playerRepository;

    //TODO: maybe refactor this into manager class?
    //TODO: extract the dto part into utils?

    public GameBoardDto findGameById(UUID id) throws IOException {
        Game game = gameRepository.findById(id).orElseThrow(() -> new IOException("not found"));
        GameBoardDto result = new GameBoardDto(game);
        Board board = new Board(game.getTiles());
        result.setBoard(board.parseBoard());
        result.setPossibleMoves(board.possibleMoves(TileColor.BLACK));
        return result;
    }


    //this update is only updating a NEW or ACTIVE game when player put a new pawn
    //TODO: refactor error to throw 400 error
    public GameBoardDto updateGameById(UUID id, UpdateGameRequestDto request) throws IOException {
        Game game = gameRepository.findById(id).orElseThrow(() -> new IOException("game not found"));
        if (game.getState() == GameState.COMPLETE) throw new IOException("this game is complete");
        if (game.getState() == GameState.PENDING) throw new IOException("this game has not started");
        if (game.getState() == GameState.NEW) game.setState(GameState.ACTIVE);
        //TODO: validate pos FE & BE
        Tile newTile = new Tile(Integer.parseInt(request.getRow()), Integer.parseInt(request.getCol()), request.getColor().equals("BLACK") ? TileColor.BLACK : TileColor.WHITE);
        newTile.setGame(game);
        tileRepository.save(newTile);
        Board board = new Board(game.getTiles());
        board.isTherePiecesToBeFlipped(newTile);

        if (board.isGameOver()) {
            Map<String, Object> winningResult = board.whoIsWinner();
            game.setState(GameState.COMPLETE);
            game.setWinByHowMany((int) winningResult.get("count"));
            if (winningResult.get("winner").equals("BLACK")) {
                game.setWinnerId(game.getBlackPlayer().id);
            } else if (winningResult.get("winner").equals("WHITE")) {
                game.setWinnerId(game.getWhitePlayer().id);
            } else {
                game.setState(GameState.TIE);
            }
        } else {
            if (game.getBlackPlayer().id.equals(request.getPlayer())) {
                game.setCurrentPlayerId(game.getWhitePlayer().id);
            } else {
                game.setCurrentPlayerId(game.getBlackPlayer().id);
            }
        }

        gameRepository.save(game);
        GameBoardDto result = new GameBoardDto(game);
        result.setPossibleMoves(board.possibleMoves(request.getColor().equals("BLACK") ? TileColor.WHITE : TileColor.BLACK));
        result.setBoard(board.parseBoard());

        return result;
    }

    public UUID createGame(UUID player1, UUID player2) throws IOException {
        //find players to ensure player exists
        Player blackPlayer = playerRepository.findById(player1).orElseThrow(() -> new IOException("player1 not found"));
        Player whitePlayer = playerRepository.findById(player2).orElseThrow(() -> new IOException("player2 not found"));;;
        Game newGame = new Game();
        newGame.setState(GameState.NEW);
        newGame.setBlackPlayer(blackPlayer);
        newGame.setWhitePlayer(whitePlayer);
        newGame.setCurrentPlayerId(blackPlayer.id);

        gameRepository.save(newGame);

        Tile tile1 = new Tile(3, 3, TileColor.BLACK);
        Tile tile2 = new Tile(3, 4, TileColor.WHITE);
        Tile tile3 = new Tile(4, 3, TileColor.WHITE);
        Tile tile4 = new Tile(4, 4, TileColor.BLACK);

        tile1.setGame(newGame);
        tile2.setGame(newGame);
        tile3.setGame(newGame);
        tile4.setGame(newGame);

        tileRepository.save(tile1);
        tileRepository.save(tile2);
        tileRepository.save(tile3);
        tileRepository.save(tile4);

        return newGame.getId();
    }
}
