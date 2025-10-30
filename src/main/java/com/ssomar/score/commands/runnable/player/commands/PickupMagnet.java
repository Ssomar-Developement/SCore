package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        double radius = (double) sCommandToExec.getSettingValue("radius");
        int duration = (int) sCommandToExec.getSettingValue("duration");
        double speed = (double) sCommandToExec.getSettingValue("speed");
        String itemTypesStr = (String) sCommandToExec.getSettingValue("itemTypes");
        String blacklistStr = (String) sCommandToExec.getSettingValue("blacklist");
        boolean particleEffect = (boolean) sCommandToExec.getSettingValue("particleEffect");
        boolean playSound = (boolean) sCommandToExec.getSettingValue("sound");
        String velocityMode = (String) sCommandToExec.getSettingValue("velocityMode");

        // Parse item types filter
        Set<Material> allowedTypes = parseItemTypes(itemTypesStr);
        Set<Material> blacklistedTypes = parseBlacklist(blacklistStr);

        // Instant pulse mode
        if (duration == 0) {
            pullItems(receiver, radius, speed, allowedTypes, blacklistedTypes, particleEffect, playSound, velocityMode);
            return;
        }

        // Permanent mode
        if (duration < 0) {
            duration = Integer.MAX_VALUE;
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
                } else {
                    pullItems(receiver, radius, speed, allowedTypes, blacklistedTypes, particleEffect, playSound, velocityMode);
                    ticksElapsed[0]++;
                }
            }
        };

        task.set(SCore.schedulerHook.runRepeatingTask(runnable, 0L, 1L));
    }

    private void pullItems(Player receiver, double radius, double speed, Set<Material> allowedTypes,
                          Set<Material> blacklistedTypes, boolean particleEffect, boolean playSound, String velocityMode) {
        Location playerLoc = receiver.getLocation().add(0, 1, 0); // Center at player's chest height

        boolean itemPulled = false;

        // Get nearby entities and filter for Item type
        for (org.bukkit.entity.Entity entity : receiver.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Item)) {
                continue;
            }
            Item item = (Item) entity;
            // Skip if item is too close (already being picked up naturally)
            if (item.getLocation().distance(playerLoc) < 1.0) {
                continue;
            }

            // Filter by item type
            Material itemMaterial = item.getItemStack().getType();
            if (!allowedTypes.isEmpty() && !allowedTypes.contains(itemMaterial)) {
                continue;
            }
            if (blacklistedTypes.contains(itemMaterial)) {
                continue;
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
        }

        // Play sound if any item was pulled
        if (playSound && itemPulled) {
            try {
                receiver.playSound(receiver.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.3f, 1.5f);
            } catch (Exception e) {
                // Sound may not exist in older versions
            }
        }
    }

    private Set<Material> parseItemTypes(String itemTypesStr) {
        Set<Material> types = new HashSet<>();

        if (itemTypesStr == null || itemTypesStr.trim().isEmpty() || itemTypesStr.equalsIgnoreCase("ALL")) {
            return types; // Empty set means all types allowed
        }

        String[] typeNames = itemTypesStr.split(",");
        for (String typeName : typeNames) {
            typeName = typeName.trim().toUpperCase();

            // Handle category shortcuts
            if (typeName.equals("ORES")) {
                types.addAll(getOres());
            } else if (typeName.equals("WEAPONS")) {
                types.addAll(getWeapons());
            } else if (typeName.equals("FOOD")) {
                types.addAll(getFood());
            } else if (typeName.equals("TOOLS")) {
                types.addAll(getTools());
            } else if (typeName.equals("ARMOR")) {
                types.addAll(getArmor());
            } else {
                // Try to parse as material
                try {
                    Material mat = Material.valueOf(typeName);
                    types.add(mat);
                } catch (IllegalArgumentException e) {
                    // Invalid material name, skip it
                }
            }
        }

        return types;
    }

    private Set<Material> parseBlacklist(String blacklistStr) {
        Set<Material> blacklist = new HashSet<>();

        if (blacklistStr == null || blacklistStr.trim().isEmpty()) {
            return blacklist;
        }

        String[] materials = blacklistStr.split(",");
        for (String matName : materials) {
            matName = matName.trim().toUpperCase();
            try {
                Material mat = Material.valueOf(matName);
                blacklist.add(mat);
            } catch (IllegalArgumentException e) {
                // Invalid material name, skip it
            }
        }

        return blacklist;
    }

    private Set<Material> getOres() {
        Set<Material> ores = new HashSet<>();
        for (Material mat : Material.values()) {
            String name = mat.name();
            if (name.contains("_ORE") || name.equals("ANCIENT_DEBRIS") ||
                name.equals("RAW_IRON") || name.equals("RAW_GOLD") || name.equals("RAW_COPPER")) {
                ores.add(mat);
            }
        }
        return ores;
    }

    private Set<Material> getWeapons() {
        Set<Material> weapons = new HashSet<>();
        for (Material mat : Material.values()) {
            String name = mat.name();
            if (name.contains("_SWORD") || name.contains("_AXE") || name.equals("BOW") ||
                name.equals("CROSSBOW") || name.equals("TRIDENT")) {
                weapons.add(mat);
            }
        }
        return weapons;
    }

    private Set<Material> getFood() {
        Set<Material> food = new HashSet<>();
        for (Material mat : Material.values()) {
            if (mat.isEdible()) {
                food.add(mat);
            }
        }
        return food;
    }

    private Set<Material> getTools() {
        Set<Material> tools = new HashSet<>();
        for (Material mat : Material.values()) {
            String name = mat.name();
            if (name.contains("_PICKAXE") || name.contains("_SHOVEL") ||
                name.contains("_HOE") || name.contains("_AXE")) {
                tools.add(mat);
            }
        }
        return tools;
    }

    private Set<Material> getArmor() {
        Set<Material> armor = new HashSet<>();
        for (Material mat : Material.values()) {
            String name = mat.name();
            if (name.contains("_HELMET") || name.contains("_CHESTPLATE") ||
                name.contains("_LEGGINGS") || name.contains("_BOOTS")) {
                armor.add(mat);
            }
        }
        return armor;
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
