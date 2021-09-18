package com.ssomar.score.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
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
			default:
				sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop ]"));
				break;
			}
		}
		else {
			sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop ]"));
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
		
				Collections.sort(arguments);
				return arguments;
			}
		}
		return null;
	}

}
