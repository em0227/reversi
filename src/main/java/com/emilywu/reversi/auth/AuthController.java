package com.emilywu.reversi.auth;

import com.emilywu.reversi.game.Game;
import com.emilywu.reversi.game.GameRepository;
import com.emilywu.reversi.player.Player;
import com.emilywu.reversi.player.PlayerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PlayerRepository playerRepository; // Create this repository for user data

    @Autowired
    private GameRepository gameRepository; // For retrieving game boards

    @PostMapping("/google-signin")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<?> handleGoogleSignIn(@RequestBody GoogleSignInRequest request,
                                                HttpServletRequest httpRequest,
                                                HttpServletResponse httpResponse) {
        // Validate the Google token (optional but recommended)
        // You can use Google's tokeninfo endpoint or libraries like Google API Client

        // Find or create user
        Player player = playerRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    Player newUser = new Player();
                    newUser.setEmail(request.getEmail());
                    newUser.setName(request.getName());
                    newUser.setGoogleId(request.getSub());
                    return playerRepository.save(newUser);
                });

        // Create a session for the user
        httpRequest.getSession(true).setAttribute("USER_ID", player.getId());

        // Get user's active games
        List<Game> activeGames = gameRepository.findByCurrentPlayerId(player.getId());

        // Return user data and games
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("user", player);
        responseData.put("games", activeGames);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER_ID") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated", false));
        }

        UUID playerId = (UUID) session.getAttribute("USER_ID");
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(player);
    }
}