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

public class SetItemlore extends ItemMetaCommand {

    public SetItemlore() {
        CommandSetting line = new CommandSetting("line", -1, Integer.class, 1);
        CommandSetting text = new CommandSetting("text", -1, String.class, "&6New_lore_line");
        text.setAcceptUnderScoreForLongText(true);
        List<CommandSetting> settings = getSettings();
        settings.add(line);
        settings.add(text);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec) {
        int line = (int) sCommandToExec.getSettingValue("line");
        String text = (String) sCommandToExec.getSettingValue("text");


        ItemMeta itemmeta = dynamicMeta.getMeta();

        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(text);
        message.append(" ");
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));

        List<String> list = itemmeta.getLore();

        if (list == null) return;
        if (list.size() < line) return;

        if (line > 0) line += -1;
        list.set(line, StringConverter.coloredString(message.toString()));

        itemmeta.setLore(list);
    }


    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_LORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_LORE line:1 text:&6New_lore_line";
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
