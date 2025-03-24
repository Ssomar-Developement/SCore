package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemPotionColor extends ItemMetaCommand {

    public SetItemPotionColor() {
        CommandSetting String = new CommandSetting("color", -1, Integer.class, 0);
        List<CommandSetting> settings = getSettings();
        settings.add(String);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dMeta, SCommandToExec sCommandToExec) {
        int namespace = (int) sCommandToExec.getSettingValue("color");

        if(!(dMeta.getMeta() instanceof PotionMeta)) return;

        PotionMeta itemmeta = (PotionMeta) dMeta.getMeta();
        itemmeta.setColor(Color.fromRGB(namespace));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_POTIONCOLOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_POTIONCOLOR color:12354";
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
