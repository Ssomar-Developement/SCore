package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.executableitems.fly.FlyManager;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* FLY ON */
public class FlyOn extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		receiver.setAllowFlight(true);
		FlyManager.getInstance().addPlayerWithFly(p);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";
		return error;
	}
}
