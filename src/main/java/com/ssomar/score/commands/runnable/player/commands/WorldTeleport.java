package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

public class WorldTeleport extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {

		if(args.size()==1) {

			Location locP = receiver.getLocation();
			
			World world = Bukkit.getWorld(args.get(0));
			
			
			int i = 0;
			boolean teleport= false;
			while(i<999 && !teleport) {
				
				Location newLoc = new Location(world, locP.getX()+i, locP.getY(), locP.getZ());

				if(world.getEnvironment().equals(Environment.NETHER)) {
					int y = receiver.getWorld().getHighestBlockYAt((int) newLoc.getX(), (int) newLoc.getZ());
					newLoc.setY(y);
					
					boolean block1 = false;
					boolean block2 = false;
					
					while(newLoc.getY()>120) {
						newLoc.subtract(0, 1, 0);
					}
					
					while(newLoc.getY()>0) {
						if(block1 && newLoc.getBlock().isEmpty()) {
							block2 = true;
						}
						else if(newLoc.getBlock().isEmpty()) {
							block1 = true;
						}
						else if(block1 && block2){
							if(newLoc.getBlock().getType().equals(Material.LAVA)) {
								block1 = false;
								block2 = false;
								break;
							}
							newLoc.add(0, 1, 0);
							receiver.teleport(newLoc);
							teleport = true;
							break;
						}
						else {
							block1 = false;
							block2 = false;
						}
						newLoc.subtract(0, 1, 0);
					}
				}
				else {
					int y = receiver.getWorld().getHighestBlockYAt((int) newLoc.getX(), (int) newLoc.getZ());
					newLoc.setY(y);
					while(newLoc.getBlock().isEmpty() && newLoc.getY()>0) {
						newLoc.subtract(0, 1, 0);
					}
					if(newLoc.getBlock().getType().equals(Material.LAVA)) {
						continue;
					}
					if(newLoc.getBlock().isEmpty() && newLoc.getY() > 0) continue;
					else if(!newLoc.getBlock().isEmpty()) {
						newLoc.add(0, 1, 0);
						receiver.teleport(newLoc);
						teleport = true;
					}
				}
				i++;
			}
			if(!teleport) receiver.sendMessage("ERROR NO POS FOUND");	
		}
	}

	@Override
	public String verify(List<String> args) {

		String error ="";

		String wT= "WORLDTELEPORT {world}";
		if(args.size()!=1) error = notEnoughArgs+wT;
		else {
			if(Bukkit.getWorld(args.get(0))==null) error = invalidWorld+args.get(0)+" for command: "+wT;
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("WORLDTELEPORT");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "WORLDTELEPORT {world}";
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
