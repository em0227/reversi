package com.emilywu.reversi.game;

import com.emilywu.reversi.game.dto.CreateGameRequestDto;
import com.emilywu.reversi.game.dto.GameBoardDto;
import com.emilywu.reversi.game.dto.UpdateGameRequestDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", principal.getAttribute("name"));
        userDetails.put("email", principal.getAttribute("email"));
        // Add other attributes you need
        return userDetails;
    }

    @GetMapping("/authenticated/{game_id}")
    public ResponseEntity<GameBoardDto> isAuthenticated(@PathVariable UUID game_id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return gameService.findGameById(game_id);
        } else {
            throw new ServletException("User is not authenticated");
        }
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
