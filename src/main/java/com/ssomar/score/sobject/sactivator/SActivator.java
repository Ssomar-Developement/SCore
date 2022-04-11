package com.ssomar.score.sobject.sactivator;

import java.util.List;

import com.ssomar.sevents.events.projectile.hitentity.ProjectileHitEntityEvent;
import com.ssomar.score.conditions.BlockConditions;
import com.ssomar.score.conditions.CustomEIConditions;
import com.ssomar.score.conditions.EntityConditions;
import com.ssomar.score.conditions.ItemConditions;
import com.ssomar.score.conditions.PlayerConditions;
import com.ssomar.score.conditions.WorldConditions;
import com.ssomar.score.conditions.condition.placeholders.PlaceholdersCondition;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;
import com.ssomar.sevents.events.projectile.hitplayer.ProjectileHitPlayerEvent;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public interface SActivator {
	
	String getParentObjectID();
	
	String getID();

	SOption getOption();
	
	/* Delay */
	boolean isDelayInTick();
	
	int getDelay();
	
	/* Conditions */
	PlayerConditions getOwnerConditions();
	
	PlayerConditions getPlayerConditions();
	
	PlayerConditions getTargetPlayerConditions();
	
	WorldConditions getWorldConditions();
	
	ItemConditions getItemConditions();
	
	BlockConditions getBlockConditions();
	
	EntityConditions getTargetEntityConditions();
	
	BlockConditions getTargetBlockConditions();
	
	CustomEIConditions getCustomEIConditions();
	
	List<PlaceholdersCondition> getPlaceholdersConditions();
	
	/* Required ExecutableItems */
	List<RequiredEI> getRequiredExecutableItems();
	
	RequiredEI getRequiredEI(String id);
	
	/* Detailed blocks */
	DetailedBlocks getDetailedBlocks();


	static void cancelEvent(Event e, boolean condition) {
		if (e != null && condition && e instanceof Cancellable) {
			/* IMPORTANT? IF THE PROJECTILE IS NOT REMOVED THE SERVER CRASH ! */
			if(e instanceof ProjectileHitEntityEvent){
				((ProjectileHitEntityEvent)e).getEntity().remove();
			}
			else if(e instanceof ProjectileHitPlayerEvent){
				((ProjectileHitPlayerEvent)e).getEntity().remove();
			}
			((Cancellable) e).setCancelled(true);
		}
	}
}
