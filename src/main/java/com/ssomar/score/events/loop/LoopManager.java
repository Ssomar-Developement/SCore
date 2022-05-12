package com.ssomar.score.events.loop;

import com.ssomar.executableblocks.blocks.activators.ActivatorEB;
import com.ssomar.executableblocks.blocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableitems.executableitems.activators.ActivatorEI;
import com.ssomar.executableitems.executableitems.activators.Option;
import com.ssomar.score.SCore;
import com.ssomar.score.api.executableblocks.ExecutableBlocksAPI;
import com.ssomar.score.api.executableblocks.placed.ExecutableBlockPlacedInterface;
import com.ssomar.score.events.EntityWalkOnEvent;
import com.ssomar.score.sobject.sactivator.EventInfo;
import com.ssomar.score.sobject.sactivator.SActivator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class LoopManager {

	private static LoopManager instance;

	public static final int DELAY = 5;

	private Map<SActivator, Integer> loopActivators = new HashMap<>();

	public void setup() {
		this.runLoop();
	}


	public void runLoop() {
		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				List<SActivator> toActiv = new ArrayList<>();
				for(SActivator activator: loopActivators.keySet()) {
					int delay;
					if((delay = loopActivators.get(activator)) != 0) {
						int toRemove = LoopManager.DELAY;
						if(delay <= toRemove) toRemove = delay;	
						loopActivators.put(activator, delay-toRemove);
						continue;
					}
					else {
						toActiv.add(activator);
						if(activator.isDelayInTick()) loopActivators.put(activator, activator.getDelay());
						else loopActivators.put(activator, activator.getDelay()*20);
					}
				}
				if(!toActiv.isEmpty()) {

					if(SCore.hasExecutableItems) {	
						List<ActivatorEI> listEI = new ArrayList<>();

						for(SActivator sActivator : toActiv) {
							if(sActivator instanceof ActivatorEI) {
								listEI.add((ActivatorEI) sActivator);
							}
						}

						if(!listEI.isEmpty()) {
							for(Player player : Bukkit.getServer().getOnlinePlayers()) {
								LoopEvent e = new LoopEvent();

								EventInfo eInfo = new EventInfo(e);
								eInfo.setPlayer(Optional.of(player));
								com.ssomar.executableitems.events.EventsManager.getInstance().activeOption(Option.LOOP, eInfo, listEI);
							}
						}

					}

					if(SCore.hasExecutableBlocks) {	
						List<ActivatorEB> listEB = new ArrayList<>();

						for(SActivator sActivator : toActiv) {
							if(sActivator instanceof ActivatorEB) {
								listEB.add((ActivatorEB) sActivator);
							}
						}

						if(!listEB.isEmpty()) {
							Map<Location, ExecutableBlockPlacedInterface> mapEBP = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getAllExecutableBlocksPlaced();
							for(Location loc : mapEBP.keySet()) {
								if(!loc.isWorldLoaded() || !loc.getWorld().isChunkLoaded(loc.getBlockX()/16, loc.getBlockZ()/16)) continue;
								ExecutableBlockPlaced eBP = (ExecutableBlockPlaced) mapEBP.get(loc);

								if(eBP.hasEntityOn()) {
									Bukkit.getScheduler().runTask(SCore.plugin, new Runnable() {
										@Override
										public void run() {
											Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 0.2, 1, 0.2);
											if(!entities.isEmpty()) {
												for(Entity ent : entities) {

													if(ent instanceof LivingEntity && !(ent instanceof Player)) {
														LivingEntity lE = (LivingEntity)ent;
														Vector v = lE.getVelocity();
														if(v.getX() != 0 || v.getZ() != 0) {
															EntityWalkOnEvent e = new EntityWalkOnEvent();
															EventInfo eInfo = new EventInfo(e);
															eInfo.setTargetEntity(Optional.of(ent));
															com.ssomar.executableblocks.events.EventsManager.getInstance().activeOption(com.ssomar.executableblocks.blocks.activators.Option.ENTITY_WALK_ON, eBP, eInfo, listEB);
														}
													}
												}
											}
										}
									});
								}
								if(eBP.hasLoop()) {
									LoopEvent e = new LoopEvent();
									EventInfo eInfo = new EventInfo(e);
									com.ssomar.executableblocks.events.EventsManager.getInstance().activeOption(com.ssomar.executableblocks.blocks.activators.Option.LOOP, eBP, eInfo, listEB);
								}
							}
						}
					}
				}
			}
		};
		runnable.runTaskTimer(SCore.plugin, 0L, DELAY);
	}


	public void addLoopActivators(ActivatorEI activator) {
		loopActivators.put(activator, activator.getDelay());
	}

	public void resetLoopActivatorsEB() {
		Map<SActivator, Integer> update = new HashMap<>();
		for(SActivator sAct : loopActivators.keySet()) {
			if(!(sAct instanceof ActivatorEB)) update.put(sAct, loopActivators.get(sAct));
		}
		loopActivators = update;
	}

	public void resetLoopActivatorsEI() {
		Map<SActivator, Integer> update = new HashMap<>();
		for(SActivator sAct : loopActivators.keySet()) {
			if(!(sAct instanceof ActivatorEI)) update.put(sAct, loopActivators.get(sAct));
		}
		loopActivators = update;
	}

	public static LoopManager getInstance() {
		if(instance == null)  instance = new LoopManager();
		return instance;
	}

	public Map<SActivator, Integer> getLoopActivators() {
		return loopActivators;
	}

	public void setLoopActivators(Map<SActivator, Integer> loopActivators) {
		this.loopActivators = loopActivators;
	}
}
