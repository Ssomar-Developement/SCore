package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class SendBlankMessage extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		String message=" ";
		receiver.sendMessage(message);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";	
		return error;
	}
}
