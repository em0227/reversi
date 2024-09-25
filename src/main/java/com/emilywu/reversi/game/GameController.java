package com.emilywu.reversi.game;

import com.emilywu.reversi.game.dto.GameBoardDto;
import com.emilywu.reversi.game.dto.updateGameRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/{game_id}")
    public GameBoardDto getGame (@PathVariable UUID game_id) throws IOException {
        return gameService.findGameById(game_id);
    }

    @PostMapping
    public GameBoardDto createGame (@RequestBody UUID player1, UUID player2) throws IOException {
        return gameService.createGame(player1, player2);
    }

    @PutMapping("/{game_id}")
    public GameBoardDto updateGame (@PathVariable UUID game_id, @RequestBody updateGameRequestDto request) throws IOException {
        System.out.println("hit put");
        return gameService.updateGameById(game_id, request);
    }
}
