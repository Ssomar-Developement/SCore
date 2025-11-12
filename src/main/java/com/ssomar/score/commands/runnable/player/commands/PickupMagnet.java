package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.features.types.list.ListDetailedMaterialFeature;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PickupMagnet extends PlayerCommand {

    public PickupMagnet() {
        CommandSetting radius = new CommandSetting("radius", 0, Double.class, 5.0);
        CommandSetting duration = new CommandSetting("duration", 1, Integer.class, 100);
        CommandSetting speed = new CommandSetting("speed", 2, Double.class, 0.3);
        CommandSetting itemTypes = new CommandSetting("itemTypes", 3, String.class, "ALL");
        CommandSetting blacklist = new CommandSetting("blacklist", 4, String.class, "");
        CommandSetting particleEffect = new CommandSetting("particleEffect", 5, Boolean.class, true);
        CommandSetting sound = new CommandSetting("sound", 6, Boolean.class, false);
        CommandSetting velocityMode = new CommandSetting("velocityMode", 7, String.class, "DIRECT");

        List<CommandSetting> settings = getSettings();
        settings.add(radius);
        settings.add(duration);
        settings.add(speed);
        settings.add(itemTypes);
        settings.add(blacklist);
        settings.add(particleEffect);
        settings.add(sound);
        settings.add(velocityMode);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        SsomarDev.testMsg("§e[PICKUP_MAGNET] Command started for player: " + receiver.getName(), true);

        double radius = (double) sCommandToExec.getSettingValue("radius");
        int duration = (int) sCommandToExec.getSettingValue("duration");
        double speed = (double) sCommandToExec.getSettingValue("speed");
        String itemTypesStr = (String) sCommandToExec.getSettingValue("itemTypes");
        String blacklistStr = (String) sCommandToExec.getSettingValue("blacklist");
        boolean particleEffect = (boolean) sCommandToExec.getSettingValue("particleEffect");
        boolean playSound = (boolean) sCommandToExec.getSettingValue("sound");
        String velocityMode = (String) sCommandToExec.getSettingValue("velocityMode");

        SsomarDev.testMsg("§e[PICKUP_MAGNET] Parameters: radius=" + radius + ", duration=" + duration +
                          ", speed=" + speed + ", itemTypes=" + itemTypesStr + ", blacklist=" + blacklistStr +
                          ", particles=" + particleEffect + ", sound=" + playSound + ", mode=" + velocityMode, true);

        // Parse item types filter using SCore's ListDetailedMaterialFeature
        ListDetailedMaterialFeature whitelist = new ListDetailedMaterialFeature(false); // false = for items
        if (!itemTypesStr.isEmpty() && !itemTypesStr.equalsIgnoreCase("ALL")) {
            List<String> whitelistEntries = Arrays.asList(itemTypesStr.split(","));
            whitelist.load(SCore.plugin, whitelistEntries, true);
            SsomarDev.testMsg("§e[PICKUP_MAGNET] Whitelist loaded: " + whitelist.getValues().size() + " entries", true);
        } else {
            SsomarDev.testMsg("§e[PICKUP_MAGNET] No whitelist (accepting ALL items)", true);
        }

        // Parse blacklist using SCore's ListDetailedMaterialFeature
        ListDetailedMaterialFeature blacklist = new ListDetailedMaterialFeature(false); // false = for items
        if (!blacklistStr.isEmpty()) {
            List<String> blacklistEntries = Arrays.asList(blacklistStr.split(","));
            blacklist.load(SCore.plugin, blacklistEntries, true);
            SsomarDev.testMsg("§e[PICKUP_MAGNET] Blacklist loaded: " + blacklist.getValues().size() + " entries", true);
        } else {
            SsomarDev.testMsg("§e[PICKUP_MAGNET] No blacklist", true);
        }

        // Instant pulse mode
        if (duration == 0) {
            SsomarDev.testMsg("§e[PICKUP_MAGNET] Using instant pulse mode", true);
            pullItems(receiver, radius, speed, whitelist, blacklist, particleEffect, playSound, velocityMode);
            return;
        }

        // Permanent mode
        if (duration < 0) {
            duration = Integer.MAX_VALUE;
            SsomarDev.testMsg("§e[PICKUP_MAGNET] Using permanent mode", true);
        } else {
            SsomarDev.testMsg("§e[PICKUP_MAGNET] Using duration mode: " + duration + " ticks", true);
        }

        // Duration-based mode
        final int finalDuration = duration;
        final int[] ticksElapsed = {0};

        AtomicReference<ScheduledTask> task = new AtomicReference<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (ticksElapsed[0] >= finalDuration || !receiver.isOnline() || receiver.isDead()) {
                    if (task.get() != null) {
                        task.get().cancel();
                    }
                    SsomarDev.testMsg("§e[PICKUP_MAGNET] Task ended. Ticks elapsed: " + ticksElapsed[0], true);
                } else {
                    pullItems(receiver, radius, speed, whitelist, blacklist, particleEffect, playSound, velocityMode);
                    ticksElapsed[0]++;
                }
            }
        };

        task.set(SCore.schedulerHook.runRepeatingTask(runnable, 0L, 1L));
    }

    private void pullItems(Player receiver, double radius, double speed, ListDetailedMaterialFeature whitelist,
                          ListDetailedMaterialFeature blacklist, boolean particleEffect, boolean playSound, String velocityMode) {
        Location playerLoc = receiver.getLocation().add(0, 1, 0); // Center at player's chest height

        boolean itemPulled = false;
        int totalEntities = 0;
        int itemEntities = 0;
        int tooClose = 0;
        int whitelistFiltered = 0;
        int blacklistFiltered = 0;
        int pulled = 0;

        // Get nearby entities and filter for Item type
        for (org.bukkit.entity.Entity entity : receiver.getNearbyEntities(radius, radius, radius)) {
            totalEntities++;
            if (!(entity instanceof Item)) {
                continue;
            }
            itemEntities++;
            Item item = (Item) entity;

            // Skip if item is too close (already being picked up naturally)
            if (item.getLocation().distance(playerLoc) < 1.0) {
                tooClose++;
                continue;
            }

            // Filter by whitelist using SCore's proper validation
            if (!whitelist.getValues().isEmpty()) {
                if (!whitelist.verifItem(item.getItemStack())) {
                    whitelistFiltered++;
                    SsomarDev.testMsg("§e[PICKUP_MAGNET] Filtered by whitelist: " + item.getItemStack().getType(), true);
                    continue;
                }
            }

            // Filter by blacklist using SCore's proper validation
            if (!blacklist.getValues().isEmpty()) {
                if (blacklist.verifItem(item.getItemStack())) {
                    blacklistFiltered++;
                    SsomarDev.testMsg("§e[PICKUP_MAGNET] Filtered by blacklist: " + item.getItemStack().getType(), true);
                    continue; // Skip blacklisted items
                }
            }

            // Calculate velocity vector
            Vector direction = playerLoc.toVector().subtract(item.getLocation().toVector()).normalize();

            // Apply velocity based on mode
            if (velocityMode.equalsIgnoreCase("CURVED")) {
                // Add upward component for curved trajectory
                direction.setY(direction.getY() + 0.2);
            }

            Vector velocity = direction.multiply(speed);
            item.setVelocity(velocity);

            // Prevent item from despawning while being pulled
            if (item.getPickupDelay() > 0) {
                item.setPickupDelay(0);
            }

            SsomarDev.testMsg("§a[PICKUP_MAGNET] Pulling item: " + item.getItemStack().getType() +
                              " x" + item.getItemStack().getAmount() +
                              " from distance: " + String.format("%.2f", item.getLocation().distance(playerLoc)), true);

            // Particle effect
            if (particleEffect) {
                Location itemLoc = item.getLocation();
                // Create particle trail from item to player
                Vector particleVector = playerLoc.toVector().subtract(itemLoc.toVector()).normalize().multiply(0.5);
                for (int i = 0; i < 3; i++) {
                    Location particleLoc = itemLoc.clone().add(particleVector.clone().multiply(i));
                    try {
                        receiver.getWorld().spawnParticle(Particle.PORTAL, particleLoc, 1, 0.1, 0.1, 0.1, 0);
                    } catch (Exception e) {
                        // Particle may not exist in older versions
                        break;
                    }
                }
            }

            itemPulled = true;
            pulled++;
        }

        SsomarDev.testMsg("§b[PICKUP_MAGNET] Summary: total=" + totalEntities +
                          ", items=" + itemEntities +
                          ", tooClose=" + tooClose +
                          ", whitelist=" + whitelistFiltered +
                          ", blacklist=" + blacklistFiltered +
                          ", pulled=" + pulled, true);

        // Play sound if any item was pulled
        if (playSound && itemPulled) {
            try {
                receiver.playSound(receiver.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.3f, 1.5f);
            } catch (Exception e) {
                // Sound may not exist in older versions
            }
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PICKUP_MAGNET");
        names.add("ITEM_MAGNET");
        names.add("PULL_ITEMS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PICKUP_MAGNET radius:5.0 duration:100 speed:0.3 itemTypes:ALL blacklist: particleEffect:true sound:false velocityMode:DIRECT";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_AQUA;
    }
}
