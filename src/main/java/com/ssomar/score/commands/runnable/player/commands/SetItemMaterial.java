package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETITEMNAME {slot} {text} */
public class SetItemMaterial extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        ItemStack item;

        int slot = Integer.valueOf(args.get(0));

        try {
            if(slot == -1) item = receiver.getInventory().getItemInMainHand();
            else item = receiver.getInventory().getItem(slot);
        } catch (NullPointerException e) {
            return;
        }

        Material material = Material.getMaterial(args.get(1).toUpperCase());

        item.setType(material);


    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac1 = checkMaterial(args.get(1), isFinalVerification, getTemplate());
        if (!ac1.isValid()) return Optional.of(ac1.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETITEMMATERIAL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETITEMMATERIAL {slot} {material}";
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
