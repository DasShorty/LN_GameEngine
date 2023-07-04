package com.laudynetwork.gameengine.game.gamestate;

import com.laudynetwork.networkutils.api.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameStateHandler {

    private final Map<GameState, ArrayList<Pair<?, Consumer<?>>>> onGameStateChange = new HashMap<>();

    public <T> void onChangeGameStatePerformer(GameState state, T instance, Consumer<T> onChange) {
        if (this.onGameStateChange.containsKey(state)) {
            this.onGameStateChange.put(state, new ArrayList<>(List.of(new Pair<>(instance, onChange))));
        }
    }
}
