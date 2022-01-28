package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.fly.FlyManager;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

/* FLY OFF */
@SuppressWarnings("deprecation")
public class FlyOff extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		boolean teleport = true;
		try{
			teleport = Boolean.parseBoolean(args.get(0));
		} catch (Exception e){

		}
		if(teleport){
			if(!receiver.isOnGround()) {
				Location playerLocation= receiver.getLocation();
				boolean isVoid = false;
				while(playerLocation.getBlock().isEmpty()) {
					if(playerLocation.getY()<=1) {
						isVoid = true;
						break;
					}
					playerLocation.subtract(0, 1, 0);
				}
				if(!isVoid) {
					playerLocation.add(0, 1, 0);
					receiver.teleport(playerLocation);
				}
			}
		}
		receiver.setAllowFlight(false);
		receiver.setFlying(false);
		FlyManager.getInstance().removePlayerWithFly(p);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";
		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("FLY OFF");
		return names;
	}

	@Override
	public String getTemplate() {
		return "FLY OFF {teleportOnTheGround true or false}";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.BLUE;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.AQUA;
	}
}
