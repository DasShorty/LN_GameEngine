package com.laudynetwork.gameengine.api.animation;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Animation {

    public Animation(long delay, long period) {
        run(delay, period);
    }


    public abstract List<? extends Player> sendTo();

    protected abstract void run(long delay, long period);

}
