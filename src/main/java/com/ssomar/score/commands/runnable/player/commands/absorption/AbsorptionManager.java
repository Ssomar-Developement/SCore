package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.data.AbsorptionQuery;
import com.ssomar.score.data.Database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AbsorptionManager {

    public static AbsorptionManager instance;
    //private List<AbsorptionObject> absorptionList;

    public AbsorptionManager() {
        instance = this;
        //absorptionList = new java.util.ArrayList<>();
    }

    public AbsorptionObject applyAbsorption(AbsorptionObject absorption) {
        SsomarDev.testMsg(ChatColor.GOLD+"[#s0014] AbsorptionManager.applyAbsorption() is triggered", true);

        // needs further explanation since this method is empty -Special70
        if(absorption.getTask() != null) absorption.getTask().cancel();

        Player receiver = SCore.plugin.getServer().getPlayer(absorption.getPlayerUUID());
        if(receiver == null) return absorption;

        double currentabsorption = receiver.getAbsorptionAmount();

        long remainingTimeTiksTime = absorption.getExpiryTime() - System.currentTimeMillis();
        remainingTimeTiksTime = remainingTimeTiksTime / 50;

        /* seems redundant because the conditions placed before this method call makes this scenario impossible
        if (remainingTimeTiksTime <= 0){
            absorption.setToRemove(true);
            return absorption;
        }
         */

        SsomarDev.testMsg("currentAbso: "+currentabsorption+" abso to add "+absorption.getAbsorption(), true);
        receiver.setAbsorptionAmount(currentabsorption + absorption.getAbsorption());
        SsomarDev.testMsg("newAbso: "+receiver.getAbsorptionAmount(), true);

        // create a unique identification for this absorption
        UUID absorptionUUID = UUID.randomUUID();

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " ABSORPTION: " + absorption.getAbsorption(), true);
                if (!receiver.isDead()) {
                    try {
                        receiver.setAbsorptionAmount(receiver.getAbsorptionAmount()-absorption.getAbsorption());
                        // at the moment, each absorption custom command call will perform a total of 2 sql queries.
                        // I just could not comprehend how bad it could get at worst so for now, it's going to stay as it is unless
                        // reports complain about it.
                    }catch(IllegalArgumentException e){
                        //I don't know how to add a debug message, but this happens if the player tries to remove ABSORPTION
                        //like ABSORPTION -5, if the player has ABSORPTION 5, it will work, but once it worked now the player
                        //has ABSORPTION 0, if he tries again, error
                    }
                } else {
                    AbsorptionQuery.insertToRecords(Database.getInstance().connect(), absorptionUUID, receiver.getUniqueId(), absorption.getAbsorption(), absorption.getExpiryTime());
                }
                //absorptionList.remove(absorption);
            }
        };
        absorption.setTask(SCore.schedulerHook.runEntityTask(runnable3, null, receiver, remainingTimeTiksTime));
        return absorption;
    }

    public void onConnect(Player player) {
        SsomarDev.testMsg(ChatColor.YELLOW+"[#s0015] AbsorptionManager.onConnect() is triggered", true);
        List<AbsorptionObject> toRemove = AbsorptionQuery.getAbsorptionsToRemove(Database.getInstance().connect(), player.getUniqueId().toString(), null);
        SsomarDev.testMsg(ChatColor.YELLOW+"[#s0016] Amount of rows retrieved: "+toRemove.size(), true);
        for(AbsorptionObject absorption : toRemove) {
            if (player.getAbsorptionAmount() > absorption.getAbsorption())
                player.setAbsorptionAmount(player.getAbsorptionAmount() - absorption.getAbsorption());
            else if (player.getAbsorptionAmount() == 0) {
                break;
            } else {
                player.setAbsorptionAmount(0);
            }
        }
    }

    // this method is not needed anymore
    /*
    public void onDisconnect(Player player) {
        List<AbsorptionObject> toRemove = AbsorptionQuery.getAbsorptionsToRemove(Database.getInstance().connect(), player.getUniqueId().toString());
        SCore.plugin.getLogger().info(String.valueOf(player.getAbsorptionAmount()));
        for(AbsorptionObject absorption : toRemove) {
            player.setAbsorptionAmount(player.getAbsorptionAmount() - absorption.getAbsorption());
        }
    }

     */

    public static AbsorptionManager getInstance() {
        if (instance == null) instance = new AbsorptionManager();
        return instance;
    }

}
