package com.laudynetwork.gameengine.game;

import com.laudynetwork.gameengine.game.backend.GameDataHandler;
import com.laudynetwork.gameengine.game.gamestate.GameState;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
public abstract class Game implements Listener {

    protected final GameType type;
    protected final int maxPlayers;
    protected final int minPlayers;
    protected final GameTeamHandler gameTeamHandler;
    private final GameDataHandler dataHandler;
    @Setter
    private GameState currentState;

    public Game(GameType type, int maxPlayers, int minPlayers) {
        this.type = type;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.dataHandler = Bukkit.getServicesManager().getRegistration(GameDataHandler.class).getProvider();
        this.gameTeamHandler = new GameTeamHandler();
        this.currentState = GameState.WAITING;
        saveGame();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        onJoin(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        onQuit(event);
    }

    private void saveGame() {
        this.dataHandler.createData(type.id(), minPlayers, maxPlayers);
    }

    public abstract void onGameStateChange(GameState newState, GameState oldState);

    public abstract boolean onLoad();

    public abstract boolean onStart();

    public abstract boolean onStop();

    public abstract void onJoin(PlayerJoinEvent event);

    public abstract void onQuit(PlayerQuitEvent event);

}
