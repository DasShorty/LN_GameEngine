package com.laudynetwork.gameengine.api.animation.impl;

import com.laudynetwork.gameengine.GameEngine;
import com.laudynetwork.gameengine.api.animation.Animation;
import lombok.val;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public abstract class ActionBarAnimation extends Animation {

    public ActionBarAnimation(long delay, long period) {
        super(delay, period);
    }


    public abstract Component onRender();

    @Override
    public void run(long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(GameEngine.getINSTANCE(), () -> {
            val component = onRender();
            sendTo().forEach(player -> player.sendActionBar(component));
        }, delay, period);
    }
}
