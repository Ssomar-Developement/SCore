package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.listeners.projectiles.ProjectileInfo;
import com.ssomar.executableitems.listeners.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.PlayerCustomLaunchEntityEvent;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.SProjectileType;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class LocatedLaunch extends PlayerCommand {

    //OCATED_LAUNCH {projectileType} {frontValue positive=front , negative=back} {rightValue right=positive, negative=left} {yValue} {velocity} [vertical rotation] [horizontal rotation]";
    //    }

    public LocatedLaunch() {
        CommandSetting projectileType = new CommandSetting("projectile", 0, String.class, null);
        CommandSetting frontValue = new CommandSetting("frontValue", 1, Double.class, 0);
        CommandSetting rightValue = new CommandSetting("rightValue", 2, Double.class, 0);
        CommandSetting yValue = new CommandSetting("yValue", 3, Double.class, 0);
        CommandSetting velocity = new CommandSetting("velocity", 4, Double.class, 1);
        CommandSetting angleRotationVertical = new CommandSetting("angleRotationVertical", 5, Double.class, 0);
        CommandSetting angleRotationHorizontal = new CommandSetting("angleRotationHorizontal", 6, Double.class, 0);
        List<CommandSetting> settings = getSettings();
        settings.add(projectileType);
        settings.add(frontValue);
        settings.add(rightValue);
        settings.add(yValue);
        settings.add(velocity);
        settings.add(angleRotationVertical);
        settings.add(angleRotationHorizontal);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ActionInfo aInfo = sCommandToExec.getActionInfo();

        /* postivite value = front , negative = back */
        double frontValue = (double) sCommandToExec.getSettingValue("frontValue");

        /* postivite value = right , negative = left */
        double rightValue = (double) sCommandToExec.getSettingValue("rightValue");

        double yValue = (double) sCommandToExec.getSettingValue("yValue");

        double velocity = (double) sCommandToExec.getSettingValue("velocity");

        double rotationVertical = (double) sCommandToExec.getSettingValue("angleRotationVertical");
        double rotationHorizontal = (double) sCommandToExec.getSettingValue("angleRotationHorizontal");

        Location eyeLoc = receiver.getEyeLocation();
        Vector front = eyeLoc.getDirection().clone().setY(0).multiply(frontValue);
        Vector right = eyeLoc.getDirection().clone().setY(0).rotateAroundY(270 * Math.PI / 180).multiply(rightValue);
        Vector calcul = front.add(right);

        Location recLoc = receiver.getLocation();
        double newX = recLoc.getX() + calcul.getX();
        double newY = recLoc.getY() + calcul.getY();
        double newZ = recLoc.getZ() + calcul.getZ();
        Location toLaunchLoc = new Location(recLoc.getWorld(), newX, newY, newZ);
        toLaunchLoc.add(0, yValue, 0);
        //SsomarDev.testMsg("x: "+newX+" y: "+newY+" z: "+newZ);

        try {
            Projectile entity = null;
            String type = (String) sCommandToExec.getSettingValue("projectile");
            Optional<SProjectile> projectileOptional = null;
            SProjectile projectile = null;
            if (SProjectileType.getProjectilesClasses().containsKey(type)) {
                entity = (Projectile) recLoc.getWorld().spawn(toLaunchLoc, SProjectileType.getProjectilesClasses().get(type));
            } else if ((projectileOptional = SProjectilesManager.getInstance().getLoadedObjectWithID(type)).isPresent()) {
                projectile = projectileOptional.get();
                entity = (Projectile) recLoc.getWorld().spawn(toLaunchLoc, SProjectileType.getProjectilesClasses().get(projectile.getType().getValue().get().getValidNames()[0]));
                projectile.transformTheProjectile(entity, receiver, projectile.getType().getValue().get().getMaterial());
            } else entity = recLoc.getWorld().spawn(toLaunchLoc, Arrow.class);

            //	SsomarDev.testMsg("null entity: " + (entity==null));

            if (entity != null) {
                entity.setShooter(receiver);
                Vector v = null;
                Location loc = null;
                boolean searchBlockOrEntity = true;

                /* rotation part */
                Location eyeLoc2 = receiver.getEyeLocation();
                float pitch = eyeLoc2.getPitch();
                float yaw = eyeLoc2.getYaw();
                //SsomarDev.testMsg( "pitch: " + pitch + " yaw: " + yaw);
                float newPitch = (float) (pitch + rotationHorizontal);
                float newYaw = (float) (yaw + rotationVertical);
                if (newPitch > 90) newPitch = 90;
                if (newPitch < -90) {
                    newPitch = newPitch + 90;
                    newPitch = newPitch * -1;
                    newPitch = -90 + newPitch;
                    if (newYaw > 0) {
                        newYaw = -180 + newYaw;
                    } else if (newYaw < 0) {
                        newYaw = 180 + newYaw;
                    } else newYaw = 0;
                }
                //SsomarDev.testMsg( "NEW pitch: " + newPitch + " yaw: " + newYaw);
                eyeLoc2.setPitch(newPitch);
                eyeLoc2.setYaw(newYaw);


                /* idk why I coded this part
                int multiply = 2;

                while (searchBlockOrEntity && multiply < 100) {
                    v = eyeLoc2.getDirection().clone();
                    v = v.multiply(multiply);

                    loc = new Location(receiver.getWorld(), eyeLoc2.getX() + v.getX(), eyeLoc2.getY() + v.getY(), eyeLoc2.getZ() + v.getZ());
                    if (!loc.getBlock().isEmpty()) {
                        searchBlockOrEntity = false;
                    } else {
                        for (Entity e : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
                            if (e.isOnGround()) {
                                searchBlockOrEntity = false;
                                break;
                            }
                        }
                    }
                    multiply++;
                } */

                Vector last = loc.toVector().subtract(toLaunchLoc.toVector());
                last = last.normalize();
                last = last.multiply(velocity);


                if (!SCore.is1v13Less()) {
                    if (entity instanceof Fireball) {
                        Fireball fireball = (Fireball) entity;
                        fireball.setDirection(last);
                    } else if (entity instanceof DragonFireball) {
                        DragonFireball fireball = (DragonFireball) entity;
                        fireball.setDirection(last);
                    } else {
                        entity.setVelocity(last);
                    }
                }

                if (projectile != null) {
                    projectile.transformTheProjectile(entity, receiver, projectile.getType().getValue().get().getMaterial());
                }

                if (SCore.hasExecutableItems && aInfo.getExecutableItem() != null) {
                    ProjectileInfo pInfo = new ProjectileInfo(receiver, entity.getUniqueId(), Optional.ofNullable(aInfo.getExecutableItem()), aInfo.getSlot(), System.currentTimeMillis());
                    ProjectilesHandler.getInstance().addProjectileInfo(pInfo);
                }

                PlayerCustomLaunchEntityEvent playerCustomLaunchProjectileEvent = new PlayerCustomLaunchEntityEvent(receiver, entity);
                Bukkit.getServer().getPluginManager().callEvent(playerCustomLaunchProjectileEvent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("LOCATED_LAUNCH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "LOCATED_LAUNCH projectileType:ARROW frontValue=0 rightValue=0 yValue=0 velocity=1 angleRotationVertical:0 angleRotationHorizontal:0";
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
