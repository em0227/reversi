package com.emilywu.reversi.game;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public Game findGameById(UUID id) throws IOException {
        //**will this return board too?
        //**how would player be saved?
        return gameRepository.findById(id).orElseThrow(() -> new IOException("not found"));
    }


    //should I differentiate update when change from PENDING -> NEW (no pos input)?
    public Game updateGameById(UUID id, List<Integer> pos) throws IOException {
        Game game = findGameById(id);
        //check first if game state is NOT COMPLETE
        //**if board does not come with game
        //Board board = boardRepository.findById(game.board);
        //board.updateBoard: 1. put piece on pos on board 2. check if new piece causes any flipping (helper methods)
        //isGameOver: see if there's any more empty space on board (board.isAnyEmptySpace?)
        //if game is over, update game state, set winner => FE will show proper message
        //if not over, switch currentPlayer
        //save game and return game
        gameRepository.save(game);
        return game;
    }
}
