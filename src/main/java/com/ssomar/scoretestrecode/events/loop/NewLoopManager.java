package com.ssomar.scoretestrecode.events.loop;

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
import com.ssomar.scoretestrecode.features.custom.activators.activator.NewSActivator;
import com.ssomar.scoretestrecode.features.custom.activators.activator.NewSActivatorWithLoopFeature;
import com.ssomar.scoretestrecode.features.custom.loop.LoopFeatures;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class NewLoopManager {

	private static NewLoopManager instance;

	public static final int DELAY = 5;

	@Getter@Setter
	private Map<NewSActivator, Integer> loopActivators = new HashMap<>();

	public void setup() {
		this.runLoop();
	}


	public void runLoop() {
		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {

				List<NewSActivator> toActiv = new ArrayList<>();
				for(NewSActivator activator: loopActivators.keySet()) {
					NewSActivatorWithLoopFeature loopAct = (NewSActivatorWithLoopFeature)activator;
					LoopFeatures features = loopAct.getLoopFeatures();
					int delay;
					if((delay = loopActivators.get(activator)) != 0) {
						int toRemove = NewLoopManager.DELAY;
						if(delay <= toRemove) toRemove = delay;	
						loopActivators.put(activator, delay-toRemove);
						continue;
					}
					else {
						toActiv.add(activator);
						if(features.getDelayInTick().getValue()) loopActivators.put(activator, features.getDelay().getValue().get());
						else loopActivators.put(activator, features.getDelay().getValue().get()*20);
					}
				}
				if(!toActiv.isEmpty()) {

					if(SCore.hasExecutableItems) {	
						List<ActivatorEI> listEI = new ArrayList<>();

						/* // TODO for(NewSActivator sActivator : toActiv) {
							if(sActivator instanceof ActivatorEI) {
								listEI.add((ActivatorEI) sActivator);
							}
						}*/

						if(!listEI.isEmpty()) {
							for(Player player : Bukkit.getServer().getOnlinePlayers()) {
								NewLoopEvent e = new NewLoopEvent();

								EventInfo eInfo = new EventInfo(e);
								eInfo.setPlayer(Optional.of(player));
								com.ssomar.executableitems.events.EventsManager.getInstance().activeOption(Option.LOOP, eInfo, listEI);
							}
						}

					}

					if(SCore.hasExecutableBlocks) {	
						List<ActivatorEB> listEB = new ArrayList<>();

						/* // TODO for(NewSActivator sActivator : toActiv) {
							if(sActivator instanceof ActivatorEB) {
								listEB.add((ActivatorEB) sActivator);
							}
						} */

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
									NewLoopEvent e = new NewLoopEvent();
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
		//loopActivators.put(activator, activator.getDelay());
	}

	public void resetLoopActivatorsEB() {
		Map<SActivator, Integer> update = new HashMap<>();
		/* // TODO for(NewSActivator sAct : loopActivators.keySet()) {
			if(!(sAct instanceof ActivatorEB)) update.put(sAct, loopActivators.get(sAct));
		}
		loopActivators = update;*/
	}

	public void resetLoopActivatorsEI() {
		Map<SActivator, Integer> update = new HashMap<>();
		/* // TODO for(SActivator sAct : loopActivators.keySet()) {
			if(!(sAct instanceof ActivatorEI)) update.put(sAct, loopActivators.get(sAct));
		}
		loopActivators = update; */
	}

	public static NewLoopManager getInstance() {
		if(instance == null)  instance = new NewLoopManager();
		return instance;
	}
}
