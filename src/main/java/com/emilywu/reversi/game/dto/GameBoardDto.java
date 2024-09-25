package com.emilywu.reversi.game.dto;

import com.emilywu.reversi.game.Game;
import com.emilywu.reversi.game.GameState;
import com.emilywu.reversi.player.Player;
import com.emilywu.reversi.tile.Tile;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GameBoardDto {
    private UUID id;
    private GameState state;
    private UUID winnerId;
    private UUID currentPlayerId;
    private UUID blackPlayer;
    private UUID whitePlayer;
    public List<List<String>> board;
//    private ZonedDateTime createdAt;
//    private ZonedDateTime updatedAt;

    public GameBoardDto(Game game) {
        this.id = game.getId();
        this.state = game.getState();
        this.winnerId = game.getWinnerId();
        this.currentPlayerId = game.getCurrentPlayerId();
        this.blackPlayer = game.getBlackPlayer().getId();
        this.whitePlayer = game.getWhitePlayer().getId();
//        this.createdAt = game.getCreatedAt();
//        this.updatedAt = game.getUpdatedAt();
    }
}
