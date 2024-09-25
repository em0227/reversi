package com.emilywu.reversi.game;

import com.emilywu.reversi.game.dto.GameBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
