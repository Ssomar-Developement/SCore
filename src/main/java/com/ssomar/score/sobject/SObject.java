package com.ssomar.score.sobject;

import java.util.List;
import java.util.Optional;


import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.sobject.sactivator.SActivator;
import org.jetbrains.annotations.Nullable;

public interface SObject {
	
	String getId();

	void setId(String id);
	
	String getPath();
	
	List<SActivator> getActivators();
	
	ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

	Item dropItem(Location location, int amount);
	
	@Nullable
    SActivator getActivator(String actID);

}
