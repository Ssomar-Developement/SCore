package com.ssomar.score.sobject;

import java.io.File;
import java.util.List;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.sobject.sactivator.SActivator;
import org.jetbrains.annotations.Nullable;

public interface SObject {
	
	String getId();

	void setId(String id);
	
	String getPath();
	
	List<SActivator> getActivators();
	
	ItemStack formItem(int quantity, Player p);
	
	@Nullable
    SActivator getActivator(String actID);

}
