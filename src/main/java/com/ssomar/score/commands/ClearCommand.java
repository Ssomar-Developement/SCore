package com.ssomar.score.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ssomar.score.SCore;
import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.CommandsManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class ClearCommand {

	public static void clearCmd(SPlugin sPlugin, CommandSender sender, String [] args) {
		String name = "";
		if(sender instanceof Player) {
			if(args.length > 1) {
				if(Bukkit.getPlayer(args[0]) == null)  {
					sender.sendMessage(StringConverter.coloredString("&4"+sPlugin.getNameDesign()+" &cInvalid playername."));	
					return;
				}
				else name = Bukkit.getPlayer(args[0]).getName();
			}
			else name = ((Player)sender).getName();
		}
		else {
			if(args.length<1) {
				sender.sendMessage(StringConverter.coloredString("&4"+sPlugin.getNameDesign()+" &c/ei clear {playername}."));	
				return;
			}
			if(Bukkit.getPlayer(args[0]) == null)  {
				sender.sendMessage(StringConverter.coloredString("&4"+sPlugin.getNameDesign()+" &cInvalid playername."));	
				return;
			}
			else name = Bukkit.getPlayer(args[0]).getName();
		}
		if(CommandsManager.getInstance().getDelayedCommands().containsKey(name)) {
			for(UUID uuid : CommandsManager.getInstance().getDelayedCommands().get(name).keySet()){
				SCore.plugin.getServer().getScheduler().cancelTask(CommandsManager.getInstance().getDelayedCommands().get(name).get(uuid));
			}
			CommandsManager.getInstance().getDelayedCommands().remove(name);
		}
		ActionbarHandler.getInstance().removeActionbars(Bukkit.getPlayer(name));
		sender.sendMessage(StringConverter.coloredString("&2"+sPlugin.getNameDesign()+" &aSuccesfully clear the user: &e"+name));	
	}
}
