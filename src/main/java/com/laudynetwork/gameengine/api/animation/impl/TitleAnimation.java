package com.laudynetwork.gameengine.api.animation.impl;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.gameengine.api.animation.Animation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;

public abstract class TitleAnimation extends Animation {
    public TitleAnimation(long delay, long period) {
        super(delay, period);
    }

    public abstract Title.Times times();

    public abstract Component title();

    public abstract Component subTitle();

    @Override
    protected void run(long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(GameEngine.getINSTANCE(), () -> sendTo().forEach(player -> {
            player.sendTitlePart(TitlePart.TIMES, times());
            player.sendTitlePart(TitlePart.TITLE, title());
            player.sendTitlePart(TitlePart.SUBTITLE, subTitle());
        }), delay, period);
    }
}
