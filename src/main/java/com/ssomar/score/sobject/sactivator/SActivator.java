package com.ssomar.score.sobject.sactivator;

import java.util.List;

import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;
import com.ssomar.score.sobject.sactivator.requiredei.RequiredEI;

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
}
