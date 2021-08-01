package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;

/* DROPITEM {material} [quantity} */
public class DropItem extends BlockCommand{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
		try {
			int amount = Integer.valueOf(args.get(1));
			if(amount>0) {
				block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.valueOf(args.get(0).toUpperCase()), amount));
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";
		
		String dropitem = "DROPITEM {material} [quantity}";
		if(args.size()<2) error = notEnoughArgs+dropitem;
		else if(args.size()==2) {
			if(Material.matchMaterial(args.get(0).toUpperCase())==null) error = invalidMaterial+args.get(0)+" for command: "+dropitem;
			else {
				try {
					Integer.valueOf(args.get(1));
				}catch(NumberFormatException e){
					error = invalidQuantity+args.get(1)+" for command: "+dropitem;
				}
			}
		}
		else error= tooManyArgs+dropitem;
		
		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DROPITEM");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DROPITEM {material} {quantity}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
