package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Boots extends PlayerCommand {

    public Boots() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {

        /* Delay fix a double activation of the item, not so easy to explain, it fixes this issue: https://discord.com/channels/701066025516531753/1014297458735595680/1014299784229683302*/
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                PlayerInventory inv = receiver.getInventory();
                ItemStack item = inv.getItemInMainHand();
                ItemStack headItem = inv.getBoots();
                if (!item.getType().equals(Material.AIR)) {
                    if (headItem != null) {
                        Map<Enchantment, Integer> enchants = headItem.getEnchantments();
                        if (enchants.containsKey(Enchantment.BINDING_CURSE)) return;
                    }
                    inv.setBoots(item);
                    inv.setItemInMainHand(headItem);
                }
            }
        };
        SCore.schedulerHook.runTask(runnable3, 1);

    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("BOOTS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "BOOTS";
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
