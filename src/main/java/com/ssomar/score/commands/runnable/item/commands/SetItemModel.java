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

public class SetItemModel extends ItemMetaCommand {

    public SetItemModel() {

        CommandSetting name = new CommandSetting("model", -1, String.class, "minecraft:stone");
        List<CommandSetting> settings = getSettings();
        settings.add(name);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dMeta, SCommandToExec sCommandToExec) {
        String name = (String) sCommandToExec.getSettingValue("model");
        ItemMeta itemmeta = dMeta.getMeta();

        itemmeta.setItemModel(NamespacedKey.fromString(name));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_MODEL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_MODEL model:minecraft:stone";
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
