package com.laudynetwork.gameengine.game;

public record GameType(String id) {

    @Override
    public String toString() {
        return id;
    }
}
