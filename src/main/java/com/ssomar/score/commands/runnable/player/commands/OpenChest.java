package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* BURN {timeinsecs} */
public class OpenChest extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        //OPENCHEST world x y z
        World world = Bukkit.getWorld(args.get(0));
        if(world == null) return;

        Double x = NTools.getDouble(args.get(1)).get();
        Double y = NTools.getDouble(args.get(2)).get();
        Double z = NTools.getDouble(args.get(3)).get();

        Location location = new Location(world,x,y,z);

        if(location.getBlock().getState() instanceof Chest){
            Chest chest = (Chest) location.getBlock().getState();
            receiver.openInventory(chest.getBlockInventory());
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 4) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkInteger(args.get(2), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac3 = checkInteger(args.get(3), isFinalVerification, getTemplate());
        if (!ac3.isValid()) return Optional.of(ac.getError());


        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("OPENCHEST");
        return names;
    }

    @Override
    public String getTemplate() {
        return "OPENCHEST {world} {x} {y} {z}";
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
