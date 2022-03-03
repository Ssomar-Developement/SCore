package com.ssomar.score.commands;

import java.util.UUID;

import com.ssomar.score.sobject.sactivator.cooldowns.CooldownsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;

public class ClearCommand {

	public static void clearCmd(SPlugin sPlugin, CommandSender sender, String [] args) {
		UUID pUUID = null;

		if(sender instanceof Player) {
			if(args.length > 1) {
				if(Bukkit.getPlayer(args[0]) == null)  {
					sender.sendMessage(StringConverter.coloredString("&4"+sPlugin.getNameDesign()+" &cInvalid playername."));	
					return;
				}
				else  pUUID = Bukkit.getPlayer(args[0]).getUniqueId();
			}
			else  pUUID = ((Player)sender).getUniqueId();
		}
		else {
			if(args.length < 1) {
				sender.sendMessage(StringConverter.coloredString("&4"+sPlugin.getNameDesign()+" &cERROR &6/"+sPlugin.getShortName().toLowerCase()+" clear {playername}."));
				return;
			}
			if(Bukkit.getPlayer(args[0]) == null)  {
				sender.sendMessage(StringConverter.coloredString("&4"+sPlugin.getNameDesign()+" &cInvalid playername."));	
				return;
			}
			else pUUID = Bukkit.getPlayer(args[0]).getUniqueId();
		}
		CommandsHandler.getInstance().removeAllDelayedCommands(pUUID);
		CooldownsManager.getInstance().removeCooldownsOf(pUUID);
		Player player = Bukkit.getPlayer(pUUID);
		ActionbarHandler.getInstance().removeActionbars(player);
		sender.sendMessage(StringConverter.coloredString("&2"+sPlugin.getNameDesign()+" &aSuccesfully clear the user: &e"+player.getName()));	

	}
}
