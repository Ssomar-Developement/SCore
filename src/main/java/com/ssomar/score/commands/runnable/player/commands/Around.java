package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommandTemplate;
import com.ssomar.score.commands.runnable.player.PlayerCommandsExecutor;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;

/* AROUND {distance} {true or false} {Your commands here} */
public class Around extends PlayerCommandTemplate{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo, boolean silenceOutput) {
		try {
			double distance = Double.valueOf(args.get(0));
			int cpt = 0;

			for (Entity e: receiver.getNearbyEntities(distance, distance, distance)) {
				if(e instanceof Player) {
					Player target =  (Player) e;
					if(target.hasMetadata("NPC") || target.equals(receiver)) continue;
					
					/* regroup the last args that correspond to the commands */
					StringBuilder prepareCommands = new StringBuilder();
					for(String s : args.subList(2, args.size())) {
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

						Location loc = target.getLocation();

						s = s.replaceAll("%target_x%", loc.getX()+"");
						s = s.replaceAll("%target_y%", loc.getY()+"");
						s = s.replaceAll("%target_z%", loc.getZ()+"");
						s = s.replaceAll("%target%", target.getName());
						new PlayerCommandsExecutor(Arrays.asList(s), p, silenceOutput, target, aInfo).runPlayerCommands(silenceOutput);		
					}				
					cpt++;
				}
			}
			if(cpt == 0 && Boolean.valueOf(args.get(1))) sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_PLAYER_HIT));

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String around= "AROUND {distance} {DisplayMsgIfNoPlayer true or false} {Your commands here}";
		if(args.size()<3) error = notEnoughArgs+around;
		else if(args.size()>3) { 
			try {
				Double.valueOf(args.get(0));

				if(Boolean.valueOf(args.get(1)) == null) error = invalidBoolean+args.get(1)+" for command: "+around;

			}catch(NumberFormatException e){
				error = invalidDistance+args.get(0)+" for command: "+around;
			}
		}

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("AROUND");
		return names;
	}

	@Override
	public String getTemplate() {
		return "AROUND {distance} {DisplayMsgIfNoPlayer true or false} {Your commands here}";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.LIGHT_PURPLE;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.DARK_PURPLE;
	}

}
