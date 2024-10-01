package com.emilywu.reversi.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateGameDto {
    private UUID player1;
    private UUID player2;
}
