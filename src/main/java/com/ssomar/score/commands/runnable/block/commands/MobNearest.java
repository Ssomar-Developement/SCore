package com.ssomar.score.commands.runnable.block.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* MOB_AROUND {distance} {Your commands here} */
public class MobNearest extends BlockCommand implements FeatureParentInterface {

    public MobNearest(){
        setCanExecuteCommands(true);
    }

    public static void mobAroundExecution(Location location, boolean forceMute, List<String> args, ActionInfo aInfo) {

        List<String> verifyArgs = new ArrayList<>(args);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                double distance = Double.valueOf(verifyArgs.get(0));

                Entity e = location.getWorld().getEntities().stream()
                        .filter(p -> !(p instanceof Player) && p instanceof Mob)
                        .min(Comparator.comparingDouble((p) -> p.getLocation().distanceSquared(location)))
                        .orElse(null);

                if (e == null || e.getLocation().distance(location) > distance) {
                    return;
                }
                List<Entity> targets = new ArrayList<>();
                targets.add(e);

                CommmandThatRunsCommand.runEntityCommands(targets, verifyArgs.subList(1, verifyArgs.size()), aInfo);
            }
        };
        runnable.runTask(SCore.plugin);
    }

    @Override
    public void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(block.getLocation(), false, args, aInfo);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_NEAREST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_NEAREST {max distance} {Your commands here}";
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
        return null;
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
