package com.emilywu.reversi.game;

import com.emilywu.reversi.board.Board;
import com.emilywu.reversi.player.Player;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Game {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name= "black_player_id", nullable = false)
    private Player blackPlayerId;

    @ManyToOne
    @JoinColumn(name= "white_player_id", nullable = false)
    private Player whitePlayerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="board_id")
    private Board board;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
