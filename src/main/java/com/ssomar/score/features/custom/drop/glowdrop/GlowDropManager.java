package com.ssomar.score.features.custom.drop.glowdrop;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GlowDropManager {

    private static GlowDropManager instance;
    private final HashMap<ChatColor, Team> teams;

    private Map<UUID, ChatColor> links = new HashMap<>();


    public GlowDropManager() {
        instance = this;
        teams = new HashMap<>();
        links = new HashMap<>();
        if (!SCore.is1v11Less()) {
            ScoreboardManager scoreboardManager = SCore.plugin.getServer().getScoreboardManager();
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

    public Optional<String> onRequestPlaceholder(OfflinePlayer player, String params) {
        if (params.startsWith("cmd-glow")) {
            if (links.containsKey(player.getUniqueId())) {
                return Optional.of("&" + links.get(player.getUniqueId()).getChar());
            }
            return Optional.of("");
        }
        return Optional.empty();
    }

    public void addGlow(Entity entity, ChatColor color) {
        SsomarDev.testMsg("addGlow : " + entity.getType() + " " + color.name(), false);
        links.put(entity.getUniqueId(), color);
        /* if(SCore.hasTAB && entity instanceof Player){
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(entity.getUniqueId());
            TabAPI.getInstance().getTeamManager().updateTeamData(tabPlayer);
        }*/
        if (teams.containsKey(color)) {
            Team team = teams.get(color);
            team.addEntry(entity.getUniqueId().toString());
            if (entity instanceof OfflinePlayer) team.addEntry(((OfflinePlayer) entity).getName());
            entity.setGlowing(true);
        } else SsomarDev.testMsg("color not found > " + color.name(), false);
    }

    public void removeGlow(Entity entity, ChatColor color) {
        links.remove(entity.getUniqueId());
        /* if(SCore.hasTAB && entity instanceof Player){
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(entity.getUniqueId());
            TabAPI.getInstance().getTeamManager().resumeTeamHandling(tabPlayer);
        }*/
        if (teams.containsKey(color)) {
            Team team = teams.get(color);
            team.removeEntry(entity.getUniqueId().toString());
            if (entity instanceof OfflinePlayer) team.removePlayer((OfflinePlayer) entity);
            entity.setGlowing(false);
        }
    }
}
