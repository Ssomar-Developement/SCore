package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* ADDENCHANTMENT {slot} {enchantment} {level} */
public class AddEnchantment extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {

        ItemStack item;
        ItemMeta itemMeta;
        int slot = NTools.getInteger(args.get(0)).get();
        int level = NTools.getInteger(args.get(2)).get();

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
        if (item == null || item.getType() == Material.AIR) return;

        try {
            itemMeta = item.getItemMeta();
        } catch (NullPointerException e) {
            return;
        }

        try {
            org.bukkit.enchantments.Enchantment enchantment = org.bukkit.enchantments.Enchantment.getByKey(NamespacedKey.minecraft(args.get(1).toLowerCase()));
            if (enchantment == null) return;
            if (level <= 0) return;

            itemMeta.addEnchant(enchantment,level, true);
            item.setItemMeta(itemMeta);
        }catch(IllegalArgumentException e){
            return;
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 3) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        ArgumentChecker ac3 = checkInteger(args.get(2), isFinalVerification, getTemplate());
        if (!ac3.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("ADDENCHANTMENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "ADDENCHANTMENT {slot} {enchantment} {level}";
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
