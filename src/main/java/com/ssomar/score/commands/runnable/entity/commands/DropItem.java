package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandTemplate;

public class DropItem extends EntityCommandTemplate{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			int amount = Integer.valueOf(args.get(1));
			if(amount>0 && !entity.isDead()) {
				entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.valueOf(args.get(0)), amount));
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		return "";
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

}
