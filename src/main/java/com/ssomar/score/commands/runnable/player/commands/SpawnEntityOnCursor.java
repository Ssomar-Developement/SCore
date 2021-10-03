package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

/* SPAWNENTITYONCURSOR {entity} {amount} {maxRange} */
public class SpawnEntityOnCursor extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		int range = 200;
		if(args.size()==3) {
			try {
				range= Integer.parseInt(args.get(2));
				if(range<=0) {
					range=200;
				}
			}catch(Exception ignored) {}
		}

		EntityType entityType = EntityType.ZOMBIE;
		int amount=1;

		if(args.size()>0) { 
			try {
				entityType= EntityType.valueOf(args.get(0).toUpperCase());
			}catch(Exception ignored){}
		}
		if(args.size()>1) { 
			try {
				amount = Integer.parseInt(args.get(1));
			}catch(NumberFormatException ignored){}
		}

		Block block = receiver.getTargetBlock(null, range);

		if(block.getType()!=Material.AIR) {

			Location loc = block.getLocation();
			loc.add(0, 1, 0);	

			if(entityType.equals(EntityType.LIGHTNING)) {
				for(int i=0; i<amount; i++) receiver.getWorld().strikeLightning(loc);
			}
			else {
				for(int i=0; i<amount; i++) {	
					Entity e =receiver.getWorld().spawnEntity(loc, entityType);
					if(entityType.equals(EntityType.FIREBALL)) e.setVelocity(new Vector(0, 0, 0));
				}
			}
			
			
		}
	}

	@Override
	public String verify(List<String> args) {
		String error ="";

		String spawnOnCursor= "SPAWNENTITYONCURSOR {entity} {amount} {maxRange}";
		if(args.size()>3) error= tooManyArgs+spawnOnCursor;
		if(args.size()>0) { 
			try {
				EntityType.valueOf(args.get(0).toUpperCase());
			}catch(Exception e){
				error = invalidEntityType+args.get(0)+" for command: "+spawnOnCursor;
				return error;
			}
		}
		if(args.size()>1) { 
			try {
				Integer.valueOf(args.get(1));
			}catch(NumberFormatException e){
				error = invalidQuantity+args.get(0)+" for command: "+spawnOnCursor;
				return error;
			}
		}
		else if(args.size()>2) { 
			try {
				Integer.valueOf(args.get(2));
			}catch(NumberFormatException e){
				error = invalidRange+args.get(0)+" for command: "+spawnOnCursor;
			}
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("SPAWNENTITYONCURSOR");
		return names;
	}

	@Override
	public String getTemplate() {
		return "SPAWNENTITYONCURSOR {entity} {amount} {maxRange}";
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
