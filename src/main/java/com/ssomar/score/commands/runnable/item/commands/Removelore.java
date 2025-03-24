package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class Removelore extends ItemMetaCommand {

    public Removelore() {
        CommandSetting line = new CommandSetting("line", -1, Integer.class, 0);
        List<CommandSetting> settings = getSettings();
        settings.add(line);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec) {
        ArrayList<String> list;

        int line = (int) sCommandToExec.getSettingValue("line");

        ItemMeta itemmeta = dynamicMeta.getMeta();

        list = (ArrayList<String>) itemmeta.getLore();

        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.size() > line) list.remove(line);
        else return;

        itemmeta.setLore(list);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REMOVE_LORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REMOVE_LORE line:0";
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
