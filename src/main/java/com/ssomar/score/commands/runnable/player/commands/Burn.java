package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;

/* BURN {timeinsecs} */
public class Burn extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			int time = 200;

			double timeDouble = Double.parseDouble(args.get(0));
			time = (int) timeDouble;

			if(SCore.hasWorldGuard) {
				if(WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation())) {
					receiver.setFireTicks(20 * time);
				}
				/* setVisualFire appears in 1.17 */
				else if(SCore.is1v17()){
					receiver.setVisualFire(true);

					BukkitRunnable runnable = new BukkitRunnable() {

						@Override
						public void run() {
							if(receiver.isOnline()) receiver.setVisualFire(false);
						}
					};
					runnable.runTaskLater(SCore.getPlugin(), time);
				}
			}
			else receiver.setFireTicks(20 * time);

		}catch(Exception ignored) {}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String burn= "BURN {timeinsecs}";
		if(args.size() > 1) error = tooManyArgs+burn;
		else if(args.size() == 1) { 
			try {
				Double.valueOf(args.get(0));
			}catch(NumberFormatException e){
				error = invalidTime + args.get(0)+" for command: "+burn;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("BURN");
		return names;
	}

	@Override
	public String getTemplate() {
		return "BURN {timeinsecs}";
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
