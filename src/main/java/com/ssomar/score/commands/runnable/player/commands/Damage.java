package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.events.BlockBreakEventExtension;
import com.ssomar.score.utils.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Damage extends PlayerCommand{

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		/* When target a NPC it can occurs */
		if(receiver == null) return;

		try {
			double amount;
			String damage = args.get(0);
			/* percentage damage */
			if(damage.contains("%")) {
				String [] decomp = damage.split("\\%");
				damage = decomp[0];
				damage = damage.trim();
				if(damage.length() == 1){
					damage = "0"+damage;
				}
				
				double percentage = damage.equals("100") ? 1 : Double.parseDouble("0."+damage);
				amount = receiver.getMaxHealth() * percentage;
				amount = NTools.reduceDouble(amount, 2);
			}
			else amount = Double.parseDouble(damage);
			
			if(amount > 0 && !receiver.isDead()) {
				if(p != null) {
					boolean doDamage = true;
					if(SCore.hasWorldGuard) doDamage = WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation());
					if(doDamage) {
						p.setMetadata("cancelDamageEvent", (MetadataValue)new FixedMetadataValue((Plugin)SCore.plugin, Integer.valueOf(7772)));
						receiver.damage(amount, (Entity)p);
					}
				}
				else {
					boolean doDamage = true;
					if(SCore.hasWorldGuard) doDamage = WorldGuardAPI.isInPvpZone(receiver, receiver.getLocation());
					if(doDamage) receiver.damage(amount);
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public String verify(List<String> args) {
		String error ="";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("DAMAGE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "DAMAGE {number}";
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