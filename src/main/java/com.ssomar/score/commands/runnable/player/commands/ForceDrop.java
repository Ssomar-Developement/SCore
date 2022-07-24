package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.fly.FlyManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForceDrop extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        int slot = Double.valueOf(args.get(0)).intValue();

        PlayerInventory inventory = receiver.getInventory();

        if (slot == -1)
            slot = inventory.getHeldItemSlot();

        ItemStack toDrop = inventory.getItem(slot);
        if (toDrop != null) {
            inventory.clear(slot);
            receiver.getLocation().getWorld().dropItem(receiver.getLocation(), toDrop);
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        String error = "";

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkSlot(args.get(0), isFinalVerification, getTemplate());
        if(!ac.isValid()) return Optional.of(ac.getError());

        return  error.isEmpty() ? Optional.empty() : Optional.of(error);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORCEDROP");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORCEDROP {slot -1 for main_hand}";
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
