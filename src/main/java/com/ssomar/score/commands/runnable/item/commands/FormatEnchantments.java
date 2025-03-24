package com.ssomar.score.commands.runnable.item.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.item.ItemCommand;
import com.ssomar.score.utils.WordUtils;
import com.ssomar.score.utils.numbers.RomanNumber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FormatEnchantments extends ItemCommand {

    public FormatEnchantments() {
        List<CommandSetting> settings = getSettings();
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, ItemStack itemStack, SCommandToExec sCommandToExec) {
        ItemStack item = itemStack;

        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return;

        try {
            Bukkit.getScheduler().runTaskLater(com.ssomar.score.SCore.plugin, new Runnable() {
                @Override
                public void run() {
                    ItemMeta itemMeta = item.getItemMeta();
                    List<String> LoreCURRENT;

                    LoreCURRENT = itemMeta.getLore();
                    if (LoreCURRENT == null) {
                        LoreCURRENT = new ArrayList<>();
                    }
                    List<String> LoreOfEnchantmentsArtifical = new ArrayList<>();

                    Iterator enchantments = itemMeta.getEnchants().keySet().iterator();

                    while (enchantments.hasNext()) {
                        Enchantment enchant = (Enchantment) enchantments.next();

                        String line = getNameOfEnchantment(enchant.getKey().toString());
                        int level = item.getEnchantmentLevel(enchant);
                        if(enchant.getMaxLevel() != 1)
                            line = line + " " + RomanNumber.toRoman(level);

                        LoreOfEnchantmentsArtifical.add(line);
                    }

                    SsomarDev.testMsg(String.valueOf(LoreOfEnchantmentsArtifical), true);

                    for (int i = 0; i < LoreCURRENT.size(); i++) {
                        for (String enchantmentlore : LoreOfEnchantmentsArtifical) {
                            if (LoreCURRENT.get(i).contains(enchantmentlore.split(" ")[0])) {
                                LoreCURRENT.remove(i);
                            }
                        }
                    }

                    SsomarDev.testMsg(String.valueOf(LoreCURRENT), true);

                    LoreOfEnchantmentsArtifical.addAll(LoreCURRENT);

                    SsomarDev.testMsg(String.valueOf(LoreOfEnchantmentsArtifical), true);

                    itemMeta.setLore(LoreOfEnchantmentsArtifical);
                    itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
                    item.setItemMeta(itemMeta);
                }
            }, 1L);

        } catch (NullPointerException e) {
            return;
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORMAT_ENCHANTMENTS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORMAT_ENCHANTMENTS";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }

    public static String getNameOfEnchantment(String name) {
        return ChatColor.GRAY + WordUtils.capitalize(name.replace("minecraft:", "").replace("_", " "));
    }
}
