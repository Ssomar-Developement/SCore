package com.ssomar.score.sobject.sactivator;

import java.util.List;

import com.ssomar.score.sobject.sactivator.conditions.BlockConditions;
import com.ssomar.score.sobject.sactivator.conditions.CustomEIConditions;
import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.sobject.sactivator.conditions.ItemConditions;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;
import com.ssomar.score.sobject.sactivator.conditions.placeholders.PlaceholdersCondition;

public interface SActivator {
	
	public String getID();

	public SOption getOption(); 
	
	public PlayerConditions getOwnerConditions();
	
	public PlayerConditions getPlayerConditions();
	
	public PlayerConditions getTargetPlayerConditions();
	
	public WorldConditions getWorldConditions();
	
	public ItemConditions getItemConditions();
	
	public EntityConditions getTargetEntityConditions();
	
	public BlockConditions getTargetBlockConditions();
	
	public CustomEIConditions getCustomEIConditions();
	
	public List<PlaceholdersCondition> getPlaceholdersConditions();
}
