package com.emilywu.reversi.tile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "row_index", updatable = false, nullable = false)
    private int rowIndex;

    @Column(name = "column_index", updatable = false, nullable = false)
    private int columnIndex;

    @Column(name = "game_index", updatable = false, nullable = false)
    private int gameId;

    @Column(name = "color")
    private int color;

    @Column(name = "created_at")
    private LocalTime createdAt = now();

    @Column(name = "updated_at")
    private LocalTime updatedAt;

//    public Tile (int rowIndex, int columnIndex) {
//         this.rowIndex = rowIndex;
//         this.columnIndex = columnIndex;
//    }
}
