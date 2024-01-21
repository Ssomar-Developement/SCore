package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETITEMCUSTOMMODELDATA {slot} {customModelData} */
public class SetItemCustomModelData extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        ItemStack item;
        ItemMeta itemmeta;

        int slot = Integer.valueOf(args.get(0));

        try {
            if(slot == -1) item = receiver.getInventory().getItemInMainHand();
            else item = receiver.getInventory().getItem(slot);

            itemmeta = item.getItemMeta();
        } catch (NullPointerException e) {
            return;
        }

        int cmd = Double.valueOf(args.get(1)).intValue();

        itemmeta.setCustomModelData(cmd);

        item.setItemMeta(itemmeta);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETITEMCUSTOMMODELDATA");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETITEMCUSTOMMODELDATA {slot} {customModelData}";
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
