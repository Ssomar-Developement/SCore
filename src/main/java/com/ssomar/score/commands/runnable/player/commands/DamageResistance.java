package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/* BURN {timeinsecs} */
public class DamageResistance extends PlayerCommand{

	private static DamageResistance instance;
	@Getter
	private Map<UUID, List<Double>> activeResistances;
	private static final Boolean DEBUG = false;

	public DamageResistance() {
		activeResistances = new HashMap<>();
	}

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			double reduction = Double.valueOf(args.get(0));
			int time = Integer.valueOf(args.get(1));

			//SsomarDev.testMsg("ADD receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
			if(activeResistances.containsKey(receiver.getUniqueId())){
				activeResistances.get(receiver.getUniqueId()).add(reduction);
			}
			else activeResistances.put(receiver.getUniqueId(), new ArrayList<>(Arrays.asList(reduction)));

			BukkitRunnable runnable3 = new BukkitRunnable() {
				@Override
				public void run() {
					//SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
					if(activeResistances.containsKey(receiver.getUniqueId())){
						if(activeResistances.get(receiver.getUniqueId()).size() > 1){
							activeResistances.get(receiver.getUniqueId()).remove(reduction);
						}
						else activeResistances.remove(receiver.getUniqueId());
					}
				}
			};
			runnable3.runTaskLater(SCore.plugin, time);

		}catch(Exception ignored) {
			ignored.printStackTrace();
		}
	}

	public double getNewDamage(UUID uuid, double damage){
		if(DamageResistance.getInstance().getActiveResistances().containsKey(uuid)) {
			if(DEBUG) SsomarDev.testMsg("DamageResistanceEvent base: " + damage);
			double resistance = 0;
			for(double d : DamageResistance.getInstance().getActiveResistances().get(uuid)) {
				resistance += d;
			}

			double averagePercent = resistance / 100;
			damage = damage + (damage * averagePercent);
			if(DEBUG) SsomarDev.testMsg("DamageResistanceEvent modified "+damage);
		}
		return damage;
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String xpboost = "DAMAGE_RESISTANCE {modification in percentage} {timeinticks}";
		if(args.size() > 2) error = tooManyArgs+xpboost;
		else if(args.size() < 2) error = notEnoughArgs+xpboost;
		else if(!args.get(0).contains("%")){
			try {
				Double.valueOf(args.get(0));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(0) + " for command: " + xpboost;
			}
		}
		else if(!args.get(1).contains("%")){
			try {
				Integer.valueOf(args.get(1));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(1) + " for command: " + xpboost;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE_RESISTANCE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DAMAGE_RESISTANCE {modification in percentage example 100} {timeinticks}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

	public static DamageResistance getInstance() {
		if(instance == null) instance = new DamageResistance();
		return instance;
	}
}
