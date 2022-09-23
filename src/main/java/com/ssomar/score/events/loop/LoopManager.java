package com.ssomar.score.events.loop;

import com.ssomar.executableblocks.events.EntityWalkOnEvent;
import com.ssomar.executableblocks.executableblocks.activators.ActivatorEBFeature;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableitems.executableitems.activators.ActivatorEIFeature;
import com.ssomar.executableitems.executableitems.activators.Option;
import com.ssomar.score.SCore;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.sobject.sactivator.EventInfo;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;


public class LoopManager {

    public static final int DELAY = 5;
    private static LoopManager instance;
    @Getter
    private final Map<NewSActivator, Integer> loopActivators;
    private final List<NewSActivator> loopActivatorsToAdd;
    private final List<NewSActivator> loopActivatorsToRemove;

    @Getter
    private final List<NewSActivator> checkEntityOnofEB;

    public LoopManager() {
        loopActivators = new HashMap<>();
        loopActivatorsToAdd = new ArrayList<>();
        loopActivatorsToRemove = new ArrayList<>();
        checkEntityOnofEB = new ArrayList<>();
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
                List<NewSActivator> toActiv = new ArrayList<>();

                List<NewSActivator> toRemoveList = new ArrayList<>();
                toRemoveList.addAll(loopActivatorsToAdd);
                toRemoveList.addAll(loopActivatorsToRemove);

                Iterator<NewSActivator> it1 = loopActivators.keySet().iterator();
                while (it1.hasNext()) {
                    NewSActivator activator = it1.next();

                   NewSActivator needRemove = null;
                    for(NewSActivator toRemove1 : toRemoveList) {
                        if(activator.isEqualsOrAClone(toRemove1)) {
                            needRemove = toRemove1;
                            it1.remove();
                        }
                        if(needRemove != null) break;
                    }
                    if(needRemove != null){
                        toRemoveList.remove(needRemove);
                        loopActivatorsToRemove.remove(needRemove);
                        continue;
                    }

                    LoopFeatures loop = null;
                    for (Object feature : activator.getFeatures()) {
                        if (feature instanceof LoopFeatures) {
                            loop = (LoopFeatures) feature;
                        }
                    }
                    if (loop == null) {
                        //SsomarDev.testMsg("Loop not found for activator " + activator.getId());
                        continue;
                    }

                    int delay;
                    if ((delay = loopActivators.get(activator)) > 0) {
                        int toRemove = LoopManager.DELAY;
                        if (delay <= toRemove) toRemove = delay;
                        loopActivators.put(activator, delay - toRemove);
                        continue;
                    } else {
                        toActiv.add(activator);
                        //SsomarDev.testMsg("LOOP > "+loop.getDelay().getValue().get());
                        if (loop.getDelayInTick().getValue())
                            loopActivators.put(activator, loop.getDelay().getValue().get());
                        else loopActivators.put(activator, loop.getDelay().getValue().get() * 20);
                    }
                }

                for(NewSActivator activator : loopActivatorsToAdd) {
                    loopActivators.put(activator, 0);
                }
                loopActivatorsToAdd.clear();

                //SsomarDev.testMsg("To activ: " + toActiv.size());
                if (!toActiv.isEmpty() || !checkEntityOnofEB.isEmpty()) {

                    if (SCore.hasExecutableItems) {
                        try {
                            List<ActivatorEIFeature> listEI = new ArrayList<>();

                            for (NewSActivator sActivator : toActiv) {
                                if (sActivator instanceof ActivatorEIFeature) {
                                    listEI.add((ActivatorEIFeature) sActivator);
                                }
                            }

                            if (!listEI.isEmpty()) {
                                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                                    LoopEvent e = new LoopEvent();

                                    EventInfo eInfo = new EventInfo(e);
                                    eInfo.setPlayer(Optional.of(player));
                                    com.ssomar.executableitems.events.EventsManager.getInstance().activeOption(Option.LOOP, eInfo, listEI);
                                }
                            }
                        } catch (Exception | Error ignored) {
                        }

                    }

                    if (SCore.hasExecutableBlocks) {
                        //SsomarDev.testMsg("Checking EB on entity >> " + checkEntityOnofEB.size());
                        List<ActivatorEBFeature> listEB = new ArrayList<>();
                        for (NewSActivator sActivator : checkEntityOnofEB) {
                            if (sActivator instanceof ActivatorEBFeature) {
                                listEB.add((ActivatorEBFeature) sActivator);
                            }
                        }

                        for (NewSActivator sActivator : toActiv) {
                            if (sActivator instanceof ActivatorEBFeature) {
                                listEB.add((ActivatorEBFeature) sActivator);
                            }
                        }

                        if (!checkEntityOnofEB.isEmpty() || !listEB.isEmpty()) {
                            Map<Location, ExecutableBlockPlacedInterface> mapEBP = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getAllExecutableBlocksPlaced();
                            for (Location loc : mapEBP.keySet()) {
                                if (!loc.isWorldLoaded() || !loc.getWorld().isChunkLoaded(loc.getBlockX() / 16, loc.getBlockZ() / 16))
                                    continue;
                                ExecutableBlockPlaced eBP = (ExecutableBlockPlaced) mapEBP.get(loc);

                                //SsomarDev.testMsg(" hasentityon: " + eBP.hasEntityOn());
                                if (!checkEntityOnofEB.isEmpty() && eBP.hasEntityOn()) {
                                    //SsomarDev.testMsg("RUN ENTITY ON");
                                    runEntityOnEb(loc, eBP, listEB);
                                }
                                //SsomarDev.testMsg(" has loop eb ? "+eBP.hasLoop());
                                if (!listEB.isEmpty() && eBP.hasLoop()) {
                                    runLoopEB(eBP, listEB);
                                }
                            }
                        }
                    }
                }
            }
        };
        runnable.runTaskTimer(SCore.plugin, 0L, DELAY);
    }

    public void runEntityOnEb(Location loc, ExecutableBlockPlaced eBP, List<ActivatorEBFeature> listEB) {
        Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
            @Override
            public void run() {
                Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 0.2, 1, 0.2);
                if (!entities.isEmpty()) {
                    for (Entity ent : entities) {

                        if (ent instanceof LivingEntity && !(ent instanceof Player)) {
                            LivingEntity lE = (LivingEntity) ent;
                            Vector v = lE.getVelocity();
                            if (v.getX() != 0 || v.getZ() != 0) {
                                EntityWalkOnEvent e = new EntityWalkOnEvent();
                                EventInfo eInfo = new EventInfo(e);
                                eInfo.setTargetEntity(Optional.of(ent));
                                com.ssomar.executableblocks.events.EventsManager.getInstance().activeOption(com.ssomar.executableblocks.executableblocks.activators.Option.ENTITY_WALK_ON, eBP, eInfo, listEB);
                            }
                        }
                    }
                }
            }
        });
    }

    public void addLoopActivator(NewSActivator activator) {
        if (activator == null) return;

        loopActivatorsToAdd.add(activator);
    }

    public void removeLoopActivator(NewSActivator activator) {
        if (activator == null) return;

        loopActivatorsToRemove.add(activator);
    }

    public void runLoopEB(ExecutableBlockPlaced eBP, List<ActivatorEBFeature> listEB) {
        LoopEvent e = new LoopEvent();
        EventInfo eInfo = new EventInfo(e);
        com.ssomar.executableblocks.events.EventsManager.getInstance().activeOption(com.ssomar.executableblocks.executableblocks.activators.Option.LOOP, eBP, eInfo, listEB);
    }

    public void resetLoopActivatorsEB() {
        List<NewSActivator> toRemove = new ArrayList<>();
        for (NewSActivator sAct : loopActivators.keySet()) {
            if ((sAct instanceof ActivatorEBFeature)) toRemove.add(sAct);
        }
        for (NewSActivator sAct : toRemove) loopActivators.remove(sAct);
    }

    public void resetLoopActivatorsEI() {
        List<NewSActivator> toRemove = new ArrayList<>();
        for (NewSActivator sAct : loopActivators.keySet()) {
            if ((sAct instanceof ActivatorEIFeature)) toRemove.add(sAct);
        }
        for (NewSActivator sAct : toRemove) loopActivators.remove(sAct);
    }
}
