package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemName extends ItemMetaCommand {

    public SetItemName() {
        CommandSetting name = new CommandSetting("name", 1, String.class, "&eNew_name");
        List<CommandSetting> settings = getSettings();
        settings.add(name);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dMeta, SCommandToExec sCommandToExec) {
        String name = (String) sCommandToExec.getSettingValue("name");
        ItemMeta itemmeta = dMeta.getMeta();

        StringBuilder build = new StringBuilder(name);

        for (String s : sCommandToExec.getOtherArgs()) {
            build.append(s).append(" ");
        }

        itemmeta.setDisplayName(StringConverter.coloredString(build.toString()));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_NAME");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_NAME name:&eNew_name";
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
