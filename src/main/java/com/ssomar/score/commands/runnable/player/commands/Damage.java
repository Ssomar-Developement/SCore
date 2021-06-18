package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.StringPlaceholder;

public class Damage extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			int amount= Integer.valueOf(args.get(0));
			if(amount>0 && !receiver.isDead()) {
				if(receiver.getHealth()<=Integer.valueOf(amount)) {
					StringPlaceholder sp = new StringPlaceholder();
					sp.setPlayer(p.getName());
					sp.setTarget(receiver.getName());
					sp.setItem(aInfo.getName());
					Bukkit.getServer().broadcastMessage(StringConverter.coloredString(sp.replacePlaceholder("Someone is dead by a damage command (to edit SCore)")));
				}
				receiver.damage(Integer.valueOf(amount));
			}
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "DAMAGE {number}";
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