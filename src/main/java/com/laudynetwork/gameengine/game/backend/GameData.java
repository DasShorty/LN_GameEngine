package com.laudynetwork.gameengine.game.backend;

import com.google.gson.Gson;
import com.laudynetwork.gameengine.game.GameType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

@RequiredArgsConstructor
@Getter
public class GameData extends Document {


    private final GameType type;
    private final int minSlots;
    private final int maxSlots;
}
