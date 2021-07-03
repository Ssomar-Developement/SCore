package com.ssomar.score.nofalldamage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.ssomar.score.utils.Couple;



public class NoFallDamageManager {

	private Map<Player, List<Couple<UUID,BukkitTask>>> noFallDamageMap = new HashMap<>();

	private static NoFallDamageManager instance;

	public void addNoFallDamage(Player p, Couple<UUID, BukkitTask> c) {
		if (noFallDamageMap.containsKey(p)) {
			noFallDamageMap.get(p).add(c);
		}
		else {
			List<Couple<UUID, BukkitTask>> newList = new ArrayList<>();
			newList.add(c);
			noFallDamageMap.put(p, newList);
		}
	}


	public void removeNoFallDamage(Player p, UUID uuid) {
		boolean emptyList = false;
		for(Player player : noFallDamageMap.keySet()) {

			if(p.equals(player)) {
				List<Couple<UUID, BukkitTask>> tasks = noFallDamageMap.get(p);

				Couple<UUID, BukkitTask> toRemove = null;
				for(Couple<UUID, BukkitTask> c : tasks) {
					if(c.getElem1().equals(uuid)) {
						c.getElem2().cancel();
						toRemove = c;
					}
				}
				if(toRemove != null) tasks.remove(toRemove);

				emptyList = tasks.size() == 0;

				break;
			}
		}	
		if(emptyList) noFallDamageMap.remove(p);

	}
	
	public void removeAllNoFallDamage(Player p) {
		noFallDamageMap.remove(p);
	}

	public boolean contains(Player p) {
		for(Player player : noFallDamageMap.keySet()) {
			if(p.equals(player)) {
				return true;
			}
		}	
		return false;
	}

	
	public static NoFallDamageManager getInstance() {
		if(instance == null) instance = new NoFallDamageManager();
		return instance;
	}

}
