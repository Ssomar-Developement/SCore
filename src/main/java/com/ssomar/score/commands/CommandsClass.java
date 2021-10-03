package com.ssomar.score.commands;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Charsets;
import com.ssomar.score.projectiles.ProjectilesGUIManager;
import com.ssomar.score.projectiles.ProjectilesManager;
import com.ssomar.score.projectiles.types.CustomArrow;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ssomar.executableblocks.blocks.activators.ActivatorEB;
import com.ssomar.executableitems.items.activators.ActivatorEI;
import com.ssomar.score.SCore;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.sobject.sactivator.SActivator;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;

public class CommandsClass implements CommandExecutor, TabExecutor{

	private final SendMessage sm = new SendMessage();

	private final SCore main;

	public CommandsClass(SCore main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(args.length>0) {

			switch(args[0]) {
			case "reload":
				this.runCommand(sender, "reload", args);
				break;
			case "inspect-loop":
				this.runCommand(sender, "inspect-loop", args);
				break;
			case "projectiles":
				ProjectilesGUIManager.getInstance().startEditing((Player) sender);
				break;
			case "projectiles-create":
				this.runCommand(sender, "projectiles-create", args);
				break;
			case "projectiles-delete":
				this.runCommand(sender, "projectiles-delete", args);
				break;
			default:
				sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop | projectiles | projectiles-create | projectiles-delete ]"));
				break;
			}
		}
		else {
			sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop | projectiles ]"));
		}
		return true;
	}

	public void runCommand(CommandSender sender, String command, String[] fullArgs) {

		String [] args;
		if(fullArgs.length>1) {
			args = new String[fullArgs.length-1];
			for(int i = 0; i<fullArgs.length;i++) {
				if(i==0) continue;
				else args[i-1]= fullArgs[i];
			}
		}
		else args = new String[0];
		Player player = null;
		if((sender instanceof Player)) {
			player = (Player) sender;
			if(!(player.hasPermission("score.cmd."+command) || player.hasPermission("score.cmds") || player.hasPermission("score.*"))) {
				player.sendMessage(StringConverter.coloredString("&4[SCore] &cYou don't have the permission to execute this command: "+"&6score.cmd."+command));
				return;
			}
		}

		switch(command) {

		case "reload":
			main.onReload();
			sm.sendMessage(sender, ChatColor.GREEN+"SCore has been reload");	
			System.out.println("SCore reloaded !");
			break;
		case "inspect-loop":
			Map<SActivator, Integer> loops = LoopManager.getInstance().getLoopActivators();
			sm.sendMessage(sender, " ");
			sm.sendMessage(sender, "&8==== &7SCore contains &e"+loops.size()+" &7loop(s) &8====");
			sm.sendMessage(sender, "&7&o(The loop of ExecutableItems requires more performance when there are many players)");
			sm.sendMessage(sender, " ");
			for(SActivator sAct : loops.keySet()) {
				int delay = sAct.getDelay();
				if(!sAct.isDelayInTick()) delay = delay * 20;
				if(SCore.hasExecutableItems && sAct instanceof ActivatorEI) {
					sm.sendMessage(sender,"&bEI LOOP > &7item: &e"+sAct.getParentObjectID()+" &7delay: &e"+delay+ " &7(in ticks)");
				}
				else if(SCore.hasExecutableBlocks && sAct instanceof ActivatorEB) {
					sm.sendMessage(sender, "&aEB LOOP > &7block: &e"+sAct.getParentObjectID()+" &7delay: &e"+delay+ " &7(in ticks)");
				}
			}
			sm.sendMessage(sender, " ");
			
			break;
		case "projectiles-create":
			if(player != null) {
				if(args.length == 1) {
					if(ProjectilesManager.getInstance().getProjectileWithID(args[0]) != null) {
						player.sendMessage(StringConverter.coloredString("&4[SCore] &cError this id already exist re-enter &6/score projectiles-create ID &7&o(ID is the id you want for your new projectile)")) ;
					}
					else {
						String id = args[0];
						File file = new File(SCore.getPlugin().getDataFolder()+"/projectiles/"+id+".yml");
						try {
							file.createNewFile();
						} catch (IOException var17) {}

						FileConfiguration config = YamlConfiguration.loadConfiguration(file);
						config.set("type", "ARROW");

						try {
							Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

							try {
								writer.write(config.saveToString());
							} finally {
								writer.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

						for(SProjectiles p : ProjectilesManager.getInstance().getProjectiles()) p.resetRequestChat();

						SProjectiles proj = new CustomArrow(id, file, false);
						proj.openConfigGUI(player);
						ProjectilesManager.getInstance().addProjectile(proj);
					}
				}
				else {
					player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new projectile type &e/score projectiles-create ID &7&o(ID is the id you want for your new projectile)")) ;
				}
			}
			break;
		case "projectiles-delete":
				if(args.length == 2) {
					if(!args[1].equalsIgnoreCase("confirm")) {
						sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score projectiles-delete {projID} confirm"));
						return;
					}

					if(!ProjectilesManager.getInstance().deleteProjectile(args[0])) {
						sender.sendMessage(StringConverter.coloredString("&4[SCore] &cProjectile file not found (&6"+args[0]+".yml&c) so it can't be deleted !"));
						return;
					}
					else sender.sendMessage(StringConverter.coloredString("&2[SCore] &aProjectile file (&e"+args[0]+".yml&a) deleted !"));
				}
				else sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score projectiles-delete {projID} confirm"));
				break;
		default:
			break;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("score")) {
			ArrayList<String> arguments = new ArrayList<>();
			if (args.length == 1) {

				arguments.add("reload");
				arguments.add("inspect-loop");
				arguments.add("projectiles");
				arguments.add("projectiles-create");
				arguments.add("projectiles-delete");
		
				Collections.sort(arguments);
				return arguments;
			}
		}
		return null;
	}

}
