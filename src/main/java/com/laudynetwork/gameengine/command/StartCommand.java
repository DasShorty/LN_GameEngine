package com.laudynetwork.gameengine.command;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.gameengine.game.Game;
import com.laudynetwork.gameengine.game.gamestate.GameState;
import com.laudynetwork.networkutils.api.MongoDatabase;
import com.laudynetwork.networkutils.api.messanger.api.MessageAPI;
import com.laudynetwork.networkutils.api.player.NetworkPlayer;
import lombok.val;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    private final MessageAPI msgApi = new MessageAPI(GameEngine.getINSTANCE().getMsgCache(), MessageAPI.PrefixType.SYSTEM);
    private final MongoDatabase database = Bukkit.getServicesManager().getRegistration(MongoDatabase.class).getProvider();
    private final Game game;

    public StartCommand() {
        val provider = Bukkit.getServicesManager().getRegistration(Game.class);

        if (provider == null) {
            this.game = null;
            return;
        }

        this.game = provider.getProvider();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(this.msgApi.getMessage("en", "command.only.player"));
            return true;
        }

        val language = new NetworkPlayer(database, player.getUniqueId()).getLanguage();

        if (!player.hasPermission("game.start")) {
            player.sendMessage(this.msgApi.getMessage(language, "command.missing.permission"));
            return true;
        }

        if (game == null) {
            player.sendMessage(this.msgApi.getMessage(language, "command.start.game.null", Placeholder.unparsed("error", "Game#NULL")));
            return true;
        }

        if (!this.game.phaseRequirement(GameState.STARTING)) {
            player.sendMessage(this.msgApi.getMessage(language, "command.start.game.null", Placeholder.unparsed("error", "Game#START")));
            return true;
        }

        this.game.loadPhase(GameState.STARTING);

        player.sendMessage(this.msgApi.getMessage(language, "command.start.game"));
        return true;
    }
}
