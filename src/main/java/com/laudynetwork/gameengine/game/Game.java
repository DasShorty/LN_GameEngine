package com.laudynetwork.gameengine.game;

import com.laudynetwork.gameengine.game.backend.GameDataHandler;
import com.laudynetwork.gameengine.game.gamestate.GameState;
import com.laudynetwork.gameengine.game.phase.GamePhase;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Game {

    protected final GameType type;
    protected final int maxPlayers;
    protected final int minPlayers;
    protected final GameTeamHandler gameTeamHandler;
    private final GameDataHandler dataHandler;
    private final Map<GameState, GamePhase> gamePhases = new HashMap<>();
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

    private void saveGame() {
        this.dataHandler.createData(type.id(), minPlayers, maxPlayers);
    }

    public boolean phaseRequirement(GameState state) {
        if (!this.gamePhases.containsKey(state))
            return false;

        return this.gamePhases.get(state).requirement();
    }

    protected void registerPhase(GamePhase phase) {
        this.gamePhases.put(phase.state(), phase);
    }

    public void loadPhase(GameState state) {
        if (!this.gamePhases.containsKey(state))
            return;

        if (state != GameState.WAITING)
            if (this.gamePhases.containsKey(state))
                this.gamePhases.get(GameState.before(state)).onStop();

        val gamePhase = this.gamePhases.get(state);
        if (gamePhase.requirement())
            gamePhase.onStart();
    }

    public abstract void onGameStateChange(GameState newState, GameState oldState);

    public abstract boolean onLoad();

    public abstract boolean onStop();


}
