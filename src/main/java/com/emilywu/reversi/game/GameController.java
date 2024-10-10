package com.emilywu.reversi.game;

import com.emilywu.reversi.game.dto.CreateGameRequestDto;
import com.emilywu.reversi.game.dto.GameBoardDto;
import com.emilywu.reversi.game.dto.UpdateGameRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public UUID createGame (@RequestBody CreateGameRequestDto request) throws IOException {
        return gameService.createGame(request.getPlayer1(), request.getPlayer2());
    }

    @PutMapping("/{game_id}")
    public ResponseEntity<GameBoardDto> updateGame (@PathVariable UUID game_id, @RequestBody UpdateGameRequestDto request) throws IOException {
        System.out.println("hit put");
        return gameService.updateGameById(game_id, request);
    }
}
