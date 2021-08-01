package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

public class ParticleCommand extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		// TODO Auto-generated method stub
		try {
			receiver.getWorld().spawnParticle(Particle.valueOf(args.get(0)),
					receiver.getLocation(),
					Integer.valueOf(args.get(1)),
					Double.valueOf(args.get(2)),
					Double.valueOf(args.get(2)) ,
					Double.valueOf(args.get(2)) ,
					Double.valueOf(args.get(3)), null);
		}catch(Exception e) {}
	}

	@Override
	public String verify(List<String> args) {
		// TODO Auto-generated method stub
		return "";
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("PARTICLE");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "PARTICLE {type} {quantity} {offset} {speed}";
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
