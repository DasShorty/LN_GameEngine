package com.laudynetwork.gameengine;

import com.laudynetwork.gameengine.api.animation.AnimationController;
import com.laudynetwork.gameengine.command.GameCommand;
import com.laudynetwork.gameengine.command.StartCommand;
import com.laudynetwork.gameengine.game.backend.GameDataHandler;
import com.laudynetwork.networkutils.api.messanger.backend.MessageCache;
import com.laudynetwork.networkutils.essentials.control.api.ControlSubCommandHandler;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GameEngine extends JavaPlugin {

    @Getter
    private static GameEngine INSTANCE;
    private MessageCache msgCache;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getLogger().info("Starting Game Engine");
        Bukkit.getLogger().info("\n" +
                "   _____                          \n" +
                "  / ____|                         \n" +
                " | |  __  __ _ _ __ ___   ___     \n" +
                " | | |_ |/ _` | '_ ` _ \\ / _ \\    \n" +
                " | |__| | (_| | | | | | |  __/    \n" +
                "  \\_____|\\__,_|_| |_|_|_|\\___|    \n" +
                " |  ____|           (_)           \n" +
                " | |__   _ __   __ _ _ _ __   ___ \n" +
                " |  __| | '_ \\ / _` | | '_ \\ / _ \\\n" +
                " | |____| | | | (_| | | | | |  __/\n" +
                " |______|_| |_|\\__, |_|_| |_|\\___|\n" +
                "                __/ |             \n" +
                "               |___/              \n");
        val dataHandler = new GameDataHandler();
        Bukkit.getServicesManager().register(GameDataHandler.class, dataHandler, this, ServicePriority.Normal);

        val animationController = new AnimationController();
        Bukkit.getServicesManager().register(AnimationController.class, animationController, this, ServicePriority.Normal);


        val subCommandHandler = Bukkit.getServicesManager().getRegistration(ControlSubCommandHandler.class).getProvider();

        this.msgCache = new MessageCache();
        this.msgCache.loadFileInCache(this.getResource("translations/own/de.json"), "de");
        this.msgCache.loadFileInCache(this.getResource("translations/own/en.json"), "en");
        this.msgCache.loadFileInCache(this.getResource("translations/plugins/de.json"), "de");
        this.msgCache.loadFileInCache(this.getResource("translations/plugins/en.json"), "en");

        subCommandHandler.registerSubCommand(new GameCommand(dataHandler));

        getCommand("start").setExecutor(new StartCommand());

    }

}
