package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.executableitems.events.projectiles.ProjectileInfo;
import com.ssomar.executableitems.events.projectiles.ProjectilesHandler;
import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.PlayerCustomLaunchEntityEvent;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.features.VelocityFeature;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;

/* LAUNCH {projectileType} */
@SuppressWarnings("deprecation")
public class Launch extends PlayerCommand {

    private Map<String, Class> projectiles;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Launch() {
        projectiles = new HashMap<>();
        try {
            projectiles.put("ARROW", Arrow.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SPECTRALARROW", SpectralArrow.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SPECTRAL_ARROW", SpectralArrow.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("DRAGONFIREBALL", DragonFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("DRAGON_FIREBALL", DragonFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("FIREBALL", Fireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SMALLFIREBALL", SmallFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("LARGEFIREBALL", LargeFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("LARGE_FIREBALL", LargeFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SIZEDFIREBALL", SizedFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SIZED_FIREBALL", SizedFireball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SNOWBALL", Snowball.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("THROWNEXPBOTTLE", ThrownExpBottle.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("WITHERSKULL", WitherSkull.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("WITHER_SKULL", WitherSkull.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("EGG", Egg.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("ENDERPEARL", EnderPearl.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("ENDER_PEARL", EnderPearl.class);
        } catch (Exception | Error e) {
        }

        try {
            projectiles.put("LINGERINGPOTION", LingeringPotion.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("LINGERING_POTION", LingeringPotion.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SPLASHPOTION", SplashPotion.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SPLASH_POTION", SplashPotion.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("LLAMASPIT", LlamaSpit.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("LLAMA_SPIT", LlamaSpit.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SHULKERBULLET", ShulkerBullet.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("SHULKER_BULLET", ShulkerBullet.class);
        } catch (Exception | Error e) {
        }
        try {
            projectiles.put("TRIDENT", Trident.class);
        } catch (Exception | Error e) {
        }
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
                SProjectiles projectile = null;
                if (projectiles.containsKey(type)) {
                    entity = receiver.launchProjectile(projectiles.get(type));
                }
                else if (ProjectilesManager.getInstance().containsProjectileWithID(type)) {
                    projectile = ProjectilesManager.getInstance().getProjectileWithID(type);
                    entity = receiver.launchProjectile(projectiles.get(projectile.getIdentifierType()));
                }
                else entity = receiver.launchProjectile(Arrow.class);

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
                        //SsomarDev.testMsg( "NEW pitch: " + newPitch + " yaw: " + newYaw);
                        loc.setPitch(newPitch);
                        loc.setYaw(newYaw);

                        Vector eyeVector = loc.getDirection();


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
                        projectile.executeTransformTheProjectile(entity, receiver);
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
    public String verify(List<String> args) {
        String error = "";
        String launch = "LAUNCH {projectileType} [angle rotation vertical] [angle rotation horizontal]";
        if (args.size() < 1) error = notEnoughArgs + launch;

        return error;
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
