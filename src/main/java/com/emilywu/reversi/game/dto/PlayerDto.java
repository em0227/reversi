package com.emilywu.reversi.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerDto {
    private UUID id;
    private String name;

    public PlayerDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
