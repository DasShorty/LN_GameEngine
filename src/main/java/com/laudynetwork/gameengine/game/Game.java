package com.laudynetwork.gameengine.game;

import com.laudynetwork.gameengine.game.backend.GameDataHandler;
import com.laudynetwork.gameengine.game.gamestate.GameState;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public abstract class Game {

    protected final GameType type;
    protected final int maxPlayers;
    protected final int minPlayers;
    private final GameDataHandler dataHandler;

    public Game(GameType type, int maxPlayers, int minPlayers) {
        this.type = type;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.dataHandler = Bukkit.getServicesManager().getRegistration(GameDataHandler.class).getProvider();
        saveGame();
    }

    private void saveGame() {
        this.dataHandler.createData(type.id(), minPlayers, maxPlayers);
    }

    public abstract void onGameStateChange(GameState newState, GameState oldState);

}
