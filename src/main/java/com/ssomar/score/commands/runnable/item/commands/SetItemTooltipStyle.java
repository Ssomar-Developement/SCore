package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemTooltipStyle extends ItemMetaCommand {

    public SetItemTooltipStyle() {
        CommandSetting String = new CommandSetting("tooltipModel", -1, String.class, "namespace:id");
        List<CommandSetting> settings = getSettings();
        settings.add(String);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dMeta, SCommandToExec sCommandToExec) {
        String namespace = (String) sCommandToExec.getSettingValue("tooltipModel");
        NamespacedKey material = NamespacedKey.fromString(namespace);

        ItemMeta itemmeta = dMeta.getMeta();
        itemmeta.setTooltipStyle(material);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_TOOLTIPSTYLE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_TOOLTIPSTYLE tooltipModel:namespace:id";
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
