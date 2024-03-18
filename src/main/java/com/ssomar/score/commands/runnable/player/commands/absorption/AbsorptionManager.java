package com.ssomar.score.commands.runnable.player.commands.absorption;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AbsorptionManager {

    public static AbsorptionManager instance;
    private List<AbsorptionObject> absorptionList;

    public AbsorptionManager() {
        instance = this;
        absorptionList = new java.util.ArrayList<>();
    }

    public void addAbsorption(AbsorptionObject absorption) {
        absorptionList.add(absorption);
    }

    public AbsorptionObject applyAbsorption(AbsorptionObject absorption) {

        if(absorption.getTask() != null) absorption.getTask().cancel();

        Player receiver = SCore.plugin.getServer().getPlayer(absorption.getPlayerUUID());
        if(receiver == null) return absorption;

        double currentabsorption = receiver.getAbsorptionAmount();

        long remainingTimeTiksTime = absorption.getTime() - System.currentTimeMillis();
        remainingTimeTiksTime = remainingTimeTiksTime / 50;
        if (remainingTimeTiksTime <= 0){
            absorption.setToRemove(true);
            return absorption;
        }

        SsomarDev.testMsg("currentAbso: "+currentabsorption+" abso to add "+absorption.getAbsorption(), true);
        receiver.setAbsorptionAmount(currentabsorption + absorption.getAbsorption());
        SsomarDev.testMsg("newAbso: "+receiver.getAbsorptionAmount(), true);

        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " ABSORPTION: " + absorption.getAbsorption(), true);
                if (!receiver.isDead()) {
                    try {
                        receiver.setAbsorptionAmount(receiver.getAbsorptionAmount()-absorption.getAbsorption());
                    }catch(IllegalArgumentException e){
                        //I don't know how to add a debug message, but this happens if the player tries to remove ABSORPTION
                        //like ABSORPTION -5, if the player has ABSORPTION 5, it will work, but once it worked now the player
                        //has ABSORPTION 0, if he tries again, error
                    }
                }
                absorptionList.remove(absorption);
            }
        };
        absorption.setTask(SCore.schedulerHook.runEntityTask(runnable3, null, receiver, remainingTimeTiksTime));
        return absorption;
    }

    public void onConnect(Player player) {
        List<AbsorptionObject> toRemove = new java.util.ArrayList<>();
        for(AbsorptionObject absorption : absorptionList) {
            if(absorption.getPlayerUUID().equals(player.getUniqueId())) {
                if(applyAbsorption(absorption).isToRemove()) toRemove.add(absorption);
            }
        }
        absorptionList.removeAll(toRemove);
    }

    public void onDisconnect(Player player) {
        for(AbsorptionObject absorption : absorptionList) {
            if(absorption.getPlayerUUID().equals(player.getUniqueId())) {
                absorption.getTask().cancel();
                player.setAbsorptionAmount(player.getAbsorptionAmount() - absorption.getAbsorption());
            }
        }
    }

    public static AbsorptionManager getInstance() {
        if (instance == null) instance = new AbsorptionManager();
        return instance;
    }

}
