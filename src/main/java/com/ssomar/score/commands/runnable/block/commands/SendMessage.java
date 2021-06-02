package com.ssomar.score.commands.runnable.block.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;

public class SendMessage extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		String message="";
		for(String s: args) {
			//SsomarDev.testMsg("cmdarg> "+s);
			message= message+s+" ";
		}
		message = message.substring(0, message.length()-1);
		sm.sendMessage(p, message);
	}

	@Override
	public String verify(List<String> args) {
		return "";
	}

}
