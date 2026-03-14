package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

        boolean hasEquippable = itemMeta.hasEquippable();
        EquippableComponent equippable = itemMeta.getEquippable();
        if (!hasEquippable) {
            ItemStack defaultItem = new ItemStack(dMeta.getMaterial());
            if (defaultItem.hasData(DataComponentTypes.EQUIPPABLE)) {
                io.papermc.paper.datacomponent.item.Equippable defaultEquippable = defaultItem.getData(DataComponentTypes.EQUIPPABLE);
                equippable.setDamageOnHurt(defaultEquippable.damageOnHurt());
                equippable.setSlot(defaultEquippable.slot());
            }
        }
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
