package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommandTemplate;

/* PARTICLE {type} {quantity} {offset} {speed}*/
public class ParticleCommand extends BlockCommandTemplate{

	@Override
	public void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		//By default particles is spawn on the side on the block, this part center the spawn
		Location newLoc = block.getLocation().clone();
		newLoc.add(0.5, 0.5, 0.5);

		try {
			block.getWorld().spawnParticle(Particle.valueOf(args.get(0)),
					newLoc,
					Integer.valueOf(args.get(1)),
					Double.valueOf(args.get(2)),
					Double.valueOf(args.get(2)) ,
					Double.valueOf(args.get(2)) ,
					Double.valueOf(args.get(3)), null);
		}catch(Exception e) {}
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

}
