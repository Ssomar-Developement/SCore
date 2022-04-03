package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ForceFocus extends PlayerCommand {

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

		int time = 60;
		UUID targetUUID = null;
		try {
			targetUUID = UUID.fromString(args.get(0));
		}catch(Exception e){
			return;
		}
		try {
			time = Integer.parseInt(args.get(1));
		}catch(NumberFormatException e){}

		Entity targetEntity = Bukkit.getServer().getEntity(targetUUID);
		if(targetEntity != null) {
			BukkitRunnable runnable3 = new BukkitRunnable() {

				@Override
				public void run() {
					if(targetEntity == null  || targetEntity.isDead() || receiver == null || receiver.isDead() || !receiver.isOnline()){
						this.cancel();
						return;
					}

					Location targetLoc = targetEntity.getLocation().add(0,1.5,0);
					Location eyeLoc = receiver.getEyeLocation();
					Vector vec = new Vector(eyeLoc.getX()-targetLoc.getX(), eyeLoc.getY()-targetLoc.getY(),eyeLoc.getZ()-targetLoc.getZ());
					vec = vec.normalize();
					receiver.teleport(receiver.getLocation().setDirection(vec.multiply(-1)));
				}
			};
			runnable3.runTaskTimer(SCore.plugin, 0, 5);
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
		names.add("FORCEFOCUS");
		return names;
	}

	@Override
	public String getTemplate() {
		return "FORCEFOCUS {UUID} {time in ticks}";
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
