package com.ssomar.score.sobject;

import java.util.List;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.sobject.sactivator.SActivator;
import org.jetbrains.annotations.Nullable;

public interface SObject {
	
	String getID();
	
	String getPath();
	
	List<SActivator> getActivators();
	
	ItemStack formItem(int quantity, Player p);
	
	@Nullable
    SActivator getActivator(String actID);

}
