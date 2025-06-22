package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import org.bukkit.Input;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerRideOnEntityManager implements Listener {

    private static PlayerRideOnEntityManager instance;

    private Map<UUID, Double> riders;

    private List<UUID> forwardRiders;

    public PlayerRideOnEntityManager() {
        this.riders = new HashMap<>();
        this.forwardRiders = new java.util.ArrayList<>();
        loopForwardRiders();
    }

    public void addRider(UUID uuid, double speed) {
        if (!riders.containsKey(uuid)) {
            riders.put(uuid, speed);
        }
    }

    @EventHandler
    public void onPlayerInputEvent(PlayerInputEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (riders.containsKey(playerUUID)) {
            Input input = event.getInput();
            if (input.isForward() && !forwardRiders.contains(playerUUID)) forwardRiders.add(playerUUID);
            else forwardRiders.remove(playerUUID);

            if(input.isJump()) {
                makeMountJump(player);
            }
        }
    }

    public void makeMountJump(Player player) {
        Entity mount = player.getVehicle();
        if (mount != null) {
            double jumpHeight = 0.5; // Adjust the jump height as needed
            mount.setVelocity(mount.getVelocity().setY(jumpHeight));
        }
    }

    public void loopForwardRiders() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (UUID uuid : forwardRiders) {
                    Player player = org.bukkit.Bukkit.getPlayer(uuid);
                    if (player != null && player.isOnline()) {
                        Entity mount = player.getVehicle();
                        if (mount != null) {
                            Location loc = player.getLocation();
                            //Location mountLoc = mount.getLocation();
                            mount.setRotation(loc.getYaw(), loc.getPitch());

                            double speed = 0.4*riders.getOrDefault(uuid, 1.0); // Default speed is 1.0 if not set
                            double x = -Math.sin(Math.toRadians(loc.getYaw())) * speed;
                            double z = Math.cos(Math.toRadians(loc.getYaw())) * speed;
                            mount.setVelocity(mount.getVelocity().setX(x).setZ(z));
                        }
                        else {
                            forwardRiders.remove(uuid);
                            riders.remove(uuid);
                        }
                    }
                }
            }
        };
        SCore.schedulerHook.runAsyncRepeatingTask(
                runnable,
                0L, // initial delay
                1L // repeat every tick
        );
    }

    public static PlayerRideOnEntityManager getInstance() {
        if (instance == null) {
            instance = new PlayerRideOnEntityManager();
        }
        return instance;
    }

}
