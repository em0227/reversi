package com.emilywu.reversi.tile;

import com.emilywu.reversi.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TileRepository extends JpaRepository<Tile, java.util.UUID>{
}
