package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;

/* PARTICLE {type} {quantity} {offset} {speed} */
public class ParticleCommand extends EntityCommand{

	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		try {
			entity.getWorld().spawnParticle(Particle.valueOf(args.get(0).toUpperCase()),
					entity.getLocation(),
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
