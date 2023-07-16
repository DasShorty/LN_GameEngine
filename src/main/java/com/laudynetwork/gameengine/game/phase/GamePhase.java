package com.laudynetwork.gameengine.game.phase;

import com.laudynetwork.gameengine.game.gamestate.GameState;

public interface GamePhase {

    GameState state();
    void onStart();
    void onStop();
    boolean requirement();

}
