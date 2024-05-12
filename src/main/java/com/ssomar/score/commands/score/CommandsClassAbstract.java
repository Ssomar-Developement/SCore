package com.ssomar.score.commands.score;

import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.utils.strings.StringJoiner;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandsClassAbstract<T extends SPlugin> implements CommandExecutor, TabExecutor {


    @Getter
    private T sPlugin;

    @Getter
    private final SendMessage sm = new SendMessage();

    @Getter
    private static List<String> argumentsQuantity = new ArrayList<>();
    @Getter
    private static List<String> argumentsUsage = new ArrayList<>();

    private List<String> commands = new ArrayList<>();

    public CommandsClassAbstract(T sPlugin) {
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

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] fullArgs) {
        if (fullArgs.length > 0) {
            String commandName = fullArgs[0].toLowerCase();
            if (commands.contains(commandName)) {

                //SsomarDev.testMsg("Command: "+commandName+" perm:"+sPlugin.getShortName().toLowerCase() + ".cmd." + commandName, true);
                String[] args;
                String typedCommand = command.getName()+" ";
                if (fullArgs.length > 1) {
                    args = new String[fullArgs.length - 1];
                    for (int i = 0; i < fullArgs.length; i++) {
                        typedCommand += fullArgs[i] + " ";
                        if (i == 0) continue;
                        else args[i - 1] = fullArgs[i];
                    }
                } else args = new String[0];
                Player player = null;
                if ((sender instanceof Player)) {
                    player = (Player) sender;
                    if (!(player.hasPermission(sPlugin.getShortName().toLowerCase() + ".cmd." + commandName) || player.hasPermission(sPlugin.getShortName().toLowerCase() + ".cmds") || player.hasPermission(sPlugin.getShortName().toLowerCase() + ".*"))) {
                        player.sendMessage(StringConverter.coloredString("&4" + sPlugin.getNameWithBrackets() + " &cYou don't have the permission to execute this command: &6" + sPlugin.getShortName().toLowerCase() + ".cmd." + commandName));
                        return true;
                    }
                }

                this.runCommand(sender, sender instanceof Player ? (Player) sender : null, commandName, args, typedCommand);
            } else {
                sender.sendMessage(StringConverter.coloredString("&c" + sPlugin.getNameWithBrackets() + " &cInvalid argument! Usage: /" + sPlugin.getShortName().toLowerCase() + " &8[ &7" + StringJoiner.join(getPermittedCommands(sender), " &c| &7") + " &8]"));
            }
        } else
            sender.sendMessage(StringConverter.coloredString("&c" + sPlugin.getNameWithBrackets() + " &cInvalid argument! Usage: /" + sPlugin.getShortName().toLowerCase() + " &8[ &7" + StringJoiner.join(getPermittedCommands(sender), " &c| &7") + " &8]"));

        return true;
    }

    public List<String> getPermittedCommands(CommandSender sender) {
        List<String> permittedCommands = new ArrayList<>();
        for (String cmd : commands) {
            if (sender.hasPermission(sPlugin.getShortName().toLowerCase() + ".cmd." + cmd) || sender.hasPermission(sPlugin.getShortName().toLowerCase() + ".cmds") || sender.hasPermission(sPlugin.getShortName().toLowerCase() + ".*"))
                permittedCommands.add(cmd);
        }
        return permittedCommands;
    }

    /**
     * Runs the command for the given {@link CommandSender}.
     *
     * @param sender   the {@link CommandSender} running the command.
     * @param command  the command that was entered.
     * @param fullArgs the passed command arguments.
     */
    public abstract void runCommand(final CommandSender sender, @Nullable final Player player, final String command, final String[] fullArgs, String typedCommand);

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (command.getName().equalsIgnoreCase(sPlugin.getShortName().toLowerCase())) {
            List<String> arguments = new ArrayList<>();

            if (args.length == 1) {
                arguments = getPermittedCommands(sender);

                Collections.sort(arguments);
                return arguments.stream()
                        .filter(element -> element.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .collect(Collectors.toList());
            }
            arguments = getOnTabCompleteArguments(sender, command, label, args);
            if(arguments.size() > 0) return arguments;
        }
        return null;
    }

    public abstract List<String> getOnTabCompleteArguments(CommandSender sender, Command command, String label, String[] args);

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public void addCommands(List<String> commands) {
        this.commands.addAll(commands);
    }
}