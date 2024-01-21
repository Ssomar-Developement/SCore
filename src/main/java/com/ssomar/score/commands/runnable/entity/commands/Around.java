package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ssomar.score.commands.runnable.player.commands.Around.aroundExecution;

public class Around extends EntityCommand {

    public Around() {
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {
        List<String> newArgs = new ArrayList<>(args);
        if(newArgs.size() >= 2){
            if (!(newArgs.get(1).equalsIgnoreCase("false") || newArgs.get(1).equalsIgnoreCase("true"))) {
                newArgs.add(1, "false");
            }
        }
        aroundExecution(receiver, newArgs, aInfo, false);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

       return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("AROUND");
        return names;
    }

    @Override
    public String getTemplate() {
        return "AROUND {distance} {Your commands here}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_PURPLE;
    }

}
