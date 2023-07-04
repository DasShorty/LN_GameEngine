package com.laudynetwork.gameengine.game;

import lombok.Getter;

@Getter
public abstract class Game {
    private final GameType type;
    private final int maxPlayers;
    private final int minPlayers;

    public Game(GameType type, int maxPlayers, int minPlayers) {
        this.type = type;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
    }
}
