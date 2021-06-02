package com.ssomar.score.commands.runnable.item;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.commands.runnable.ActionInfo;

public interface ItemCommandInterface {

    public abstract void run(Player p, ItemStack item, List<String> args, ActionInfo aInfo, boolean silenceOutput);
	
	public abstract String verify(List<String> args);
}
