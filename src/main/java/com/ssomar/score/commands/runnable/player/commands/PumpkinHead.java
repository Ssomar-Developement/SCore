package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/* PUMPKIN_HEAD */
public class PumpkinHead extends PlayerCommand {

    public PumpkinHead() {
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        // Create a carved pumpkin helmet
        ItemStack pumpkinHelmet = new ItemStack(Material.CARVED_PUMPKIN);
        ItemMeta meta = pumpkinHelmet.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Jack O'Lantern Head");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "A spooky Halloween pumpkin!");
            meta.setLore(lore);
            pumpkinHelmet.setItemMeta(meta);
        }

        // Replace helmet slot
        receiver.getInventory().setHelmet(pumpkinHelmet);

        // Spawn Halloween particles
        receiver.getWorld().spawnParticle(
            Particle.FLAME,
            receiver.getLocation().add(0, 2, 0),
            20,
            0.3,
            0.3,
            0.3,
            0.02
        );

        // Play equip sound
        receiver.getWorld().playSound(receiver.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("PUMPKIN_HEAD");
        names.add("PUMPKINHEAD");
        return names;
    }

    @Override
    public String getTemplate() {
        return "PUMPKIN_HEAD";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GOLD;
    }

    @Override
    public ChatColor getExtraColor() {
        return ChatColor.YELLOW;
    }
}
