package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.data.AbsorptionQuery;
import com.ssomar.score.data.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class AbsorptionManager {

    public static AbsorptionManager instance;
    //private List<AbsorptionObject> absorptionList;

    public AbsorptionManager() {
        instance = this;
        //absorptionList = new java.util.ArrayList<>();
    }

    public AbsorptionObject applyAbsorption(AbsorptionObject absorption) {
        return applyAbsorption(absorption, false);
    }

    private AbsorptionObject applyAbsorption(AbsorptionObject absorption, boolean isReschedule) {
        SsomarDev.testMsg(ChatColor.GOLD+"[#s0014] AbsorptionManager.applyAbsorption() is triggered (isReschedule: "+isReschedule+")", true);

        // needs further explanation since this method is empty -Special70
        if(absorption.getTask() != null) absorption.getTask().cancel();

        Player receiver = SCore.plugin.getServer().getPlayer(absorption.getPlayerUUID());
        if(receiver == null) return absorption;

        double currentabsorption = receiver.getAbsorptionAmount();

        long remainingTimeTiksTime = absorption.getExpiryTime() - System.currentTimeMillis();
        remainingTimeTiksTime = remainingTimeTiksTime / 50;

        SsomarDev.testMsg("currentAbso: "+currentabsorption+" abso to add "+absorption.getAbsorption(), true);
        receiver.setAbsorptionAmount(currentabsorption + absorption.getAbsorption());
        SsomarDev.testMsg("newAbso: "+receiver.getAbsorptionAmount(), true);

        // Get or create a unique identification for this absorption
        UUID absorptionUUID;
        if (absorption.getAbsorptionUUID() != null) {
            // Already has UUID (rescheduling after reconnect)
            absorptionUUID = absorption.getAbsorptionUUID();
        } else {
            // New absorption, create UUID
            absorptionUUID = UUID.randomUUID();
            absorption.setAbsorptionUUID(absorptionUUID);
        }

        // Save to database immediately so it persists across logouts (but only if not rescheduling)
        if (!isReschedule) {
            SCore.schedulerHook.runAsyncTask(() -> AbsorptionQuery.insertToRecords(Database.getInstance().connect(), absorptionUUID, receiver.getUniqueId(), absorption.getAbsorption(), absorption.getExpiryTime()),0);

        }

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " ABSORPTION: " + absorption.getAbsorption(), true);
                if (!receiver.isDead() || receiver.isOnline()) {
                    try {
                        //receiver.setAbsorptionAmount(receiver.getAbsorptionAmount()-absorption.getAbsorption());
                        AbsorptionManager.modifyAbsorption(receiver.getUniqueId().toString(), receiver.getAbsorptionAmount()-absorption.getAbsorption());
                        // Remove from database since the task completed successfully
                        SCore.schedulerHook.runAsyncTask(()->AbsorptionQuery.deleteAbsorption(Database.getInstance().connect(), absorptionUUID.toString()),0);
                    }catch(IllegalArgumentException e){
                        //I don't know how to add a debug message, but this happens if the player tries to remove ABSORPTION
                        //like ABSORPTION -5, if the player has ABSORPTION 5, it will work, but once it worked now the player
                        //has ABSORPTION 0, if he tries again, error
                    }
                }
                // If player is dead, keep the record in database so it can be handled on rejoin
                //absorptionList.remove(absorption);
            }
        };
        // we are doing runTask instead of runEntityTask because we'd want the absorption to persist even after relogs
        absorption.setTask(SCore.schedulerHook.runTask(runnable3, remainingTimeTiksTime));
        return absorption;
    }

    public void onConnect(Player player) {
        SsomarDev.testMsg(ChatColor.GOLD+"[#s0015] AbsorptionManager.onConnect() is triggered", true);

        UUID playerUUID = player.getUniqueId();

        // Run database query async
        Runnable runnableAsync = new Runnable() {
            @Override
            public void run() {
                // Handle expired absorptions - remove them from player
                List<AbsorptionObject> toRemove = AbsorptionQuery.getAbsorptionsToRemove(Database.getInstance().connect(), player.getUniqueId().toString());

                SsomarDev.testMsg("[#s0016] Amount of expired absorptions: "+toRemove.size(), true);

                // Sync back to main thread to modify player
                Runnable runnableSync = new Runnable() {
                    @Override
                    public void run() {

                        // Re-fetch player in case they disconnected
                        Player getBackPlayer = Bukkit.getPlayer(playerUUID);
                        if (getBackPlayer == null || !getBackPlayer.isOnline()) return;

                        for(AbsorptionObject absorption : toRemove) {
                            if (getBackPlayer.getAbsorptionAmount() > absorption.getAbsorption())
                                getBackPlayer.setAbsorptionAmount(getBackPlayer.getAbsorptionAmount() - absorption.getAbsorption());
                            else if (getBackPlayer.getAbsorptionAmount() == 0) {
                                break;
                            } else {
                                getBackPlayer.setAbsorptionAmount(0);
                            }
                        }

                        // Delete AFTER successful removal from player - run async to not block main thread
                        SCore.schedulerHook.runAsyncTask(() -> {
                            for (AbsorptionObject absorption : toRemove) {
                                AbsorptionQuery.deleteAbsorption(Database.getInstance().connect(), absorption.getAbsorptionUUID().toString());
                            }
                        }, 0);
                    }
                };
                SCore.schedulerHook.runTask(runnableSync, 0);
            }
        };
        SCore.schedulerHook.runAsyncTask(runnableAsync, 0);
    }

    public static AbsorptionManager getInstance() {
        if (instance == null) instance = new AbsorptionManager();
        return instance;
    }

    /**
     * This method's purpose is to safely modify a player's absorption value because complications
     * occur when a player relogs.
     * @param player_uuid
     * @param new_value
     */
    private static void modifyAbsorption(String player_uuid, double new_value) {
        Player player = Bukkit.getPlayer(UUID.fromString(player_uuid));
        assert player != null;
        player.setAbsorptionAmount(new_value);
    }

}
