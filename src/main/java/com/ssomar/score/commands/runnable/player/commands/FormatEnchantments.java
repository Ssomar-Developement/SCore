package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.WordUtils;
import com.ssomar.score.utils.numbers.RomanNumber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/* FORMAT_ENCHANTMENTS {slot}*/
public class FormatEnchantments extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        try {
            Bukkit.getScheduler().runTaskLater(com.ssomar.score.SCore.plugin, new Runnable() {
                @Override
                public void run() {
                    ItemStack item;
                    ItemMeta itemmeta;
                    List<String> LoreCURRENT;

                    int slot = Integer.valueOf(args.get(0));

                    if(slot == -1) item = receiver.getInventory().getItemInMainHand();
                    else item = receiver.getInventory().getItem(slot);

                    if (item == null) return;

                    itemmeta = item.getItemMeta();

                    LoreCURRENT = itemmeta.getLore();
                    if (LoreCURRENT == null) {
                        LoreCURRENT = new ArrayList<>();
                    }
                    List<String> LoreOfEnchantmentsArtifical = new ArrayList<>();

                    Iterator enchantments = itemmeta.getEnchants().keySet().iterator();

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
                            if (LoreCURRENT.get(i).contains(enchantmentlore)) {
                                LoreCURRENT.remove(i);
                            }
                        }
                    }

                    SsomarDev.testMsg(String.valueOf(LoreCURRENT), true);

                    for (String enchantmentlore : LoreCURRENT) {
                        LoreOfEnchantmentsArtifical.add(enchantmentlore);
                    }

                    SsomarDev.testMsg(String.valueOf(LoreOfEnchantmentsArtifical), true);

                    itemmeta.setLore(LoreOfEnchantmentsArtifical);
                    itemmeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
                    item.setItemMeta(itemmeta);
                }
            }, 1L);

        } catch (NullPointerException e) {
            return;
        }
    }


    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORMAT_ENCHANTMENTS");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORMAT_ENCHANTMENTS {slot}";
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
