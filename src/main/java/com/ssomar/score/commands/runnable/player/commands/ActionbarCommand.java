package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.actionbar.Actionbar;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class ActionbarCommand extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		SsomarDev.testMsg("passe actionbar");
		if(args.size() == 2) {
			try {
				int time = Integer.valueOf(args.get(1));
				SsomarDev.testMsg("passe actionbar: "+time+ " first: "+args.get(0));
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

}
