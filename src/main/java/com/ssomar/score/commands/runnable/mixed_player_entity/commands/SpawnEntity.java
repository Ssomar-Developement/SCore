package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.particles.commands.XParticle;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.EntityBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnEntity extends MixedCommand {

    public SpawnEntity() {
        CommandSetting entity = new CommandSetting("entity", 0, EntityBuilder.class, "ZOMBIE");
        CommandSetting amount = new CommandSetting("amount", 1, Integer.class, 1);
        CommandSetting world = new CommandSetting("world", 1, String.class, "");
        CommandSetting x = new CommandSetting("x", 2, Double.class, Double.MIN_VALUE);
        CommandSetting y = new CommandSetting("y", 2, Double.class, Double.MIN_VALUE);
        CommandSetting z = new CommandSetting("z", 2, Double.class, Double.MIN_VALUE);
        CommandSetting yaw = new CommandSetting("yaw", 2, Double.class, Double.MIN_VALUE);
        CommandSetting pitch = new CommandSetting("pitch", 3, Double.class, Double.MIN_VALUE);
        CommandSetting offset = new CommandSetting("offset", 4, Double.class, 0);
        List<CommandSetting> settings = getSettings();
        settings.add(world);
        settings.add(entity);
        settings.add(amount);
        settings.add(yaw);
        settings.add(pitch);
        settings.add(offset);
        settings.add(x);
        settings.add(y);
        settings.add(z);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        int amount = (int) sCommandToExec.getSettingValue("amount");
        double yaw = (double) sCommandToExec.getSettingValue("yaw");
        double pitch = (double) sCommandToExec.getSettingValue("pitch");

        String worldName = (String) sCommandToExec.getSettingValue("world");
        double x = (double) sCommandToExec.getSettingValue("x");
        double y = (double) sCommandToExec.getSettingValue("y");
        double z = (double) sCommandToExec.getSettingValue("z");


        EntityBuilder entityBuilder = (EntityBuilder) sCommandToExec.getSettingValue("entity");

        Location receiverLoc = receiver.getLocation();
        if (yaw == Double.MIN_VALUE) yaw = receiverLoc.getYaw();
        if (pitch == Double.MIN_VALUE) pitch = receiverLoc.getPitch();
        World world = receiver.getWorld();
        if (!worldName.isEmpty()) {
            Optional<World> world1 = AllWorldManager.getWorld(worldName);
            if (world1.isPresent()) {
                world = world1.get();
            }
        }
        if (x == Double.MIN_VALUE) x = receiverLoc.getX();
        if (y == Double.MIN_VALUE) y = receiverLoc.getY();
        if (z == Double.MIN_VALUE) z = receiverLoc.getZ();

        receiverLoc.setWorld(world);
        receiverLoc.setX(x);
        receiverLoc.setY(y);
        receiverLoc.setZ(z);

        Vector dir = XParticle.getDirection(yaw, pitch);
        Vector offset = dir.clone().multiply((double) sCommandToExec.getSettingValue("offset"));

        receiverLoc.add(offset);

        // Can be null
        EntityType toSpawnEntityType = entityBuilder.getEntityType();
        EntityType lightning = SCore.is1v20v5Plus() ? EntityType.LIGHTNING_BOLT : EntityType.valueOf("LIGHTNING");

        for (int i = 0; i < amount; i++) {

            if (toSpawnEntityType != null && toSpawnEntityType.equals(lightning)) {
                receiver.getWorld().strikeLightning(receiverLoc);
                continue;
            }

            Entity e = entityBuilder.buildEntity(receiverLoc);
            if (toSpawnEntityType != null && toSpawnEntityType.equals(EntityType.FIREBALL))
                e.setVelocity(new Vector(0, 0, 0));
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SPAWN_ENTITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SPAWN_ENTITY entity:ZOMBIE amount:1 pitch:0 yaw:0 offset:0";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

}
