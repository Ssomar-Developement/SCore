package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* FLY OFF */
@SuppressWarnings("deprecation")
public class FlyOff extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		if(!receiver.isOnGround()) {
			Location playerLocation= receiver.getLocation();
			boolean isVoid=false;
			while(playerLocation.getBlock().isEmpty()) {
				if(playerLocation.getY()<=1) {
					isVoid=true;
					break;
				}
				playerLocation.subtract(0, 1, 0);
			}
			if(!isVoid) {
				playerLocation.add(0, 1, 0);
				receiver.teleport(playerLocation);
			}
		}
		receiver.setAllowFlight(false);
		receiver.setFlying(false);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";
		return error;
	}
}
