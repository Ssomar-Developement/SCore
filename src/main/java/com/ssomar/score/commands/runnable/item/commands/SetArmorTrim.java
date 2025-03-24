package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemMetaCommand;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class SetArmorTrim extends ItemMetaCommand {

    public SetArmorTrim() {
        CommandSetting pattern = new CommandSetting("pattern", -1, String.class, "sentry");
        CommandSetting patternMaterial = new CommandSetting("patternMaterial", -1, String.class, "emerald");
        List<CommandSetting> settings = getSettings();
        settings.add(pattern);
        settings.add(patternMaterial);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, DynamicMeta dynamicMeta, SCommandToExec sCommandToExec) {
        String pattern = (String) sCommandToExec.getSettingValue("pattern");
        String patternMaterial = (String) sCommandToExec.getSettingValue("patternMaterial");

        ItemMeta itemmeta = dynamicMeta.getMeta();


        if (itemmeta instanceof ArmorMeta) {
            ArmorMeta armor = (ArmorMeta) itemmeta;

            if(pattern.equalsIgnoreCase("remove") || pattern.equalsIgnoreCase("null")){
                armor.setTrim(null);
                return;
            }

            TrimPattern trimPattern = getTrimPattern(pattern.toLowerCase());
            if (trimPattern == null) return;

            TrimMaterial trimMaterial = getTrimMaterial(patternMaterial.toLowerCase());
            if (trimMaterial == null) return;

            ArmorTrim armorTrim = new ArmorTrim(trimMaterial,trimPattern);

            try {
                armor.setTrim(armorTrim);
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
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ARMOR_TRIM pattern:sentry patternMaterial:emerald";
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
