package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.nofalldamage.NoFallDamageManager;

/* JUMP {amount} */
public class Jump extends PlayerCommand{


	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		double jump = 5;
		if(args.size() == 1) {
			try {
				jump = Double.parseDouble(args.get(0));
			}catch(Exception ignored) {}
		}

		Vector v = receiver.getVelocity();
		v.setX(0);
		v.setY(jump);
		v.setZ(0);
		receiver.setVelocity(v);

		/* NO FALL DAMAGE PART */
		NoFallDamageManager.getInstance().addNoFallDamage(receiver);
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String jump= "JUMP {number (max 5)}";
		if(args.size()>1) error= tooManyArgs+jump;
		else if(args.size()==1) { 
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime+args.get(0)+" for command: "+jump;
			}
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("JUMP");
		return names;
	}

	@Override
	public String getTemplate() {
		return "JUMP {number (max 5)}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}
}