package com.emilywu.reversi.tile;

import com.emilywu.reversi.game.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

import static java.time.LocalTime.now;

@Getter
@Setter
@Entity
public class Tile {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "row_index", updatable = false, nullable = false)
    private int rowIndex;

    @Column(name = "column_index", updatable = false, nullable = false)
    private int columnIndex;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "color")
    private TileColor color;

    @Column(name = "created_at")
    private LocalTime createdAt = now();

    @Column(name = "updated_at")
    private LocalTime updatedAt;

    //how to ensure row & col combo have to be unique
    public Tile (int rowIndex, int columnIndex, TileColor color) {
         this.rowIndex = rowIndex;
         this.columnIndex = columnIndex;
         this.color = color;
    }

    public Tile() {
    }
}
