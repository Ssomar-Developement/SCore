package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ssomar.score.SsomarDev;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.jetbrains.annotations.NotNull;

/* AROUND {distance} {true or false} {Your commands here} */
public class Around extends BlockCommand{
	@Override
	public String verify(List<String> args) {
		String error = "";

		String around = "AROUND {distance} [affectThePlayerThatActivesTheActivator true or false] {Your commands here}";

		if(args.size() < 2) error = notEnoughArgs+around;
		else if(args.size() > 2) { 
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
		names.add("AROUND");
		return names;
	}

	@Override
	public String getTemplate() {
		return "AROUND {distance} [affectThePlayerThatActivesTheActivator true or false] {Your commands here}";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.LIGHT_PURPLE;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.DARK_PURPLE;
	}

	@Override
	public void run(Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo) {
		BukkitRunnable runnable = new BukkitRunnable() {
			@Override
			public void run() {
				try {
					double distance = Double.parseDouble(args.get(0));
					boolean affectThePlayerThatActivesTheActivator = true;
					if(args.get(1).equalsIgnoreCase("false")) affectThePlayerThatActivesTheActivator = false;

					for (Entity e: block.getWorld().getNearbyEntities(block.getLocation(), distance, distance, distance)) {
						if(e instanceof Player) {
							Player target =  (Player) e;
							if(target.hasMetadata("NPC") || (!affectThePlayerThatActivesTheActivator && (p != null && p.equals(target)))) continue;

							StringPlaceholder sp = new StringPlaceholder();
							sp.setAroundTargetPlayerPlcHldr(target.getUniqueId());

							ActionInfo aInfo2 = aInfo.clone();
							aInfo2.setReceiverUUID(target.getUniqueId());

							/* regroup the last args that correspond to the commands */
							StringBuilder prepareCommands = new StringBuilder();
							for(String s : args.subList(1, args.size())) {
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
									s = s.substring(1);
								}
								while(s.endsWith(" ")) {
									s = s.substring(0, s.length()-1);
								}
								if(s.startsWith("/")) s = s.substring(1);

								s = sp.replacePlaceholder(s);

								PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(Collections.singletonList(s), aInfo2);
								CommandsExecutor.runCommands(builder);}				
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTask(SCore.getPlugin());
	}
}
