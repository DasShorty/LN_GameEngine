package com.laudynetwork.gameengine.game.backend;

import com.laudynetwork.gameengine.game.GameType;
import com.laudynetwork.networkutils.api.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import lombok.val;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class GameDataHandler {

    private final MongoCollection<Document> collection;

    public GameDataHandler() {
        val provider = Bukkit.getServicesManager().getRegistration(MongoDatabase.class).getProvider();
        this.collection = provider.getDatabase().getCollection("minecraft_general_games");
    }

    public GameData dataFromDatabase(GameType type) {
        return GameData.fromJson(this.collection.find(Filters.eq("type", type.id())).first().toJson());
    }

    private boolean containsData(GameType type) {
        val filter = Filters.eq("type", type);

        if (this.collection.find(filter).first() == null)
            return false;
        val documents = convertIteratorToList(this.collection.find(filter).iterator());
        return !documents.isEmpty();

    }

    private List<Document> convertIteratorToList(MongoCursor<Document> documents) {
        val list = new ArrayList<Document>();
        while (documents.hasNext()) {
            list.add(documents.next());
        }
        return list;
    }

    public GameData createData(String id, int minSlots, int maxSlots) {
        val type = new GameType(id);
        val data = new GameData(type, minSlots, maxSlots);

        System.out.println(type.id());

        if (this.containsData(type))
            return null;

        this.collection.insertOne(data.toDocument());

        return data;
    }

}
