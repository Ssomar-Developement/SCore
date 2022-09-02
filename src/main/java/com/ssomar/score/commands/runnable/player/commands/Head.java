package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.sevents.events.player.equip.armor.ArmorType;
import com.ssomar.sevents.events.player.equip.armor.PlayerEquipArmorEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* HEAD */
public class Head extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        /* Delay fix a double activation of the item, not so easy to esplain, it fixes this issue: https://discord.com/channels/701066025516531753/1014297458735595680/1014299784229683302*/
        BukkitRunnable runnable3 = new BukkitRunnable() {
            @Override
            public void run() {
                PlayerInventory inv = receiver.getInventory();
                ItemStack item = inv.getItemInMainHand();
                ItemStack headItem = inv.getHelmet();
                if (!item.getType().equals(Material.AIR)) {
                    if (headItem != null) {
                        Map<Enchantment, Integer> enchants = headItem.getEnchantments();
                        if (enchants.containsKey(Enchantment.BINDING_CURSE)) return;
                    }
                    PlayerEquipArmorEvent bbE = new PlayerEquipArmorEvent(receiver, PlayerEquipArmorEvent.EquipMethod.HOTBAR, ArmorType.HELMET, headItem, item);
                    Bukkit.getPluginManager().callEvent(bbE);

                    if(!bbE.isCancelled()) {
                        inv.setHelmet(item);
                        inv.setItemInMainHand(headItem);
                    }
                }
            }
        };
        runnable3.runTaskLater(SCore.plugin, 1);

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("HEAD");
        return names;
    }

    @Override
    public String getTemplate() {
        return "HEAD";
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
