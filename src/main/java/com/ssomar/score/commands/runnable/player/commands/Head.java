package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

/* HEAD */
public class Head extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		PlayerInventory inv = receiver.getInventory();
		ItemStack item = inv.getItemInMainHand();
		ItemStack headItem = inv.getHelmet();
		if(!item.getType().equals(Material.AIR)) {
			inv.setHelmet(item);
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
		names.add("HEAD");
		return names;
	}

	@Override
	public String getTemplate() {
		return "HEAD";
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
