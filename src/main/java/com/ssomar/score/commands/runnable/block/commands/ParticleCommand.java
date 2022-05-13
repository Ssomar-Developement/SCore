package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;

/* PARTICLE {type} {quantity} {offset} {speed}*/
public class ParticleCommand extends BlockCommand{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
		//By default particles is spawn on the side on the block, this part center the spawn
		Location newLoc = block.getLocation().clone();
		newLoc.add(0.5, 0.5, 0.5);

		try {
			block.getWorld().spawnParticle(Particle.valueOf(args.get(0).toUpperCase()),
					newLoc,
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
