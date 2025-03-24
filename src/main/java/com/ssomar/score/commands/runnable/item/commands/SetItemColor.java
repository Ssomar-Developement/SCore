package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemCommand;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemColor extends ItemCommand {

    public SetItemColor() {
        CommandSetting color = new CommandSetting("color", -1, Integer.class, 13434623);
        List<CommandSetting> settings = getSettings();
        settings.add(color);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, ItemStack item, SCommandToExec sCommandToExec) {
        int color = (int) sCommandToExec.getSettingValue("color");


        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        ItemMeta itemmeta = item.getItemMeta();

        Material material = item.getType();
        if (material.equals(Material.LEATHER_BOOTS) ||
                material.equals(Material.LEATHER_CHESTPLATE) ||
                material.equals(Material.LEATHER_LEGGINGS) ||
                material.equals(Material.LEATHER_HELMET)
                || (!SCore.is1v13Less() && material.equals(Material.LEATHER_HORSE_ARMOR))) {

            LeatherArmorMeta aMeta = (LeatherArmorMeta) itemmeta;
            aMeta.setColor(Color.fromRGB(color));
        }
        else if(itemmeta instanceof FireworkEffectMeta){
            FireworkEffectMeta fMeta = (FireworkEffectMeta) itemmeta;
            FireworkEffect aa = FireworkEffect.builder().withColor(Color.fromRGB(color)).build();
            fMeta.setEffect(aa);
        }

        item.setItemMeta(itemmeta);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SET_ITEM_COLOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_COLOR color:13434623";
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
