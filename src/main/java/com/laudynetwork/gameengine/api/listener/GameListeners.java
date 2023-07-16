package com.laudynetwork.gameengine.api.listener;

import com.laudynetwork.gameengine.GameEngine;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public class GameListeners implements Listener {

    private static GameListeners INSTANCE;

    public GameListeners() {
        INSTANCE = this;
    }

    public static void unregisterAll() {
        HandlerList.unregisterAll(INSTANCE);
    }

    public static <E extends Event> void listen(Class<E> clazz, EventPriority priority, boolean ignoreCancelled, Consumer<E> onEvent) {
        Bukkit.getPluginManager().registerEvent(clazz, INSTANCE, priority, (listener, event) -> {

            if (event.getClass().isInstance(event) && event.getClass().getName().equals(clazz.getName()))
                onEvent.accept((E) event);

        }, GameEngine.getINSTANCE(), ignoreCancelled);
    }

}
