package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.entity.Player;

import com.ssomar.score.actionbar.Actionbar;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class ActionbarCommand extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		if(args.size() == 2) {
			try {
				int time = Integer.valueOf(args.get(1));
				ActionbarHandler.getInstance().addActionbar(receiver, new Actionbar(args.get(0), time));
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
		names.add("ACTIONBAR ON");
		return names;
	}

	@Override
	public String getTemplate() {
		return "ACTIONBAR ON";
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public Color getExtraColor() {
		return Color.OLIVE;
	}

}
