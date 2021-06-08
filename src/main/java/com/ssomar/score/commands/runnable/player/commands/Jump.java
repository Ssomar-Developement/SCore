package com.ssomar.score.commands.runnable.player.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;
import com.ssomar.score.nofalldamage.NoFallDamageManager;
import com.ssomar.score.utils.Couple;

/* JUMP {amount} */
public class Jump extends PlayerCommandTemplate{


	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {

		int jump = 5;
		if(args.size()==1) {
			try {
				jump = Integer.valueOf(args.get(0));
			}catch(Exception e) {}
		}

		Vector v = receiver.getVelocity();
		v.setX(0);
		v.setY(jump);
		v.setZ(0);
		receiver.setVelocity(v);


		UUID uuid = UUID.randomUUID();

		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {
				NoFallDamageManager.getInstance().removeNoFallDamage(receiver, uuid);
			}
		};
		BukkitTask task = runnable.runTaskLater(SCore.getPlugin(), 300);

		NoFallDamageManager.getInstance().addNoFallDamage(receiver, new Couple<UUID, BukkitTask>(uuid, task));
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String jump= "JUMP {number}";
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
}