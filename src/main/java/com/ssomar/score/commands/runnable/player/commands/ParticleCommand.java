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
		try {
			receiver.getWorld().spawnParticle(Particle.valueOf(args.get(0)),
					receiver.getLocation(),
					Integer.parseInt(args.get(1)),
					Double.parseDouble(args.get(2)),
					Double.parseDouble(args.get(2)) ,
					Double.parseDouble(args.get(2)) ,
					Double.parseDouble(args.get(3)), null);
		}catch(Exception ignored) {}
	}

	@Override
	public String verify(List<String> args) {
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
		return "PARTICLE {type} {quantity} {offset} {speed}";
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
