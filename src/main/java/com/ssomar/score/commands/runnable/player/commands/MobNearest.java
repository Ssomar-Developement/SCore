package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.entity.EntityRunCommandsBuilder;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
public class MobNearest extends PlayerCommand implements FeatureParentInterface {

    public static void mobAroundExecution(Location location, @Nullable Entity receiver, boolean forceMute, List<String> args, ActionInfo aInfo) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                double distance = Double.valueOf(args.get(0));

                Entity e = receiver.getWorld().getEntities().stream()
                        .filter(p -> !p.equals(receiver) && !(p instanceof Player) && p instanceof Mob)
                        .min(Comparator.comparingDouble((p) -> p.getLocation().distanceSquared(receiver.getLocation())))
                        .orElse(null);

                if (e == null || e.getLocation().distance(receiver.getLocation()) > distance) {
                    return;
                }

                StringPlaceholder sp = new StringPlaceholder();
                sp.setAroundTargetEntityPlcHldr(e.getUniqueId());

                ActionInfo aInfo2 = aInfo.clone();
                aInfo2.setEntityUUID(e.getUniqueId());

                /* regroup the last args that correspond to the commands */
                StringBuilder prepareCommands = new StringBuilder();
                /* Remove the maxdistance arg*/
                args.remove(0);
                for (String s : args) {
                    prepareCommands.append(s);
                    prepareCommands.append(" ");
                }
                prepareCommands.deleteCharAt(prepareCommands.length() - 1);

                String buildCommands = prepareCommands.toString();
                String[] tab;
                if (buildCommands.contains("<+>")) tab = buildCommands.split("<\\+>");
                else {
                    tab = new String[1];
                    tab[0] = buildCommands;
                }
                List<String> commands = new ArrayList<>();
                for (int m = 0; m < tab.length; m++) {
                    String s = tab[m];
                    while (s.startsWith(" ")) {
                        s = s.substring(1);
                    }
                    while (s.endsWith(" ")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    if (s.startsWith("/")) s = s.substring(1);

                    commands.add(s);
                }
                commands = sp.replacePlaceholders(commands);
                EntityRunCommandsBuilder builder = new EntityRunCommandsBuilder(commands, aInfo2);
                CommandsExecutor.runCommands(builder);
            }
        };
        runnable.runTask(SCore.plugin);
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(receiver.getLocation(), receiver, false, args, aInfo);
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
