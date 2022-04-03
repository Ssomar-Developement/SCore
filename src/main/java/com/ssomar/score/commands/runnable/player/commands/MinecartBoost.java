package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.usedapi.WorldGuardAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/* BURN {timeinsecs} */
public class MinecartBoost extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		Entity entity;
		double boost = 5;

		try{
			boost = Double.parseDouble(args.get(0));
		}catch (Exception e){}

		if((entity = receiver.getVehicle()) != null && entity instanceof Minecart){
			Minecart minecart = (Minecart) entity;
			Location minecartLoc = minecart.getLocation();
			Block potentialRails = minecartLoc.getBlock();
			if(potentialRails.getType().toString().contains("RAIL")){
				minecart.setVelocity(receiver.getEyeLocation().getDirection().multiply(boost));
			}
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("MINECART_BOOST");
		return names;
	}

	@Override
	public String getTemplate() {
		return "MINECART_BOOST {boost (number)}";
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
