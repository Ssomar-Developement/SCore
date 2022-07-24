package com.ssomar.score.commands;

import com.ssomar.executableitems.ExecutableItems;
import com.ssomar.score.linkedplugins.LinkedPlugins;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.scoretestrecode.sobject.NewSObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropCommand {

    public DropCommand(SPlugin sPlugin, CommandSender sender, String[] args) {
        try {
            NewSObject sObject;
            if ((sObject = LinkedPlugins.getSObject(sPlugin, args[0])) == null) {
                sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " "+sPlugin.getObjectName()+" " + args[0] + " not found");
                return;
            }

            int qty;
            if (args.length == 1) qty = 1;
            else {
                if (!args[1].matches("\\d+")) {
                    sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " Quantity " + args[1] + " is invalid.");
                    return;
                }
                qty = Integer.parseInt(args[1]);
            }

            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length <= 2) {
                    runDrop(sObject, Integer.parseInt(args[1]), p.getLocation());
                    System.out.println(sPlugin.getNameDesign() + " Succesfully run /" + sPlugin.getShortName().toLowerCase() + " drop " + args[0] + " " + qty + " " + p.getWorld().getName() + " " + (int) p.getLocation().getX() + " " + (int) p.getLocation().getY() + " " + (int) p.getLocation().getZ() + " ");
                    return;
                }
            }

            if (Bukkit.getServer().getWorld(args[2]) == null) {
                sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " WORLD " + args[2] + " is invalid.");
                return;
            }
            try {
                Double.valueOf(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " X " + args[3] + " is invalid.");
                return;
            }
            try {
                Double.valueOf(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " Y " + args[3] + " is invalid.");
                return;
            }
            try {
                Double.valueOf(args[5]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " Z " + args[3] + " is invalid.");
                return;
            }
            runDrop(sObject, qty, new Location(Bukkit.getServer().getWorld(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5])));
            ExecutableItems.plugin.getLogger().fine(sPlugin.getNameDesign() + " Succesfully run /" + sPlugin.getShortName().toLowerCase() + " drop " + args[0] + " " + qty + " " + args[2] + " " + args[3] + " " + args[4] + " " + args[5] + " ");
            return;
        } catch (ArrayIndexOutOfBoundsException error) {
            sender.sendMessage(ChatColor.RED + sPlugin.getNameDesign() + " SCommand invalid, verify your args /" + sPlugin.getShortName().toLowerCase() + " drop {id} {quantity} {world} {x} {y} {z}");
        }

    }

    public void runDrop(NewSObject sObject, int qty, Location loc) {
        sObject.dropItem(loc, qty);
    }


}
