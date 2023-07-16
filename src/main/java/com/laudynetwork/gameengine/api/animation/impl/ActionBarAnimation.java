package com.laudynetwork.gameengine.api.animation.impl;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.gameengine.api.animation.Animation;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public abstract class ActionBarAnimation extends Animation {

    private BukkitTask task;

    public ActionBarAnimation(long delay, long period) {
        super(delay, period);
    }

    @Override
    public void cancel() {
        if (task == null)
            return;
        if (task.isCancelled())
            return;
        task.cancel();
    }

    public abstract Component onRender(Player player);

    @Override
    public void run(long delay, long period) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(GameEngine.getINSTANCE(), () -> {
            onTick();
            sendTo().forEach(player -> player.sendActionBar(onRender(player)));
        }, delay, period);
    }
}
