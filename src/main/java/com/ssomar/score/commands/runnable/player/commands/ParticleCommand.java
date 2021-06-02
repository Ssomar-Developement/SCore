package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class ParticleCommand extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
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

}
