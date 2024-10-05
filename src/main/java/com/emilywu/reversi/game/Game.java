package com.emilywu.reversi.game;

import com.emilywu.reversi.board.Board;
import com.emilywu.reversi.player.Player;
import com.emilywu.reversi.tile.Tile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Game {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private GameState state;

    @Column(name = "winner_id")
    private UUID winnerId;

    @Column(name = "current_player_id", nullable = false)
    private UUID currentPlayerId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "black_player_id")
    private Player blackPlayer;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "white_player_id")
    private Player whitePlayer;

    @Column(name = "wind_by_how_many")
    private int winByHowMany = 0;

    @OneToMany(mappedBy = "game")
    public List<Tile> tiles;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
