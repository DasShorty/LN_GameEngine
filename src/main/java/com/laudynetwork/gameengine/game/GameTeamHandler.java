package com.laudynetwork.gameengine.game;

import lombok.val;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GameTeamHandler {

    private final Map<String, Team> gameTeams = new HashMap<>();

    public void team(String id, Scoreboard scoreboard, Component prefix, Component suffix, NamedTextColor color) {
        var entryTeam = scoreboard.getEntryTeam(id);
        if (entryTeam == null)
            entryTeam = scoreboard.registerNewTeam(id);

        entryTeam.prefix(prefix);
        entryTeam.suffix(suffix);
        entryTeam.color(color);

        this.gameTeams.put(id, entryTeam);
    }
    /**
     * removed out of Hash Map but not from the scoreboard
     */
    public void removeTeam(String id) {

        if (!this.gameTeams.containsKey(id))
            return;

        val team = this.gameTeams.get(id);
    }

    /**
     * @return team from the id or null if the id didn't exist
     */
    public @Nullable Team team(String id) {
        if (!this.gameTeams.containsKey(id))
            return null;

        return this.gameTeams.get(id);
    }

}
