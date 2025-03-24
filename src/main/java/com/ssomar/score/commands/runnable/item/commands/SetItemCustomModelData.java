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

public class SetItemCustomModelData extends ItemMetaCommand {

    public SetItemCustomModelData() {
        CommandSetting customModelData = new CommandSetting("customModelData", -1, Integer.class, 2);
        List<CommandSetting> settings = getSettings();
        settings.add(customModelData);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec) {

        int customModelData = (int) sCommandToExec.getSettingValue("customModelData");

        ItemMeta itemmeta = dynamicMeta.getMeta();

        itemmeta.setCustomModelData(customModelData);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_CUSTOM_MODEL_DATA");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_CUSTOM_MODEL_DATA customModelData:2";
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
