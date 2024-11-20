package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class SetArmorTrim extends PlayerCommand {

    public SetArmorTrim() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting pattern = new CommandSetting("pattern", 1, String.class, "sentry");
        CommandSetting patternMaterial = new CommandSetting("patternMaterial", 2, String.class, "emerald");
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(pattern);
        settings.add(patternMaterial);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        String pattern = (String) sCommandToExec.getSettingValue("pattern");
        String patternMaterial = (String) sCommandToExec.getSettingValue("patternMaterial");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        itemmeta = item.getItemMeta();


        if (itemmeta instanceof ArmorMeta) {
            ArmorMeta armor = (ArmorMeta) itemmeta;

            if(pattern.equalsIgnoreCase("remove") || pattern.equalsIgnoreCase("null")){
                armor.setTrim(null);
                item.setItemMeta(itemmeta);
                return;
            }

            TrimPattern trimPattern = getTrimPattern(pattern.toLowerCase());
            if (trimPattern == null) return;

            TrimMaterial trimMaterial = getTrimMaterial(patternMaterial.toLowerCase());
            if (trimMaterial == null) return;

            ArmorTrim armorTrim = new ArmorTrim(trimMaterial,trimPattern);

            try {
                armor.setTrim(armorTrim);
                item.setItemMeta(itemmeta);
            }catch(Exception e){
                return;
            }

        }
    }

    private TrimMaterial getTrimMaterial(String str) {
        return Registry.TRIM_MATERIAL.get(NamespacedKey.minecraft(str));
    }

    private TrimPattern getTrimPattern(String str) {
        return Registry.TRIM_PATTERN.get(NamespacedKey.minecraft(str));
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ARMOR_TRIM");
        names.add("SETARMORTRIM");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ARMOR_TRIM slot:-1 pattern:sentry patternMaterial:emerald";
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
