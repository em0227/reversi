package com.emilywu.reversi.player;

import com.emilywu.reversi.game.Game;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    //TODO make this nullable false
    @Column(name = "email", unique = true, nullable = true)
    public String email;

    @Column(name="google_id", unique = true)
    public String googleId;

    @JsonManagedReference
    @OneToMany(mappedBy = "blackPlayer")
    private List<Game> gamesAsBlack;

    @JsonManagedReference
    @OneToMany(mappedBy = "whitePlayer")
    private List<Game> gamesAsWhite;

    @Formula(value = "(SELECT COUNT(*) FROM game g WHERE g.winner_id=id)")
    private long wins;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
