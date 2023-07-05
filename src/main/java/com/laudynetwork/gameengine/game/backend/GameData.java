package com.laudynetwork.gameengine.game.backend;

import com.google.gson.Gson;
import com.laudynetwork.gameengine.game.GameType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

@RequiredArgsConstructor
@Getter
public class GameData extends Document {

    private final GameType type;
    private final int minSlots;
    private final int maxSlots;

    public static GameData fromJson(String json) {
        return new Gson().fromJson(json, GameData.class);
    }

    public Document toDocument() {
        return new Document("type", type.id())
                .append("minSlots", minSlots)
                .append("maxSlots", maxSlots);
    }

}
