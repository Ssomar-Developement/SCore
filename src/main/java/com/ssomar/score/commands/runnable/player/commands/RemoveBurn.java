package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class RemoveBurn extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		receiver.setFireTicks(0);
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

}
