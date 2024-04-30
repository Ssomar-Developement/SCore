package com.ssomar.score.events.loop;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.custom.activators.activator.SActivator;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.OptionGlobal;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


public class LoopManager {

    public int DELAY = 5;
    private static LoopManager instance;
    @Getter
    private final Map<SActivator, Integer> loopActivators;
    private final List<SActivator> loopActivatorsToAdd;
    private final List<SActivator> loopActivatorsToRemove;

    public LoopManager() {
        DELAY = 5;
        if(GeneralConfig.getInstance().isLoopKillMode()) DELAY = 1;

        loopActivators = new HashMap<>();
        loopActivatorsToAdd = new ArrayList<>();
        loopActivatorsToRemove = new ArrayList<>();
        this.runLoop();
    }

    public static LoopManager getInstance() {
        if (instance == null) instance = new LoopManager();
        return instance;
    }

    public void runLoop() {
        //SsomarDev.testMsg("LoopManager.runLoop() >> "+ loopActivators.size());
        BukkitRunnable runnable = new BukkitRunnable() {

            @Override
            public void run() {
                //SsomarDev.testMsg("loop activatores registered: " + loopActivators.size());
                /* List of activators that we need to activate for this cycle */
                List<SActivator> toActivate = new ArrayList<>();

                /* The activator to remove for the LOOP CYCLE */
                List<SActivator> toRemoveList = new ArrayList<>();
                /* Remove the old version of the activators who want to add */
                toRemoveList.addAll(loopActivatorsToAdd);
                /* Remove the activators who want to remove */
                toRemoveList.addAll(loopActivatorsToRemove);

                /* Remove the activators selected before and extract the activators to enable */
                Iterator<SActivator> it1 = loopActivators.keySet().iterator();
                while (it1.hasNext()) {
                    SActivator activator = it1.next();

                    SActivator needRemove = null;
                    for (SActivator toRemove1 : toRemoveList) {
                        if (activator.isEqualsOrAClone(toRemove1)) {
                            needRemove = toRemove1;
                            it1.remove();
                        }
                        if (needRemove != null) break;
                    }
                    if (needRemove != null) {
                        toRemoveList.remove(needRemove);
                        loopActivatorsToRemove.remove(needRemove);
                        continue;
                    }

                    LoopFeatures loop;
                    if ((loop = activator.getLoopFeatures()) == null) continue;

                    int delay;
                    if ((delay = loopActivators.get(activator)) > DELAY) {
                        int toRemove = DELAY;
                        if (delay <= toRemove) toRemove = delay;
                        int newDelay = delay - toRemove;
                        //SsomarDev.testMsg("LOOP > " + newDelay+ " for "+activator.getId()+ " TIME "+System.currentTimeMillis(), true);
                        loopActivators.put(activator, newDelay);
                        continue;
                    } else {
                        toActivate.add(activator);
                        //SsomarDev.testMsg("LOOP ACTIVATE > "+activator.getId()+ " TIME "+System.currentTimeMillis(), true);
                        if (loop.getDelayInTick().getValue())
                            loopActivators.put(activator, loop.getDelay().getValue().get());
                        else loopActivators.put(activator, loop.getDelay().getValue().get() * 20);
                    }
                }

                /* Add the new activators */
                for (SActivator activator : loopActivatorsToAdd) {
                    loopActivators.put(activator, 0);
                }
                loopActivatorsToAdd.clear();

                //SsomarDev.testMsg("To activ: " + toActivate.size(), true);
                if (!toActivate.isEmpty()) {

                    List<SActivator> loopActivators = new ArrayList<>();

                    for (SActivator sActivator : toActivate) {
                        if (sActivator.getOption().isLoopOption())
                            loopActivators.add(sActivator);
                    }

                    LoopEvent e = new LoopEvent();
                    EventInfo eInfo = new EventInfo(e);

                    if (!loopActivators.isEmpty()) {
                        while (!loopActivators.isEmpty()) {
                            List<SActivator> extractToActivPerPlugin = loopActivators.get(0).extractActivatorsSameClass(loopActivators);

                            /* for(NewSActivator sAct : extractToActivPerPlugin) {
                                SsomarDev.testMsg("LOOP > "+sAct.getId(), true);
                            }*/

                            extractToActivPerPlugin.get(0).activateOptionGlobal(OptionGlobal.LOOP, eInfo, extractToActivPerPlugin);
                        }
                    }
                }
            }
        };
        SCore.schedulerHook.runRepeatingTask(runnable, 0L, DELAY);
    }

    public void addLoopActivator(SActivator activator) {
        if (activator == null) return;

        loopActivatorsToAdd.add(activator);
    }

    public void removeLoopActivator(SActivator activator) {
        if (activator == null) return;

        loopActivatorsToRemove.add(activator);
    }

    public void resetLoopActivators(SPlugin sPlugin) {
        List<SActivator> toRemove = new ArrayList<>();
        for (SActivator sAct : loopActivators.keySet()) {
            if ((sAct.getSPlugin() == sPlugin)) toRemove.add(sAct);
        }
        for (SActivator sAct : toRemove) loopActivators.remove(sAct);
    }
}
