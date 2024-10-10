package com.emilywu.reversi.game.dto;

import com.emilywu.reversi.game.Game;
import com.emilywu.reversi.game.GameState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GameBoardDto {
    private UUID id;
    private GameState state;
    private UUID winnerId;
    private UUID currentPlayerId;
    private PlayerDto blackPlayer;
    private PlayerDto whitePlayer;
    public List<List<String>> board;
    private int winByHowMany;
    private List<List<Integer>> possibleMoves;
    private String errorMessage;

    public GameBoardDto(Game game) {
        this.id = game.getId();
        this.state = game.getState();
        this.winnerId = game.getWinnerId();
        this.currentPlayerId = game.getCurrentPlayerId();
        this.blackPlayer = new PlayerDto(game.getBlackPlayer().getId(), game.getBlackPlayer().getName());
        this.whitePlayer = new PlayerDto(game.getWhitePlayer().getId(), game.getWhitePlayer().getName());
        this.winByHowMany = game.getWinByHowMany();
    }

    public GameBoardDto(String message) {
        this.errorMessage = message;
    }
}
