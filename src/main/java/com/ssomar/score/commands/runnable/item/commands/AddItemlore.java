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

public class AddItemlore extends ItemMetaCommand {

    public AddItemlore() {
        CommandSetting text = new CommandSetting("text", -1, String.class, "New lore");
        text.setAcceptUnderScoreForLongText(true);
        List<CommandSetting> settings = getSettings();
        settings.add(text);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec) {
        ArrayList<String> list;

        String text = (String) sCommandToExec.getSettingValue("text");

        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(text);
        message.append(" ");
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));

        ItemMeta itemmeta = dynamicMeta.getMeta();

        list = (ArrayList<String>) itemmeta.getLore();
        if(list == null) list = new ArrayList<>();
        if(!message.toString().isEmpty()) {
            list.add(StringConverter.coloredString(message.toString()));
        }
        itemmeta.setLore(list);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_ITEM_LORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_ITEM_LORE text:My_new_lore_line";
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
