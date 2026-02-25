package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetItemCustomModelData extends PlayerCommand {

    public SetItemCustomModelData() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);

        CommandSetting customModelData = new CommandSetting("customModelData", 1, String.class, 2);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(customModelData);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        String customModelData = (String) sCommandToExec.getSettingValue("customModelData");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        itemmeta = item.getItemMeta();

        if (SCore.is1v21v4Plus()) {
            String[] split = customModelData.split(";");
            List<Float> floats = new ArrayList<>();
            List<Boolean> booleans = new ArrayList<>();
            List<String> strings = new ArrayList<>();
            for (String s : split) {
                Optional<Float> f = NTools.getFloat(s);
                if (f.isPresent()) floats.add(f.get());
                else if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
                    booleans.add(Boolean.parseBoolean(s));
                else strings.add(s);
            }
            CustomModelDataComponent customModelDataComponent = itemmeta.getCustomModelDataComponent();
            customModelDataComponent.setStrings(strings);
            customModelDataComponent.setFloats(floats);
            customModelDataComponent.setFlags(booleans);
            itemmeta.setCustomModelDataComponent(customModelDataComponent);
        } else {
            Optional<Integer> integerOptional = NTools.getInteger(customModelData);
            if (integerOptional.isPresent())
                itemmeta.setCustomModelData(integerOptional.get());
        }

        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_CUSTOM_MODEL_DATA");
        names.add("SETITEMCUSTOMMODELDATA");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_CUSTOM_MODEL_DATA slot:-1 customModelData:2";
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
