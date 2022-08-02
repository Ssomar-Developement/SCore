package com.ssomar.score.commands.clear;

import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.CommandsHandler;
import com.ssomar.score.features.custom.cooldowns.CooldownsManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ClearCommand {

    public static void clearCmd(SPlugin sPlugin, CommandSender sender, String[] args) {
        UUID pUUID = null;

        /* Target of the clear */
        if (sender instanceof Player) {
            if (args.length >= 1) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage(StringConverter.coloredString("&4" + sPlugin.getNameDesign() + " &cInvalid playername."));
                    return;
                } else pUUID = Bukkit.getPlayer(args[0]).getUniqueId();
            } else pUUID = ((Player) sender).getUniqueId();
        } else {
            if (args.length < 1) {
                sender.sendMessage(StringConverter.coloredString("&4" + sPlugin.getNameDesign() + " &cERROR &6/" + sPlugin.getShortName().toLowerCase() + " clear {playername}."));
                return;
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(StringConverter.coloredString("&4" + sPlugin.getNameDesign() + " &cInvalid playername."));
                return;
            } else pUUID = Bukkit.getPlayer(args[0]).getUniqueId();
        }

        ClearType clearType = ClearType.ALL;
        if(args.length >= 2){
            try{
                clearType = ClearType.valueOf(args[1]);
            }catch (Exception e){}
        }

        Player player = Bukkit.getPlayer(pUUID);

        switch (clearType){

            case ALL:
                CommandsHandler.getInstance().removeAllDelayedCommands(pUUID);
                CooldownsManager.getInstance().removeCooldownsOf(pUUID);
                ActionbarHandler.getInstance().removeActionbars(player);
                break;
            case DELAYED_COMMANDS:
                CommandsHandler.getInstance().removeAllDelayedCommands(pUUID);
                break;
            case COOLDOWNS:
                CooldownsManager.getInstance().removeCooldownsOf(pUUID);
                break;
            case ACTIONBARS:
                ActionbarHandler.getInstance().removeActionbars(player);
                break;
        }
        sender.sendMessage(StringConverter.coloredString("&2" + sPlugin.getNameDesign() + " &aSuccesfully clear the user: &e" + player.getName()));

    }
}
