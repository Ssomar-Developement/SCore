package com.ssomar.score.commands.score;

import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.numbers.NTools;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CustomCommandAbstract<T extends SPlugin> {

    @Getter
    private T sPlugin;

    @Getter
    private static List<String> argumentsQuantity = new ArrayList<>();
    @Getter
    private static List<String> argumentsUsage = new ArrayList<>();

    @Getter
    private final SendMessage sm = new SendMessage();

    public CustomCommandAbstract(T sPlugin) {
        this.sPlugin = sPlugin;
        argumentsQuantity.add("1");
        argumentsQuantity.add("3");
        argumentsQuantity.add("5");
        argumentsQuantity.add("10");
        argumentsQuantity.add("25");

        argumentsUsage.add("5");
        argumentsUsage.add("10");
        argumentsUsage.add("20");
        argumentsUsage.add("50");
        argumentsUsage.add("100");
    }

    public abstract void run(CommandSender sender, String command, String[] args, String typedCommand);

    public abstract List<String> getCommands();

    public abstract List<String> getArguments(String command, String[] args);


    public Optional<Integer> checkAmount(CommandSender sender, String amount) {
        if (!NTools.getInteger(amount).isPresent()) {
            sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets()+" Invalid amount : " + amount);
            return Optional.empty();
        }
        return NTools.getInteger(amount);
    }

    public Optional<Double> checkDouble(CommandSender sender, String number) {
        if (!NTools.getDouble(number).isPresent()) {
            sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets()+" Invalid number : " + number);
            return Optional.empty();
        }
        return NTools.getDouble(number);
    }

    public Optional<Integer> checkSlot(CommandSender sender, String amount) {
        if (!NTools.getInteger(amount).isPresent()) {
            sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets() +" Invalid slot : " + amount);
            return Optional.empty();
        }
        if (NTools.getInteger(amount).get() > 40) {
            sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets()+" There is no slot > 40 !");
            return Optional.empty();
        }
        return NTools.getInteger(amount);
    }

    public Optional<Optional<World>> checkWorld(CommandSender sender, String world) {
        if (world == null || world.isEmpty()) {
            return Optional.of(Optional.empty());
        }

        Optional<World> worldOptional = AllWorldManager.getWorld(world);
        if (!worldOptional.isPresent()) {
            sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets()+" World " + world + " not found");
            return Optional.empty();
        } else {
            return Optional.of(worldOptional);
        }
    }

    public Optional<Optional<Player>> checkPlayer(CommandSender sender, String player, boolean giveOfflinePlayer) {
        if (player.trim().equalsIgnoreCase("all")) {
            return Optional.of(Optional.empty());
        } else {
            Player p = Bukkit.getServer().getPlayerExact(player);
            if (p == null) {
                if(giveOfflinePlayer) sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets()+" Player &6"+player+" &cnot online &7(You enabled the feature giveOffline, so the item will be given when the player reconnects)");
                else sm.sendMessage(sender, ChatColor.RED + sPlugin.getNameWithBrackets()+" Player not found or not online: &6" + player );
                return Optional.empty();
            }
            return Optional.of(Optional.of(p));
        }
    }
}
