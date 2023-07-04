package com.laudynetwork.gameengine.game.backend;

import com.laudynetwork.gameengine.game.GameType;
import com.laudynetwork.networkutils.api.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.val;
import org.bukkit.Bukkit;

public class GameDataHandler {

    private final MongoCollection<GameData> collection;

    public GameDataHandler() {
        val provider = Bukkit.getServicesManager().getRegistration(MongoDatabase.class).getProvider();
        this.collection = provider.getDatabase().getCollection("minecraft_general_games", GameData.class);
    }

    public GameData dataFromDatabase(GameType type) {
        return this.collection.find(Filters.eq("type", type.name())).first();
    }

}
