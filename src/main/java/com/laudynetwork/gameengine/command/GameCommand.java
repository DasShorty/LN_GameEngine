package com.laudynetwork.gameengine.command;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.networkutils.api.messanger.api.MessageAPI;
import com.laudynetwork.networkutils.api.player.NetworkPlayer;
import com.laudynetwork.networkutils.essentials.control.api.ControlSubCommand;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class GameCommand implements ControlSubCommand {

    private final MessageAPI msgApi = new MessageAPI(GameEngine.getINSTANCE().getMsgCache(), MessageAPI.PrefixType.SYSTEM);

    @Override
    public String id() {
        return "game-engine";
    }

    @Override
    public void onCommand(Player player, Command command, String s, String[] strings, NetworkPlayer networkPlayer) {

        if (!player.hasPermission("game.game-engine.control")) {

            return;
        }

    }

    @Override
    public List<String> onTabComplete(Player player, Command command, String s, String[] strings, NetworkPlayer networkPlayer) {
        return null;
    }
}
