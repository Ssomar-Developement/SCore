package com.ssomar.score.commands.score;

import com.ssomar.score.sobject.SObject;
import com.ssomar.score.sobject.SObjectManager;
import com.ssomar.score.sobject.SObjectWithFileEditable;
import com.ssomar.score.splugin.SPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.*;


public class PathCommand<X extends SPlugin, Y extends SObjectManager<Z>, Z extends SObjectWithFileEditable> extends CustomCommandWithManagerAbstract<X, Y, Z> {


    public PathCommand(X sPlugin, Y sObjectManager) {
        super(sPlugin, sObjectManager);
    }


    public List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        commands.add("path");
        return commands;
    }

    public void run(CommandSender sender, String command, String[] args, String typedCommand) {
        switch (command) {
            case "path":
                if (args.length >= 1) {

                    List<String> arguments = new ArrayList<>(Arrays.asList(args));

                    String eiID = arguments.get(0);

                    Optional<Z> obj = getSObjectManager().getLoadedObjectWithID(eiID);
                    if (!obj.isPresent()) {
                        getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() + " &cNo object found with ID: " + eiID);
                        return;
                    }

                    Z sObject = obj.get();
                    if (!(sObject instanceof SObjectWithFileEditable)) {
                        getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() + " &cThe object with ID: " + eiID + " has no file linked.");
                        return;
                    }
                    SObjectWithFileEditable sObjectWithFileEditable = (SObjectWithFileEditable) sObject;
                    getSm().sendMessage(sender, ChatColor.GREEN + getSPlugin().getNameWithBrackets() + " &aThe path of &e" + sObject.getId() + " &ais &e" + sObjectWithFileEditable.getPath() + "&a.");
                } else
                    getSm().sendMessage(sender, ChatColor.RED + getSPlugin().getNameWithBrackets() + " &cNot enough args /" + getSPlugin().getShortName().toLowerCase() + " {id}");
                break;
        }
    }

    public List<String> getArguments(String command, String[] args) {
        ArrayList<String> arguments = new ArrayList<String>();
        switch (args[0]) {
            case "path":
                for (SObject item : getSObjectManager().getLoadedObjects()) {
                    arguments.add(item.getId());
                }
                Collections.sort(arguments);
                return arguments;
        }
        return arguments;
    }
}
