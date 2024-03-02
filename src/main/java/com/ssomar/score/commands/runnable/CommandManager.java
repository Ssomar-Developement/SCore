package com.ssomar.score.commands.runnable;

import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CommandManager<T extends SCommand> {

    @Getter
    @Setter
    private List<T> commands;

    /** Get the associated custom command of the entry if there is one **/
    public Optional<T> getCommand(String entry) {
        for (T command : this.commands) {
            for (String name : command.getNames()) {
                if (entry.toUpperCase().startsWith(name.toUpperCase())) {
                    return Optional.of(command);
                }
            }
        }
        return Optional.empty();
    }

    /** Extract the arguments of the entry for a specific custom command **/
    public List<String> getArgs(T command, String entry) {
        for (String name : command.getNames()) {
            if (entry.startsWith(name)) {
                entry = entry.substring(name.length());
                break;
            }
        }
        if(entry.trim().equals("")) return new ArrayList<>();
        return Arrays.asList(entry.trim().split(" "));
    }

    /** Verify if the args are correct for a specific command **/
    public Optional<String> verifArgs(@NotNull T command, List<String> args) {
        return command.verify(args, false);
    }

    public Optional<String> verifCommand(String entry) {
        Optional<T> commandOpt = getCommand(entry);
        if (commandOpt.isPresent()) {
            return verifCommand(commandOpt.get(), entry);
        }
        return Optional.empty();
    }

    public Optional<String> verifCommand(T command, String entry) {
        List<String> args = getArgs(command, entry);

        Optional<String> error = this.verifArgs(command, args);
        if (error.isPresent()) {
            return Optional.of("&6>>" + " " + error.get());
        }

        return Optional.empty();
    }

    public List<String> getCommandsVerified(SPlugin sPlugin, @NotNull List<String> entries, List<String> errorList, String id) {
        List<String> result = new ArrayList<>();

        for (String entry : entries) {
            Optional<T> commandOpt = getCommand(entry);
            if (commandOpt.isPresent() && !entry.contains("+++")) {
                T command = commandOpt.get();
                Optional<String> error = verifCommand(command, entry);
                error.ifPresent(value -> errorList.add("&cERROR, Invalid command &7&o(Command: "+entry+")  &7&o(ID: " + id+ ") "+value));
            }
            result.add(entry);
        }
        return result;
    }

    public Map<String, String> getCommandsDisplay() {
        Map<String, String> result = new HashMap<>();
        for (SCommand c : this.commands) {

            ChatColor extra = c.getExtraColor();
            if (extra == null) extra = ChatColor.DARK_PURPLE;

            ChatColor color = c.getColor();
            if (color == null) color = ChatColor.LIGHT_PURPLE;

            result.put(extra + "[" + color + "&l" + c.getNames().get(0) + extra + "]", c.getTemplate());
        }
        return result;
    }
}
