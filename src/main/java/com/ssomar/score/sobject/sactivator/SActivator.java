package com.ssomar.score.sobject.sactivator;

import com.ssomar.score.sobject.sactivator.conditions.EntityConditions;
import com.ssomar.score.sobject.sactivator.conditions.PlayerConditions;
import com.ssomar.score.sobject.sactivator.conditions.WorldConditions;

public interface SActivator {
	
	public String getID();

	public SOption getOption(); 
	
	public PlayerConditions getPlayerConditions();
	
	public WorldConditions getWorldConditions();
	
	public EntityConditions getEntityConditions();
}
