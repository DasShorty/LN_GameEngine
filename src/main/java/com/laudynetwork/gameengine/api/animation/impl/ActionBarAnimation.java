package com.laudynetwork.gameengine.api.animation.impl;

import com.laudynetwork.gameengine.GameEngine;
import lombok.val;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class ActionBarAnimation {

    public ActionBarAnimation(long delay, long period) {
        run(delay, period);
    }

    public abstract Component onRender();

    public abstract List<? extends Player> sendTo();

    private void run(long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(GameEngine.getINSTANCE(), () -> {
            val component = onRender();
            sendTo().forEach(player -> player.sendActionBar(component));
        }, delay, period);
    }
}
