package com.emilywu.reversi.board;

import com.emilywu.reversi.game.Game;
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
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name="game_id")
    private Game game;

    @Column(name = "board", nullable = false)
    private List<String> board;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
