package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemInterface;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EICooldown extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();
        //EICOOLDOWN PLAYER ID SECONDS TICKS {optional activator id}
        OfflinePlayer player = Bukkit.getOfflinePlayer(args.get(0));
        String id = args.get(1);
        int number = NTools.getInteger(args.get(2)).get();
        boolean ticks = Boolean.parseBoolean(args.get(3));

        List<ExecutableItemInterface> eiAffected = new ArrayList<>();
        if (id.equalsIgnoreCase("all")) {
            eiAffected.addAll(ExecutableItemsAPI.getExecutableItemsManager().getAllExecutableItems());
        } else {
            Optional<ExecutableItemInterface> eiOpt = ExecutableItemsAPI.getExecutableItemsManager().getExecutableItem(id);
            if (eiOpt.isPresent()) {
                eiAffected.add(eiOpt.get());
            }
        }

        for (ExecutableItemInterface ei : eiAffected) {
            if (args.size() < 5) {
                ei.addCooldown(player.getPlayer(), number, ticks);
            } else {
                ei.addCooldown(player.getPlayer(), number, ticks, args.get(4));
            }
        }


    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac1 = checkInteger(args.get(2), isFinalVerification, getTemplate());
        if (!ac1.isValid()) return Optional.of(ac1.getError());

        ArgumentChecker ac2 = checkBoolean(args.get(3), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("EICOOLDOWN");
        return names;
    }

    @Override
    public String getTemplate() {
        return "EICOOLDOWN {PLAYER} {ID} {SECONDS} {boolean TICKS} [optional activator]";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }


}
