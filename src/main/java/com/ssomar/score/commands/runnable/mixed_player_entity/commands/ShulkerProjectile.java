package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import com.ssomar.score.usedapi.AllWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * SHULKER_PROJECTILE command - Spawns a shulker bullet with customizable direction, owner, and target.
 *
 * Parameters:
 * - world: The world to spawn in (default: receiver's world)
 * - x, y, z: Spawn coordinates (default: receiver's location)
 * - directionX, directionY, directionZ: Direction vector for the projectile
 * - speed: Velocity multiplier (default: 1.0)
 * - ownerUUID: UUID of the entity that owns the bullet (bullet won't attack this entity)
 * - targetUUID: UUID of the entity the bullet will track and attack
 */
public class ShulkerProjectile extends MixedCommand {

    public ShulkerProjectile() {
        // Spawn location settings
        CommandSetting world = new CommandSetting("world", -1, String.class, "");
        CommandSetting x = new CommandSetting("x", -1, Double.class, Double.MIN_VALUE);
        CommandSetting y = new CommandSetting("y", -1, Double.class, Double.MIN_VALUE);
        CommandSetting z = new CommandSetting("z", -1, Double.class, Double.MIN_VALUE);

        // Direction settings (for initial movement direction)
        CommandSetting directionX = new CommandSetting("directionX", -1, Double.class, Double.MIN_VALUE);
        CommandSetting directionY = new CommandSetting("directionY", -1, Double.class, Double.MIN_VALUE);
        CommandSetting directionZ = new CommandSetting("directionZ", -1, Double.class, Double.MIN_VALUE);

        // Speed multiplier
        CommandSetting speed = new CommandSetting("speed", -1, Double.class, 1.0);

        // Owner UUID - the entity whose projectile this is (won't attack this entity)
        CommandSetting ownerUUID = new CommandSetting("ownerUUID", -1, UUID.class, null);

        // Target UUID - the entity the projectile will home in on
        CommandSetting targetUUID = new CommandSetting("targetUUID", -1, UUID.class, null);

        List<CommandSetting> settings = getSettings();
        settings.add(world);
        settings.add(x);
        settings.add(y);
        settings.add(z);
        settings.add(directionX);
        settings.add(directionY);
        settings.add(directionZ);
        settings.add(speed);
        settings.add(ownerUUID);
        settings.add(targetUUID);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        // Get spawn location parameters
        String worldName = (String) sCommandToExec.getSettingValue("world");
        double x = (double) sCommandToExec.getSettingValue("x");
        double y = (double) sCommandToExec.getSettingValue("y");
        double z = (double) sCommandToExec.getSettingValue("z");

        // Get direction parameters
        double directionX = (double) sCommandToExec.getSettingValue("directionX");
        double directionY = (double) sCommandToExec.getSettingValue("directionY");
        double directionZ = (double) sCommandToExec.getSettingValue("directionZ");

        // Get speed
        double speed = (double) sCommandToExec.getSettingValue("speed");

        // Get owner and target UUIDs
        UUID ownerUUID = (UUID) sCommandToExec.getSettingValue("ownerUUID");
        UUID targetUUID = (UUID) sCommandToExec.getSettingValue("targetUUID");

        // Determine spawn location
        Location receiverLoc = receiver.getLocation();
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

        Location spawnLoc = new Location(world, x, y, z);

        // Spawn the shulker bullet
        ShulkerBullet bullet = world.spawn(spawnLoc, ShulkerBullet.class);

        // Set owner if provided
        if (ownerUUID != null) {
            Entity ownerEntity = Bukkit.getEntity(ownerUUID);
            if (ownerEntity instanceof LivingEntity) {
                bullet.setShooter((LivingEntity) ownerEntity);
            }
        }

        // Set target if provided - this makes the bullet home in on the target
        // For 1.21.6+, we need to handle the target setting carefully
        if (targetUUID != null) {
            Entity targetEntity = Bukkit.getEntity(targetUUID);
            if (targetEntity instanceof LivingEntity) {
                try {
                    bullet.setTarget((LivingEntity) targetEntity);
                } catch (Exception ignored) {
                    // Fallback for older versions or if target setting fails
                }
            }
        } else {
            // If no target is set, set it to null to avoid NullPointerException in 1.21.6+
            if (SCore.is1v21v6Plus()) {
                try {
                    bullet.setTarget(null);
                } catch (Exception ignored) {
                }
            }
        }

        // Calculate and apply velocity/direction
        Vector velocity;
        if (directionX != Double.MIN_VALUE && directionY != Double.MIN_VALUE && directionZ != Double.MIN_VALUE) {
            // Use provided direction
            velocity = new Vector(directionX, directionY, directionZ);
            if (velocity.lengthSquared() > 0) {
                velocity = velocity.normalize().multiply(speed);
            }
        } else if (receiver instanceof LivingEntity) {
            // Use receiver's eye direction if no direction provided
            velocity = ((LivingEntity) receiver).getEyeLocation().getDirection().multiply(speed);
        } else {
            // Default forward direction
            velocity = receiver.getLocation().getDirection().multiply(speed);
        }

        bullet.setVelocity(velocity);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SHULKER_PROJECTILE");
        names.add("SHULKERPROJECTILE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SHULKER_PROJECTILE world:world_the_end x:0 y:100 z:0 directionX:1 directionY:0 directionZ:0 speed:1 ownerUUID:%player_uuid% targetUUID:%target_uuid%";
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
