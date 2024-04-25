package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.usedapi.AllWorldManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* MOB_AROUND {distance} {Your commands here} */
public class AllMobs extends MixedCommand implements FeatureParentInterface {

    public AllMobs(){
        setCanExecuteCommands(true);
    }

    public static void mobAroundExecution(Location location, @Nullable Entity receiver, boolean forceMute, List<String> args, ActionInfo aInfo) {

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {

                    List<Entity> entities = new ArrayList<>();
                    for (String world : AllWorldManager.getWorlds()) {
                        Optional<World> wOpt = AllWorldManager.getWorld(world);
                        if (!wOpt.isPresent()) continue;

                        for (Entity e : wOpt.get().getEntities()) {
                            if (e instanceof LivingEntity && !(e instanceof Player) && e instanceof Mob) {

                                if (e.hasMetadata("NPC") || e.equals(receiver)) continue;
                                entities.add(e);
                            }
                        }
                    }

                    CommmandThatRunsCommand.runEntityCommands(entities, args, aInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        SCore.schedulerHook.runTask(runnable, 0);
    }

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(receiver.getLocation(), receiver, false, args, aInfo);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        String around = "ALL_MOBS {Your commands here}";
        if (args.size() < 1) error = notEnoughArgs + around;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ALL_MOBS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ALL_MOBS {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    @Override
    public List<FeatureInterface> getFeatures() {
        return null;
    }

    @Override
    public String getParentInfo() {
        return "MOB_AROUND Command";
    }

    @Override
    public ConfigurationSection getConfigurationSection() {
        return null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void reload() {

    }

    @Override
    public void openEditor(@NotNull Player player) {

    }

    @Override
    public void openBackEditor(@NotNull Player player) {

    }

    @Override
    public void save() {

    }

    @Override
    public boolean isPremium() {
        return false;
    }
}
