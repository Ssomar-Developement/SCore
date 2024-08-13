package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* ADDLORE {slot} {text} */
public class Addlore extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();

        ItemStack item;
        ItemMeta itemmeta;
        ArrayList<String> list;

        int slot = NTools.getInteger(args.get(0)).get();

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
        if (item == null || item.getType() == Material.AIR) return;

        itemmeta = item.getItemMeta();

        StringBuilder build = new StringBuilder();

        for (int i = 1; i < args.size(); i++) {
            build.append(args.get(i) + " ");
        }

        list = (ArrayList<String>) itemmeta.getLore();

        if(list == null){
            list = new ArrayList<>();
        }
        list.add(StringConverter.coloredString(build.toString()));

        itemmeta.setLore(list);
        item.setItemMeta(itemmeta);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADDLORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADDLORE {slot} {text dont need brackets}";
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
