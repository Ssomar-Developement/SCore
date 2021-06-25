package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.actionbar.Actionbar;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class ActionbarCommand extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		if(args.size() >= 2) {
			
			String name = "";
			
			for(int i = 0; i < args.size()-1; i++) {
				if(i == 0) name = args.get(i);
				else name = name +" "+ args.get(i);
			}
			
			try {
				int time = Integer.valueOf(args.get(args.size()-1));
				ActionbarHandler.getInstance().addActionbar(receiver, new Actionbar(name, time));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public String verify(List<String> args) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("ACTIONBAR");
		return names;
	}

	@Override
	public String getTemplate() {
		return "ACTIONBAR {name} {time in secs}";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.GREEN;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.DARK_GREEN;
	}

}
