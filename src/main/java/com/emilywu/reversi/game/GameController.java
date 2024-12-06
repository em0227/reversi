package com.emilywu.reversi.game;

import com.emilywu.reversi.game.dto.CreateGameRequestDto;
import com.emilywu.reversi.game.dto.GameBoardDto;
import com.emilywu.reversi.game.dto.UpdateGameRequestDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public Map<String, Object> currentPlayer(OAuth2AuthenticationToken token) {
        return token.getPrincipal().getAttributes();
        //String = token.getPrincipal().getAttributes().get("email");
    }


    @GetMapping("/{game_id}")
    public ResponseEntity<GameBoardDto> getGame (@PathVariable UUID game_id) throws IOException {
        return gameService.findGameById(game_id);
    }

    @PostMapping
    public ResponseEntity<UUID> createGame (@RequestBody CreateGameRequestDto request) throws IOException {
        return gameService.createGame(request.getPlayer1(), request.getPlayer2());
    }

    @PutMapping("/{game_id}")
    public ResponseEntity<GameBoardDto> updateGame (@PathVariable UUID game_id, @RequestBody UpdateGameRequestDto request) throws IOException {
        System.out.println("hit put");
        return gameService.updateGameById(game_id, request);
    }
}
