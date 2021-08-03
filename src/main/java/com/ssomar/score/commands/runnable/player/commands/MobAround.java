package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.entity.EntityRunCommandsBuilder;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.utils.placeholders.StringPlaceholder;

/* MOB_AROUND {distance} {Your commands here} */
public class MobAround extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		try {
			double distance =  Double.valueOf(args.get(0));
			int cpt = 0;

			int startForCommand = 1;
			boolean mute = false;
			if(args.get(1).equalsIgnoreCase("true")) {
				startForCommand = 2;
				mute = true;
			}
			else if( args.get(1).equalsIgnoreCase("false")) {
				startForCommand = 2;
			}

			for (Entity e: receiver.getNearbyEntities(distance, distance, distance)) {
				if(!(e instanceof Player)) {

					if(e.hasMetadata("NPC") || e.equals(receiver)) continue;
					
					StringPlaceholder sp = new StringPlaceholder();
					sp.setAroundTargetEntityPlcHldr(e.getUniqueId());

					/* regroup the last args that correspond to the commands */
					StringBuilder prepareCommands = new StringBuilder();
					for(String s: args.subList(startForCommand, args.size())) {
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

						s = sp.replacePlaceholder(s); 
						
						EntityRunCommandsBuilder builder = new EntityRunCommandsBuilder(Arrays.asList(s), aInfo);
						CommandsExecutor.runCommands(builder);	
					}			
					cpt++;
				}
			}
			if(cpt == 0 && !mute) sm.sendMessage(receiver, MessageMain.getInstance().getMessage(SCore.plugin, Message.NO_ENTITY_HIT));

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		String around= "MOB_AROUND {distance} {muteMsgIfNoEntity true or false} {Your commands here}";
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
		return "MOB_AROUND {distance} {muteMsgIfNoEntity true or false} {Your commands here}";
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
