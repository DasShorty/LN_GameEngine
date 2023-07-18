package com.laudynetwork.gameengine.api.endplayer;

import com.laudynetwork.gameengine.GameEngine;
import net.kyori.adventure.util.TriState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EndPlayerHandler {

    private final List<Player> players = new ArrayList<>();

    public void handle(Player player) {

        if (!players.contains(player))
            players.add(player);

        Bukkit.getOnlinePlayers().stream().filter(player1 -> !players.contains(player1)).forEach(onlinePlayer -> {

            onlinePlayer.hidePlayer(GameEngine.getINSTANCE(), player);

        });

        Bukkit.getScheduler().runTaskLaterAsynchronously(GameEngine.getINSTANCE(), () -> {
            synchronized (player) {
                player.setHealth(20.0D);
                player.setGameMode(GameMode.ADVENTURE);
                player.setInvulnerable(true);
                player.setSilent(true);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setFlyingFallDamage(TriState.FALSE);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 250, false, false, false));
            }
        }, 2L);

    }

}
