package com.emilywu.reversi.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateGameRequestDto {
    private String row;
    private String col;
    private String color;
    private UUID player;
}
