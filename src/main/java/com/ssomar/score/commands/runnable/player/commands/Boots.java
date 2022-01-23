package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

/* HEAD */
public class Boots extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		PlayerInventory inv = receiver.getInventory();
		ItemStack item = inv.getItemInMainHand();
		ItemStack headItem = inv.getBoots();
		if(!item.getType().equals(Material.AIR)) {
			inv.setBoots(item);
			inv.setItemInMainHand(headItem);
		}
		
	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("BOOTS");
		return names;
	}

	@Override
	public String getTemplate() {
		return "BOOTS";
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
