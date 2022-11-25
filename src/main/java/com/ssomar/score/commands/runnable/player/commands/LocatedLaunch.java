package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.PlayerCustomLaunchEntityEvent;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.*;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class LocatedLaunch extends PlayerCommand {

    private final Map<String, Class> projectiles;

    public LocatedLaunch() {
        projectiles = new HashMap<>();
        try {
            projectiles.put("ARROW", Arrow.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPECTRALARROW", SpectralArrow.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPECTRAL_ARROW", SpectralArrow.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("DRAGONFIREBALL", DragonFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("DRAGON_FIREBALL", DragonFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("FIREBALL", Fireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SMALLFIREBALL", SmallFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LARGEFIREBALL", LargeFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LARGE_FIREBALL", LargeFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SIZEDFIREBALL", SizedFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SIZED_FIREBALL", SizedFireball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SNOWBALL", Snowball.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("THROWNEXPBOTTLE", ThrownExpBottle.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("WITHERSKULL", WitherSkull.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("WITHER_SKULL", WitherSkull.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("EGG", Egg.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("ENDERPEARL", EnderPearl.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("ENDER_PEARL", EnderPearl.class);
        } catch (Exception | Error ignored) {
        }

        try {
            projectiles.put("LINGERINGPOTION", LingeringPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LINGERING_POTION", LingeringPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPLASHPOTION", SplashPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SPLASH_POTION", SplashPotion.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LLAMASPIT", LlamaSpit.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("LLAMA_SPIT", LlamaSpit.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SHULKERBULLET", ShulkerBullet.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("SHULKER_BULLET", ShulkerBullet.class);
        } catch (Exception | Error ignored) {
        }
        try {
            projectiles.put("TRIDENT", Trident.class);
        } catch (Exception | Error ignored) {
        }
    }

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        /* postivite value = front , negative = back */
        double frontValue = 0;

        /* postivite value = right , negative = left */
        double rightValue = 0;

        double yValue = 0;

        double velocity = 1;

        double rotationVertical = 0;
        double rotationHorizontal = 0;

        try {
            frontValue = Double.parseDouble(args.get(1));
        } catch (Exception ignored) {
        }

        try {
            rightValue = Double.parseDouble(args.get(2));
        } catch (Exception ignored) {
        }

        try {
            yValue = Double.parseDouble(args.get(3));
        } catch (Exception ignored) {
        }

        try {
            velocity = Double.parseDouble(args.get(4));
        } catch (Exception ignored) {
        }

        try {
            rotationVertical = Double.parseDouble(args.get(5));
        } catch (Exception ignored) {
        }

        try {
            rotationHorizontal = Double.parseDouble(args.get(6)) * -1;
        } catch (Exception ignored) {
        }

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
            String type = args.get(0);
            Optional<SProjectile> projectileOptional = null;
            SProjectile projectile = null;
            if (projectiles.containsKey(type)) {
                entity = (Projectile) recLoc.getWorld().spawn(toLaunchLoc, projectiles.get(type));
            } else if ((projectileOptional = SProjectilesManager.getInstance().getLoadedObjectWithID(type)).isPresent()) {
                projectile = projectileOptional.get();
                entity = (Projectile) recLoc.getWorld().spawn(toLaunchLoc, projectiles.get(projectile.getType().getValue().get().getValidNames()[0]));
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
                }

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
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("LOCATED_LAUNCH");
        return names;
    }

    @Override
    public String getTemplate() {
        return "LOCATED_LAUNCH {projectileType} {frontValue positive=front , negative=back} {rightValue right=positive, negative=left} {yValue} {velocity} [vertical rotation] [horizontal rotation]";
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
