package com.ssomar.score.sobject;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.sobject.sactivator.SActivator;

public interface SObject {
	
	public String getID();
	
	public String getPath();
	
	public List<SActivator> getActivators();
	
	
	public ItemStack formItem(int quantity, Player p);
	
	@Nullable
	public SActivator getActivator(String actID);

}
