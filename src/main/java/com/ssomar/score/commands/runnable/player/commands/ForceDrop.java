package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.fly.FlyManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class ForceDrop extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		int slot = -1;
		try {
			slot = Integer.parseInt(args.get(0));
		}catch(NumberFormatException e){
			return;
		}

		PlayerInventory inventory = receiver.getInventory();

		if(slot == -1)
			slot = inventory.getHeldItemSlot();

		ItemStack toDrop = inventory.getItem(slot);
		if(toDrop != null) {
			inventory.clear(slot);
			receiver.getLocation().getWorld().dropItem(receiver.getLocation(), toDrop);
		}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";
		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("FORCEDROP");
		return names;
	}

	@Override
	public String getTemplate() {
		return "FORCEDROP {slot -1 for main_hand}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}
}
