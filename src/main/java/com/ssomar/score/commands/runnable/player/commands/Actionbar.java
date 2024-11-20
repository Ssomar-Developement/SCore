package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.actionbar.ActionbarHandler;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Actionbar extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < args.size() - 1; i++) {
            if (i == 0) name = new StringBuilder(args.get(i));
            else name.append(" ").append(args.get(i));
        }

        Optional<Double> d = NTools.getDouble(args.get(args.size() - 1));
        if (!d.isPresent()) {
            /* Concat args */
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.size(); i++) {
                if (i == 0) sb = new StringBuilder(args.get(i));
                else sb.append(" ").append(args.get(i));
            }
            Utils.sendConsoleMsg(ChatColor.RED + "The time must be a number ("+sb.toString()+")");
            return;
        }
        int time = d.get().intValue();
        ActionbarHandler.getInstance().addActionbar(receiver, new com.ssomar.score.actionbar.Actionbar(StringConverter.coloredString(name.toString()), time));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ACTIONBAR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ACTIONBAR {name} {time in secs}";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.DARK_GREEN;
    }

}
