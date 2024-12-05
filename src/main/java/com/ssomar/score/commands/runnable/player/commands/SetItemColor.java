package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
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

public class SetItemColor extends PlayerCommand {

    public SetItemColor() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        CommandSetting color = new CommandSetting("color", 1, Integer.class, 13434623);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        settings.add(color);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        ItemMeta itemmeta;
        int color = (int) sCommandToExec.getSettingValue("color");
        int slot = (int) sCommandToExec.getSettingValue("slot");
        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);

        if (item == null || item.getType() == Material.AIR) return;
        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item.getType()).getItemMeta());
        }

        itemmeta = item.getItemMeta();

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
        names.add("SETITEMCOLOR");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SET_ITEM_COLOR slot:-1 color:13434623";
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
