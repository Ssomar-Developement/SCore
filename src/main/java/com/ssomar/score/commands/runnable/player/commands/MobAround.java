package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommandsExecutor;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			double distance=  Double.valueOf(args.get(0));
			int cpt = 0;

			for (Entity e: receiver.getNearbyEntities(distance, distance, distance)) {
				if(!(e instanceof Player)) {
	
					if(e.hasMetadata("NPC") || e.equals(receiver)) continue;
					
					/* regroup the last args that correspond to the commands */
					StringBuilder prepareCommands = new StringBuilder();
					for(String s: args.subList(1, args.size())) {
						prepareCommands.append(s);
						prepareCommands.append(" ");
					}
					prepareCommands.deleteCharAt(prepareCommands.length()-1);				

					String buildCommands = prepareCommands.toString();
					String [] tab;
					if(buildCommands.contains("+++")) tab = buildCommands.split("\\+\\+\\+");
					else {
						tab = new String[1];
						tab[0] = buildCommands;
					}
					for(String s : tab) {
						while(s.startsWith(" ")) {
							s = s.substring(1, s.length());
						}
						while(s.endsWith(" ")) {
							s = s.substring(0, s.length()-1);
						}
						if(s.startsWith("/")) s = s.substring(1, s.length());

						Location loc = e.getLocation();

						s =s.replaceAll("%entity_x%", loc.getX()+"");
						s =s.replaceAll("%entity_y%", loc.getY()+"");
						s =s.replaceAll("%entity_z%", loc.getZ()+"");
						s= s.replaceAll("%entity%", e.getType().toString());
						s= s.replaceAll("%entity_uuid%", e.getUniqueId().toString());
						new EntityCommandsExecutor(Arrays.asList(s), p, silenceOutput, e, aInfo).runEntityCommands(silenceOutput);		
					}				
					cpt++;
				}
			}
			if(cpt == 0) sm.sendMessage(receiver, "&cNo entity has been hit");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String around= "MOB_AROUND {distance} {Your commands here}";
		if(args.size()<2) error = notEnoughArgs+around;
		else if(args.size()>2) { 
			try {
				Double.valueOf(args.get(0));

			}catch(NumberFormatException e){
				error = invalidDistance+args.get(0)+" for command: "+around;
			}
		}

		return error;
	}
	
	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("MOB_AROUND");
		return names;
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "MOB_AROUND {distance} {Your commands here}";
	}

	@Override
	public ChatColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
