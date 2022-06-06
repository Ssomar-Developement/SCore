package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;


public class DropExecutableItem extends EntityCommand{

	public static final Boolean DEBUG = false;
	//
	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		try {
			SsomarDev.testMsg("DropExecutableItem.run()", DEBUG);
			if(SCore.hasExecutableItems && ExecutableItemsAPI.getExecutableItemsManager().isValidID(args.get(0))) {
				SsomarDev.testMsg("DropExecutableItem.run() - hasExecutableItems", DEBUG);
				int amount = Integer.parseInt(args.get(1));
				if(amount > 0) {
					SsomarDev.testMsg("DropExecutableItem.run() - amount > 0", DEBUG);
					Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(args.get(0));
					if(eiOpt.isPresent()) {
						SsomarDev.testMsg(">> loc: "+entity.getLocation());
						entity.getWorld().dropItem(entity.getLocation(), eiOpt.get().buildItem(amount, Optional.ofNullable(p)));
					}
				}
			}
		}catch(Exception ignored) {
			ignored.printStackTrace();
		}
	}


	@Override
	public String verify(List<String> args) {
		String error = "";

		String dropei = "DROPEXECUTABLEITEM {id} {quantity}";
		if(args.size() < 2) error = notEnoughArgs+dropei;
		else if(args.size() != 2) error = tooManyArgs+dropei;

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DROPEXECUTABLEITEM");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DROPEXECUTABLEITEM {id} {quantity}";
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
