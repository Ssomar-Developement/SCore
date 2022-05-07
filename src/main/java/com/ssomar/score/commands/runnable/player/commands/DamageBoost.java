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
public class DamageBoost extends PlayerCommand{

	private static DamageBoost instance;
	@Getter
	private Map<UUID, List<Double>> activeBoosts;
	private static final Boolean DEBUG = false;


	public DamageBoost() {
		activeBoosts = new HashMap<>();
	}

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			double boost = Double.valueOf(args.get(0));
			int time = Integer.valueOf(args.get(1));

			//SsomarDev.testMsg("ADD receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
			if(activeBoosts.containsKey(receiver.getUniqueId())){
				activeBoosts.get(receiver.getUniqueId()).add(boost);
			}
			else activeBoosts.put(receiver.getUniqueId(), new ArrayList<>(Arrays.asList(boost)));

			BukkitRunnable runnable3 = new BukkitRunnable() {
				@Override
				public void run() {
					//SsomarDev.testMsg("REMOVE receiver: "+receiver.getUniqueId()+ " Damage Resistance: " + reduction + " for " + time + " ticks");
					if(activeBoosts.containsKey(receiver.getUniqueId())){
						if(activeBoosts.get(receiver.getUniqueId()).size() > 1){
							activeBoosts.get(receiver.getUniqueId()).remove(boost);
						}
						else activeBoosts.remove(receiver.getUniqueId());
					}
				}
			};
			runnable3.runTaskLater(SCore.plugin, time);

		}catch(Exception ignored) {
			ignored.printStackTrace();
		}
	}
	public double getNewDamage(UUID uuid, double damage){
		if(DamageBoost.getInstance().getActiveBoosts().containsKey(uuid)) {
			if(DEBUG) SsomarDev.testMsg("DamageBoostEvent base: " + damage);
			int boost = 0;
			for(double d : DamageBoost.getInstance().getActiveBoosts().get(uuid)) {
				boost += d;
			}
			if(DEBUG) SsomarDev.testMsg("DamageBoostEvent bost: " + boost);

			double averagePercent = boost / 100;
			damage = damage + (damage * averagePercent);
			if(DEBUG) SsomarDev.testMsg("DamageBoostEvent modified "+damage);
		}
		return damage;
	}


	@Override
	public String verify(List<String> args) {
		String error = "";

		String xpboost = "DAMAGE_BOOST {modification in percentage} {timeinticks}";
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
				Integer.valueOf(args.get(0));
			} catch (NumberFormatException e) {
				error = invalidTime + args.get(0) + " for command: " + xpboost;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE_BOOST");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DAMAGE_BOOST {modification in percentage example 100} {timeinticks}";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

	public static DamageBoost getInstance() {
		if(instance == null) instance = new DamageBoost();
		return instance;
	}
}
