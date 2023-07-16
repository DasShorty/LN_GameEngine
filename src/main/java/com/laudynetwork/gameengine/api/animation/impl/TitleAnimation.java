package com.laudynetwork.gameengine.api.animation.impl;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.gameengine.api.animation.Animation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class TitleAnimation extends Animation {

    private BukkitTask task;

    public TitleAnimation(long delay, long period) {
        super(delay, period);
    }

    public abstract Title.Times times();

    public abstract Component title();

    @Override
    public void cancel() {
        if (task == null)
            return;
        if (task.isCancelled())
            return;
        task.cancel();
    }

    public abstract Component subTitle();

    @Override
    protected void run(long delay, long period) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(GameEngine.getINSTANCE(), () -> {
            onTick();
            sendTo().forEach(player -> {

                player.sendTitlePart(TitlePart.TIMES, times());
                player.sendTitlePart(TitlePart.TITLE, title());
                player.sendTitlePart(TitlePart.SUBTITLE, subTitle());
            });
        }, delay, period);
    }
}
