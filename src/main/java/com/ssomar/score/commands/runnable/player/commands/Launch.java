package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.PlayerCustomLaunchEntityEvent;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.SProjectileType;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// MUST BE SYNC -> that why runnable , Otherwise it will not run in WHILE

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class Launch extends PlayerCommand {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

            double rotationVertical = 0;
            double rotationHorizontal = 0;

            if (args.size() == 0) {
                receiver.launchProjectile(Arrow.class);
            } else {
                try {
                    rotationVertical = Double.parseDouble(args.get(1));
                } catch (Exception ignored) {
                }

                try {
                    rotationHorizontal = Double.parseDouble(args.get(2)) * -1;
                } catch (Exception ignored) {
                }
                try {
                    Entity entity = null;

                    receiver.setMetadata("cancelProjectileEvent", new FixedMetadataValue(SCore.plugin, 7772));

                    double velocity = 1;
                    String type = args.get(0);
                    Optional<SProjectile> projectileOptional = null;
                    SProjectile projectile = null;
                    if (SProjectileType.getProjectilesClasses().containsKey(type.toUpperCase())) {
                        entity = receiver.launchProjectile(SProjectileType.getProjectilesClasses().get(type.toUpperCase()));
                    } else if ((projectileOptional = SProjectilesManager.getInstance().getLoadedObjectWithID(type)).isPresent()) {
                        projectile = projectileOptional.get();
                        //SsomarDev.testMsg("LAUNCH : "+projectile.hashCode());
                        entity = receiver.launchProjectile(SProjectileType.getProjectilesClasses().get(projectile.getType().getValue().get().getValidNames()[0]));
                    } else entity = receiver.launchProjectile(Arrow.class);

                    if (entity instanceof Firework) {
                        entity.remove();
                        EntityType fireworkType =  SCore.is1v20v5Plus() ? EntityType.FIREWORK_ROCKET : EntityType.valueOf("FIREWORK");
                        entity = receiver.getWorld().spawnEntity(receiver.getEyeLocation(), fireworkType);
                        Firework firework = (Firework) entity;
                        firework.setShotAtAngle(true);
                    }

                    if (entity != null) {
                        if (!SCore.is1v13Less()) {

                            Location loc = receiver.getEyeLocation();
                            float pitch = loc.getPitch();
                            float yaw = loc.getYaw();
                            //SsomarDev.testMsg( "pitch: " + pitch + " yaw: " + yaw);
                            float newPitch = (float) (pitch + rotationHorizontal);
                            float newYaw = (float) (yaw + rotationVertical);
                            if (newPitch > 90) newPitch = 90;
                            if (newPitch < -90 && rotationVertical != 0) {
                                newPitch = newPitch + 90;
                                newPitch = newPitch * -1;
                                newPitch = -90 + newPitch;
                                if (newYaw > 0) {
                                    newYaw = -180 + newYaw;
                                } else if (newYaw < 0) {
                                    newYaw = 180 + newYaw;
                                } else newYaw = 0;
                            }
                            //SsomarDev.testMsg( "NEW pitch: " + newPitch + " yaw: " + newYaw, true);
                            loc.setPitch(newPitch);
                            loc.setYaw(newYaw);

                            Vector eyeVector;
                            /* Here I take the velocity. It is present only if the command LAUNCH is activated in a projectile launch event
                             * it's used to keep the bowforce and let the user customize the projectile launched */
                            if (aInfo.getVelocity().isPresent()) {
                                /* Take the real velocity */
                                eyeVector = aInfo.getVelocity().get();
                                double oldVelocity = eyeVector.length();
                                //SsomarDev.testMsg( "velocity: " + eyeVector.length(), true);
                                /* add to the real velocity the custom rotation */
                                Location customLoc = eyeVector.toLocation(receiver.getWorld());
                                customLoc.setPitch(newPitch);
                                customLoc.setYaw(newYaw);
                                eyeVector = customLoc.getDirection();
                                eyeVector = eyeVector.multiply(oldVelocity);

                            } else eyeVector = loc.getDirection();


                            Vector v;
                            if (entity instanceof Fireball) {
                                Fireball fireball = (Fireball) entity;
                                fireball.setDirection(eyeVector);
                            } else if (entity instanceof DragonFireball) {
                                DragonFireball fireball = (DragonFireball) entity;
                                fireball.setDirection(eyeVector);
                            } else {
                                entity.setVelocity(eyeVector);
                            }
                        }
                        if (projectile != null) {
                            Material mat = projectile.getType().getValue().get().getMaterial();
                            /* Fix for potion otherwise the method to getItem is null idk why, (set an item + potionmeta) (it works only on 1.12 plus because setcolor doesnt exists in 1.11)*/
                            if (entity instanceof ThrownPotion) {
                                ThrownPotion lp = (ThrownPotion) entity;
                                ItemStack item = new ItemStack(mat, 1);
                                PotionMeta pMeta = (PotionMeta) item.getItemMeta();
                                pMeta.setColor(Color.AQUA);
                                item.setItemMeta(pMeta);
                                lp.setItem(item);
                            }
                            projectile.transformTheProjectile(entity, receiver, mat);
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
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";
        String launch = "LAUNCH {projectileType} [angle rotation vertical] [angle rotation horizontal]";
        if (args.size() < 1) error = notEnoughArgs + launch;

        return error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("LAUNCH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "LAUNCH {projectileType} [angle rotation y] [angle rotation z]";
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
