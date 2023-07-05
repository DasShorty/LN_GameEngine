package com.laudynetwork.gameengine.command;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.gameengine.game.backend.GameDataHandler;
import com.laudynetwork.networkutils.api.messanger.api.MessageAPI;
import com.laudynetwork.networkutils.api.player.NetworkPlayer;
import com.laudynetwork.networkutils.essentials.control.api.ControlSubCommand;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class GameCommand implements ControlSubCommand {

    private final MessageAPI msgApi = new MessageAPI(GameEngine.getINSTANCE().getMsgCache(), MessageAPI.PrefixType.SYSTEM);
    private final GameDataHandler handler;

    @Override
    public String id() {
        return "game-engine";
    }

    @Override
    public void onCommand(Player player, Command command, String s, String[] args, NetworkPlayer networkPlayer) {

        val language = networkPlayer.getLanguage();

        if (!player.hasPermission("game.game-engine.control")) {
            player.sendMessage(this.msgApi.getMessage(language, "command.only.player"));
            return;
        }

        switch (args[1].toLowerCase()) {

            // control game-engine

            case "create" -> {
                // control game-engine create <game> <min> <max>

                if (args.length == 4) {
                    player.sendMessage(this.msgApi.getMessage(language, "command.usage",
                            Placeholder.unparsed("command", "/control game-engine create <game> <min> <max>")));
                    return;
                }

                val gameId = args[2];
                val minPlayers = toInt(args[3], unused -> player.sendMessage(this.msgApi.getMessage(language, "object.format.int",
                        Placeholder.unparsed("usage", "min"),
                        Placeholder.unparsed("fallback", "1"))));
                val maxPlayers = toInt(args[4], unused -> player.sendMessage(this.msgApi.getMessage(language, "object.format.int",
                        Placeholder.unparsed("usage", "max"),
                        Placeholder.unparsed("fallback", "1"))));

                val data = this.handler.createData(gameId, minPlayers, maxPlayers);

                if (data == null) {
                    player.sendMessage(this.msgApi.getMessage(language, "command.control.game-engine.exist", Placeholder.unparsed("id", gameId)));
                    return;
                }

                player.sendMessage(this.msgApi.getMessage(language, "command.control.game-engine.created", Placeholder.unparsed("id", gameId)));

            }
        }

    }

    private int toInt(String toInt, Consumer<Void> onFailure) {

        try {
            return Integer.parseInt(toInt);
        } catch (NumberFormatException ignore) {
            onFailure.accept(null);
            return 1;
        }
    }

    @Override
    public List<String> onTabComplete(Player player, Command command, String s, String[] args, NetworkPlayer networkPlayer) {

        val list = new ArrayList<String>();

        switch (args.length) {
            case 2 -> {
                list.add("create");
            }
            case 3 -> {
                switch (args[1].toLowerCase()) {
                    case "create" -> {
                        list.add("<gameId>");
                    }
                }
            }
            case 4 -> {
                switch (args[1].toLowerCase()) {
                    case "create" -> {
                        list.add("<min-player-slots>");
                    }
                }
            }
            case 5 -> {
                switch (args[1].toLowerCase()) {
                    case "create" -> {
                        list.add("<max-player-slots>");
                    }
                }
            }
            default -> list.add("(no data)");
        }

        return list;
    }
}
