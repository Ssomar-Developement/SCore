package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

import java.util.ArrayList;
import java.util.List;

public class SetEquippableModel extends ItemMetaCommand {

    public SetEquippableModel() {
        CommandSetting model = new CommandSetting("model", -1, String.class, "minecraft:diamond");
        List<CommandSetting> settings = getSettings();
        settings.add(model);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dMeta, SCommandToExec sCommandToExec) {
        String model = (String) sCommandToExec.getSettingValue("model");
        ItemMeta itemMeta = dMeta.getMeta();

        EquippableComponent equippable = itemMeta.getEquippable();
        equippable.setModel(NamespacedKey.fromString(model));
        itemMeta.setEquippable(equippable);
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
