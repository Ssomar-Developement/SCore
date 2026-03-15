package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemCommand;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

import java.util.ArrayList;
import java.util.List;

public class SetEquippableModel extends ItemCommand {

    public SetEquippableModel() {
        CommandSetting model = new CommandSetting("model", -1, String.class, "minecraft:diamond");
        List<CommandSetting> settings = getSettings();
        settings.add(model);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, ItemStack item, SCommandToExec sCommandToExec) {
        String model = (String) sCommandToExec.getSettingValue("model");

        if (item == null || item.getType() == Material.AIR) return;
        if (!item.hasItemMeta()) {
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        ItemMeta itemMeta = item.getItemMeta();

        boolean hasEquippable = itemMeta.hasEquippable();
        EquippableComponent equippable = itemMeta.getEquippable();
        if (!hasEquippable) {
            ItemStack defaultItem = new ItemStack(item.getType());
            if (defaultItem.hasData(DataComponentTypes.EQUIPPABLE)) {
                io.papermc.paper.datacomponent.item.Equippable defaultEquippable = defaultItem.getData(DataComponentTypes.EQUIPPABLE);
                equippable.setDamageOnHurt(defaultEquippable.damageOnHurt());
                equippable.setSlot(defaultEquippable.slot());
                equippable.setDispensable(defaultEquippable.dispensable());
            }
        }
        equippable.setModel(NamespacedKey.fromString(model));
        itemMeta.setEquippable(equippable);
        item.setItemMeta(itemMeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_EQUIPPABLE_MODEL");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_EQUIPPABLE_MODEL model:minecraft:diamond";
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
