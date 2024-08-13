package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* ADDENCHANTMENT {slot} {enchantment} {level} */
public class RemoveEnchantment extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {

        List<String> args = sCommandToExec.getOtherArgs();

        ItemStack item;
        ItemMeta itemMeta;
        int slot = NTools.getInteger(args.get(0)).get();

        if (slot == -1) item = receiver.getInventory().getItemInMainHand();
        else item = receiver.getInventory().getItem(slot);
        if (item == null || item.getType() == Material.AIR) return;

        try {
            itemMeta = item.getItemMeta();
        } catch (NullPointerException e) {
            return;
        }

        try {
            if(args.get(1).equalsIgnoreCase("all")){
                Map<Enchantment,Integer> enchantmentsOfItem= itemMeta.getEnchants();
                for(Enchantment enchants : enchantmentsOfItem.keySet()){
                    itemMeta.removeEnchant(enchants);
                }
                item.setItemMeta(itemMeta);
            }else {
                org.bukkit.enchantments.Enchantment enchantment = org.bukkit.enchantments.Enchantment.getByKey(NamespacedKey.minecraft(args.get(1).toLowerCase()));
                if (enchantment == null) return;

                itemMeta.removeEnchant(enchantment);
                item.setItemMeta(itemMeta);
            }
        }catch(IllegalArgumentException e){
            return;
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 2) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkInteger(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("REMOVEENCHANTMENT");
        return names;
    }

    @Override
    public String getTemplate() {
        return "REMOVEENCHANTMENT {slot} {enchantment}";
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
