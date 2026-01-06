package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

import java.util.ArrayList;
import java.util.List;

public class SetEquippableModel extends PlayerCommand {

    public SetEquippableModel() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting model = new CommandSetting("model", 1, String.class, "minecraft:diamond");
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(model);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemMeta;
        String model = (String) sCommandToExec.getSettingValue("model");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if (!item.hasItemMeta()) {
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        itemMeta = item.getItemMeta();

        EquippableComponent equippable = itemMeta.getEquippable();
        equippable.setModel(NamespacedKey.fromString(model));
        itemMeta.setEquippable(equippable);

        item.setItemMeta(itemMeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_EQUIPPABLE_MODEL");
        names.add("SETEQUIPPABLEMODEL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_EQUIPPABLE_MODEL slot:-1 model:minecraft:diamond";
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
