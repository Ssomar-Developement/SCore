package com.ssomar.score.commands.score;

import com.ssomar.score.sobject.SObjectBuildable;
import com.ssomar.score.sobject.SObjectWithFile;
import com.ssomar.score.sobject.SObjectWithFileManager;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.strings.StringSetting;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

import static com.ssomar.score.commands.score.GiveCommand.runOfflineCommand;

public class GiveFolderCommand<X extends SPlugin, Y extends SObjectWithFileManager<Z>, Z extends SObjectWithFile & SObjectBuildable> extends CustomCommandWithManagerAbstract<X, Y, Z> {


    public GiveFolderCommand(X sPlugin, Y sObjectManager) {
        super(sPlugin, sObjectManager);
    }

    /**
     * Give ExecutableItems of a folder to a player
     *
     * @param sender   The sender of the command
     * @param folder
     * @param amount
     * @param player   , if empty it will give the item to all players on the server
     * @param settings
     */
    public void giveFolder(CommandSender sender, String folder, String amount, String player, Map<String, Object> settings, String world, boolean giveOfflinePlayer, String commandToRunIfPlayerIsOffline) {
        List<Z> objectOpt = checkFolder(sender, folder);
        Optional<Integer> amountOpt = checkAmount(sender, amount);
        if (!amountOpt.isPresent()) return;
        Optional<Optional<Player>> playerOpt = checkPlayer(sender, player, giveOfflinePlayer);
        Optional<Optional<World>> worldOpt = checkWorld(sender, world);

        if (objectOpt.size() == 0 || !worldOpt.isPresent()) {
            return;
        } else if (!playerOpt.isPresent()) {
            runOfflineCommand(player, giveOfflinePlayer, commandToRunIfPlayerIsOffline);
            return;
        }

        for (Z eI : objectOpt) {
            new GiveCommand<X, Y, Z>(getSPlugin(), getSObjectManager()).give(playerOpt.get(), eI, amountOpt.get(), settings, worldOpt.get());
        }
        getSm().sendMessage(sender, getSPlugin().getNameDesign() + " &aFolder: &e" + folder + " &agiven to &e" + player);
    }

    public List<Z> checkFolder(CommandSender sender, String folder) {
        List<Z> oOpt = getSObjectManager().getObjectsOfFolder(folder);
        if (oOpt.size() == 0)
            getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameDesignWithBrackets() + " Folder " + folder + " not found or empty");
        return oOpt;
    }

    @Override
    public void run(CommandSender sender, String command, String[] args, String typedCommand) {
        switch (command) {
            case "givefolder":
                if (args.length >= 2) {
                    List<String> arguments = new ArrayList<>(Arrays.asList(args));
                    Map<String, Object> settings = StringSetting.extractSettingsAndRebuildCorrectly(arguments, 1, new ArrayList<>());
                    String pName = arguments.get(0);
                    String eiID = arguments.get(1);
                    String quantity = "1";
                    if (arguments.size() >= 3) {
                        quantity = arguments.get(2);
                    }
                    boolean giveOfflinePlayer = true;
                    if (arguments.size() >= 4) {
                        giveOfflinePlayer = Boolean.parseBoolean(arguments.get(3));
                    }
                    new GiveFolderCommand(getSPlugin(), getSObjectManager()).giveFolder(sender, eiID, quantity, pName, settings, "", giveOfflinePlayer, typedCommand);
                } else
                   getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() +" &cNot enough args &6/"+getSPlugin().getShortName().toLowerCase()+" givefolder {playername} {folder} {quantity} [giveOfflinePlayer]");
                break;

        }
    }

    @Override
    public List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        commands.add("givefolder");
        return commands;
    }

    @Override
    public List<String> getArguments(String command, String[] args) {
        ArrayList<String> arguments = new ArrayList<String>();
        switch (args[0]) {
            case "givefolder":
                if (args.length == 3) {
                    return getSObjectManager().getFoldersNames();
                } else if (args.length == 4) {
                    arguments.add("0");
                    arguments.add("1");
                    arguments.add("3");
                    arguments.add("5");
                    arguments.add("10");
                    arguments.add("25");
                    arguments.add("50");

                    return arguments;
                }
                break;
        }
        return arguments;
    }

    @Override
    public Optional<Integer> checkAmount(CommandSender sender, String amount) {
        if (!NTools.getInteger(amount).isPresent()) {
            getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() + " Invalid amount : " + amount);
            return Optional.empty();
        }
        if (NTools.getInteger(amount).get() > getSPlugin().getPluginConfig().getIntSetting("giveLimit", 100)) {
            getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() + " Quantity > " + getSPlugin().getPluginConfig().getIntSetting("giveLimit", 100) + " is blocked for security !");
            return Optional.empty();
        }
        return NTools.getInteger(amount);
    }
}
