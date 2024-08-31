package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* ADDLORE {slot} {text} */
public class Removelore extends PlayerCommand {

    public Removelore() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, 0);
        CommandSetting line = new CommandSetting("line", 1, Integer.class, 0);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(line);
        setNewSettingsMode(true);
        setCanExecuteCommands(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        List<String> args = sCommandToExec.getOtherArgs();

        ItemStack item;
        ItemMeta itemmeta;
        ArrayList<String> list;

        int slot = (int) sCommandToExec.getSettingValue("slot");
        int line = (int) sCommandToExec.getSettingValue("line");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
        if (item == null || item.getType() == Material.AIR) return;

        itemmeta = item.getItemMeta();

        list = (ArrayList<String>) itemmeta.getLore();

        if(list == null){
            list = new ArrayList<>();
        }
        if(list.size() > line) list.remove(line);
        else return;

        itemmeta.setLore(list);
        item.setItemMeta(itemmeta);
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac2 = checkInteger(args.get(1), isFinalVerification, getTemplate());
        if (!ac2.isValid()) return Optional.of(ac2.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REMOVELORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REMOVELORE {slot} {line}";
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
