package com.ssomar.score.sobject.sactivator;

import java.util.List;

import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;

public interface SActivator {
	
	public String getID();

	public SOption getOption(); 
	
	public PlayerConditions getOwnerConditions();
	
	public PlayerConditions getPlayerConditions();
	
	public PlayerConditions getTargetPlayerConditions();
	
	public WorldConditions 	getWorldConditions();
	
	public EntityConditions getTargetEntityConditions();
	
	public BlockConditions getTargetBlockConditions();
	
	public List<PlaceholdersCondition> getPlaceholdersConditions();
}
