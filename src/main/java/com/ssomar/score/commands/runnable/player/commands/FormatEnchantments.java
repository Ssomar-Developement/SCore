package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
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

public class FormatEnchantments extends PlayerCommand {

    public FormatEnchantments() {
        CommandSetting slot = new CommandSetting("slot", 0, Integer.class, -1);
        slot.setSlot(true);
        List<CommandSetting> settings = getSettings();
        settings.add(slot);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        ItemStack item;
        int slot = (int) sCommandToExec.getSettingValue("slot");

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
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

                    for (String enchantmentlore : LoreCURRENT) {
                        LoreOfEnchantmentsArtifical.add(enchantmentlore);
                    }

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
        return "FORMAT_ENCHANTMENTS slot:-1";
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
