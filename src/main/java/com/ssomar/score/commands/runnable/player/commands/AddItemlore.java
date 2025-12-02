package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AddItemlore extends PlayerCommand {

    public AddItemlore() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, 0);
        slot.setSlot(true);
        CommandSetting text = new CommandSetting("text", 1, String.class, "New lore");
        text.setAcceptUnderScoreForLongText(true);
        CommandSetting insertIndex = new CommandSetting("insertIndex", -1, Integer.class, -1);
        CommandSetting addAfter = new CommandSetting("addAfter", -1, Integer.class, -1);
        CommandSetting parsePlaceholders = new CommandSetting("parsePlaceholders", -1, Boolean.class, true);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(text);
        settings.add(insertIndex);
        settings.add(addAfter);
        settings.add(parsePlaceholders);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        ArrayList<String> list;

        int slot = (int) sCommandToExec.getSettingValue("slot");
        int insertIndex = (int) sCommandToExec.getSettingValue("insertIndex");
        int addAfter = (int) sCommandToExec.getSettingValue("addAfter");
        boolean parsePlaceholders = (boolean) sCommandToExec.getSettingValue("parsePlaceholders");
        String text = (String) sCommandToExec.getSettingValue("text");

        List<String> args = sCommandToExec.getOtherArgs();
        StringBuilder message = new StringBuilder(text);
        message.append(" ");
        for (String s : args) {
            //SsomarDev.testMsg("cmdarg> "+s);
            message.append(s).append(" ");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        itemmeta = item.getItemMeta();

        list = (ArrayList<String>) itemmeta.getLore();
        if(list == null) list = new ArrayList<>();
        if(!message.toString().isEmpty()) {
            // Process the message based on parsePlaceholders setting
            String processedMessage = parsePlaceholders ? StringConverter.coloredString(message.toString()) : message.toString();

            // Determine the insertion index
            int finalIndex = -1;
            if (addAfter != -1) {
                // addAfter takes priority: add after the specified line (so at position addAfter + 1)
                finalIndex = addAfter + 1;
            } else if (insertIndex != -1) {
                // Use insertIndex if addAfter is not specified
                finalIndex = insertIndex;
            }

            // Add the lore at the appropriate position
            if (finalIndex == -1) {
                list.add(processedMessage);
            } else {
                // Ensure the index is within bounds
                if (finalIndex > list.size()) {
                    finalIndex = list.size();
                }
                list.add(finalIndex, processedMessage);
            }
        }
        itemmeta.setLore(list);
        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADD_ITEM_LORE");
        names.add("ADD_LORE");
        names.add("ADDLORE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADD_ITEM_LORE slot:-1 text:My_new_lore_line insertIndex:0 addAfter:2 parsePlaceholders:true";
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
