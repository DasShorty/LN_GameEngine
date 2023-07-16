package com.laudynetwork.gameengine.game.gamestate;

public enum GameState {

    WAITING, // waiting for players/server
    STARTING,
    RUNNING, // game is running
    ENDING; // game ends

    public static GameState before(GameState current) {

        switch (current) {
            case STARTING, WAITING -> {
                return WAITING;
            }
            case RUNNING -> {
                return STARTING;
            }
            case ENDING -> {
                return RUNNING;
            }
        }

        return WAITING;
    }

}
