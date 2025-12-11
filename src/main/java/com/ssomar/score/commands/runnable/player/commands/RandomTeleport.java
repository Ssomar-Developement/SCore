package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTeleport extends PlayerCommand {

    private static final Random RANDOM = new Random();
    private static final int MAX_ATTEMPTS = 50;

    public RandomTeleport() {
        CommandSetting minDistance = new CommandSetting("minDistance", 0, Integer.class, 100, true);
        CommandSetting maxDistance = new CommandSetting("maxDistance", 1, Integer.class, 5000, true);
        CommandSetting showMessage = new CommandSetting("showMessage", 2, Boolean.class, true, true);
        List<CommandSetting> settings = getSettings();
        settings.add(minDistance);
        settings.add(maxDistance);
        settings.add(showMessage);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        Integer minDistance = (Integer) sCommandToExec.getSettingValue("minDistance");
        Integer maxDistance = (Integer) sCommandToExec.getSettingValue("maxDistance");
        Boolean showMessage = (Boolean) sCommandToExec.getSettingValue("showMessage");

        if (minDistance == null) minDistance = 100;
        if (maxDistance == null) maxDistance = 5000;
        if (showMessage == null) showMessage = true;

        if (minDistance > maxDistance) {
            int temp = minDistance;
            minDistance = maxDistance;
            maxDistance = temp;
        }

        World world = receiver.getWorld();
        WorldBorder border = world.getWorldBorder();
        Location borderCenter = border.getCenter();
        double borderSize = border.getSize() / 2;

        Location safeLoc = null;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int distance = minDistance + RANDOM.nextInt(maxDistance - minDistance + 1);
            double angle = RANDOM.nextDouble() * 2 * Math.PI;

            double x = receiver.getLocation().getX() + (distance * Math.cos(angle));
            double z = receiver.getLocation().getZ() + (distance * Math.sin(angle));

            if (Math.abs(x - borderCenter.getX()) > borderSize - 5 ||
                Math.abs(z - borderCenter.getZ()) > borderSize - 5) {
                continue;
            }

            Location testLoc = new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z);

            if (isSafeLocation(testLoc)) {
                safeLoc = testLoc.add(0.5, 1, 0.5);
                break;
            }
        }

        if (safeLoc != null) {
            final Location finalLoc = safeLoc;
            final Boolean finalShowMessage = showMessage;
            Runnable teleportTask = () -> {
                receiver.teleport(finalLoc);
                if (finalShowMessage) {
                    sm.sendMessage(receiver, "&aTeleported to random location! &7(" +
                            (int) finalLoc.getX() + ", " + (int) finalLoc.getY() + ", " + (int) finalLoc.getZ() + ")", false);
                }
            };
            SCore.schedulerHook.runEntityTask(teleportTask, null, receiver, 0);
        } else {
            if (showMessage) {
                sm.sendMessage(receiver, "&cCould not find a safe location. Please try again.", false);
            }
        }
    }

    private boolean isSafeLocation(Location loc) {
        Block block = loc.getBlock();
        Block blockAbove = block.getRelative(0, 1, 0);
        Block blockBelow = block.getRelative(0, -1, 0);

        if (blockBelow.getType() == Material.LAVA || blockBelow.getType() == Material.WATER) {
            return false;
        }

        if (blockBelow.getType() == Material.AIR || !blockBelow.getType().isSolid()) {
            return false;
        }

        if (block.getType() != Material.AIR && block.getType().isSolid()) {
            return false;
        }
        if (blockAbove.getType() != Material.AIR && blockAbove.getType().isSolid()) {
            return false;
        }

        if (blockBelow.getType() == Material.CACTUS || blockBelow.getType() == Material.MAGMA_BLOCK) {
            return false;
        }

        return true;
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("RANDOM_TELEPORT");
        names.add("RANDOMTELEPORT");
        names.add("RTP");
        return names;
    }

    @Override
    public String getTemplate() {
        return "RANDOM_TELEPORT [minDistance:100] [maxDistance:5000] [showMessage:true]";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.LIGHT_PURPLE;
    }
}
