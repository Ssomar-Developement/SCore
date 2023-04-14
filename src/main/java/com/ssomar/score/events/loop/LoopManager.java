package com.ssomar.score.events.loop;

import com.ssomar.score.SCore;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.OptionGlobal;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


public class LoopManager {

    public static final int DELAY = 5;
    private static LoopManager instance;
    @Getter
    private final Map<NewSActivator, Integer> loopActivators;
    private final List<NewSActivator> loopActivatorsToAdd;
    private final List<NewSActivator> loopActivatorsToRemove;

    public LoopManager() {
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
                List<NewSActivator> toActivate = new ArrayList<>();

                /* The activator to remove for the LOOP CYCLE */
                List<NewSActivator> toRemoveList = new ArrayList<>();
                /* Remove the old version of the activators who want to add */
                toRemoveList.addAll(loopActivatorsToAdd);
                /* Remove the activators who want to remove */
                toRemoveList.addAll(loopActivatorsToRemove);

                /* Remove the activators selected before and extract the activators to enable */
                Iterator<NewSActivator> it1 = loopActivators.keySet().iterator();
                while (it1.hasNext()) {
                    NewSActivator activator = it1.next();

                    NewSActivator needRemove = null;
                    for (NewSActivator toRemove1 : toRemoveList) {
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
                    if ((delay = loopActivators.get(activator)) > 0) {
                        int toRemove = LoopManager.DELAY;
                        if (delay <= toRemove) toRemove = delay;
                        loopActivators.put(activator, delay - toRemove);
                        continue;
                    } else {
                        toActivate.add(activator);
                        //SsomarDev.testMsg("LOOP > "+loop.getDelay().getValue().get());
                        if (loop.getDelayInTick().getValue())
                            loopActivators.put(activator, loop.getDelay().getValue().get());
                        else loopActivators.put(activator, loop.getDelay().getValue().get() * 20);
                    }
                }

                /* Add the new activators */
                for (NewSActivator activator : loopActivatorsToAdd) {
                    loopActivators.put(activator, 0);
                }
                loopActivatorsToAdd.clear();

                //SsomarDev.testMsg("To activ: " + toActivate.size(), true);
                if (!toActivate.isEmpty()) {

                    List<NewSActivator> loopActivators = new ArrayList<>();

                    for (NewSActivator sActivator : toActivate) {
                        if (sActivator.getOption().isLoopOption())
                            loopActivators.add(sActivator);
                    }

                    LoopEvent e = new LoopEvent();
                    EventInfo eInfo = new EventInfo(e);

                    if (!loopActivators.isEmpty()) {
                        while (!loopActivators.isEmpty()) {
                            List<NewSActivator> extractToActivPerPlugin = loopActivators.get(0).extractActivatorsSameClass(loopActivators);

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

    public void addLoopActivator(NewSActivator activator) {
        if (activator == null) return;

        loopActivatorsToAdd.add(activator);
    }

    public void removeLoopActivator(NewSActivator activator) {
        if (activator == null) return;

        loopActivatorsToRemove.add(activator);
    }

    public void resetLoopActivators(SPlugin sPlugin) {
        List<NewSActivator> toRemove = new ArrayList<>();
        for (NewSActivator sAct : loopActivators.keySet()) {
            if ((sAct.getSPlugin() == sPlugin)) toRemove.add(sAct);
        }
        for (NewSActivator sAct : toRemove) loopActivators.remove(sAct);
    }
}
