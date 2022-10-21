package com.ssomar.score.features.custom.drop.glowdrop;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class GlowDropManager {

    private static GlowDropManager instance;
    private final HashMap<ChatColor, Team> teams;


    public GlowDropManager() {
        instance = this;
        teams = new HashMap<>();
        if (!SCore.is1v11Less()) {
            ScoreboardManager scoreboardManager = ExecutableItems.plugin.getServer().getScoreboardManager();
            for (ChatColor color : ChatColor.values()) {
                Team team;
                try {
                    team = scoreboardManager.getMainScoreboard().registerNewTeam("team_" + color.name());
                } catch (IllegalArgumentException er) {
                    team = scoreboardManager.getMainScoreboard().getTeam("team_" + color.name());
                }
                if (team != null) {
                    team.setColor(color);
                    teams.put(color, team);
                }
            }
        }
    }

    public static GlowDropManager getInstance() {
        if (instance == null) {
            instance = new GlowDropManager();
        }
        return instance;
    }

    public void addGlow(Entity entity, ChatColor color) {
        SsomarDev.testMsg("addGlow : "+entity.getType()+ " "+color.name(), false);
        if (teams.containsKey(color)) {
            Team team = teams.get(color);
            team.addEntry(entity.getUniqueId().toString());
            if(entity instanceof OfflinePlayer) team.addEntry(((OfflinePlayer) entity).getName());
            entity.setGlowing(true);
        }
        else SsomarDev.testMsg("color not found > "+color.name(), false);
    }

    public void removeGlow(Entity entity, ChatColor color) {
        if (teams.containsKey(color)) {
            Team team = teams.get(color);
            team.removeEntry(entity.getUniqueId().toString());
            if(entity instanceof OfflinePlayer) team.removePlayer((OfflinePlayer) entity);
            entity.setGlowing(false);
        }
    }
}
