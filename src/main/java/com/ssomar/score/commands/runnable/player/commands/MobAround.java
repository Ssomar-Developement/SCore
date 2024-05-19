package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommmandThatRunsCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.features.FeatureInterface;
import com.ssomar.score.features.FeatureParentInterface;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends PlayerCommand implements FeatureParentInterface {

    public MobAround(){
        setCanExecuteCommands(true);
    }

    public static void mobAroundExecution(Location location, @Nullable Entity launcher, @Nullable Entity receiver, boolean forceMute, List<String> args, ActionInfo aInfo) {

        if(launcher != null) aInfo.setLauncherUUID(launcher.getUniqueId());

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    double distance = Double.parseDouble(args.get(0));

                    int startForCommand = 1;
                    boolean mute = true;
                    if (!forceMute) {
                        if (args.get(1).equalsIgnoreCase("true")) {
                            startForCommand = 2;
                            mute = false;
                        } else if (args.get(1).equalsIgnoreCase("false")) {
                            startForCommand = 2;
                        }
                    }

                    List<Entity> entities = new ArrayList<>();
                    for (Entity e : location.getWorld().getNearbyEntities(location, distance, distance, distance)) {
                        if (e instanceof LivingEntity && !(e instanceof Player)) {
                            if (e.hasMetadata("NPC") || e.equals(receiver)) continue;
                            entities.add(e);
                        }
                    }

                    boolean hit = CommmandThatRunsCommand.runEntityCommands(entities, args.subList(startForCommand, args.size()), aInfo);

                    if (!hit && !mute && receiver instanceof Player)
                        sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_ENTITY_HIT));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.runTask(SCore.plugin);
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        mobAroundExecution(receiver.getLocation(), p, receiver, false, args, aInfo);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
       return staticVerify(args, isFinalVerification, getTemplate());
    }

    public static Optional<String> staticVerify(List<String> args, boolean isFinalVerification, String template) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + template);

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, template, false);
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MOB_AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MOB_AROUND {distance} [DisplayMsgIfNoEntity true or false] [conditions] {Your commands here}";
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
