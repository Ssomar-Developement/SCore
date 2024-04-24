package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* MODIFYDURABILITY {number} {slot} */
public class ModifyDurability extends PlayerCommand {

    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
        int slot = -1;
        int modification = Double.valueOf(args.get(0)).intValue();
        boolean supportUnbreaking = false;

        if (args.size() >= 2) {
            slot = Double.valueOf(args.get(1)).intValue();
        }
        if (args.size() >= 3) {
            supportUnbreaking = Boolean.valueOf(args.get(2));
        }
        PlayerInventory pInv = receiver.getInventory();

        if (slot == -1) slot = pInv.getHeldItemSlot();

        ItemStack item = pInv.getItem(slot);
        if(item == null) return;

        if(!item.hasItemMeta()){
            item.setItemMeta(new ItemStack(item).getItemMeta());
        }

        if (item.getItemMeta() instanceof Damageable) {
            Damageable meta = (Damageable) item.getItemMeta();
            Map<Enchantment, Integer> enchants = item.getEnchantments();
            int unbreakingLevel = 0;
            Enchantment unbreaking = SCore.is1v20v5Plus() ? Enchantment.UNBREAKING : Enchantment.getByName("DURABILITY");
            if (supportUnbreaking && enchants.containsKey(unbreaking)) {
                unbreakingLevel = enchants.get(unbreaking);
            }
            if (modification < 0) {
                for (int i = modification; i < 0; i++) {
                    int random = (int) (Math.random() * 100);
                    if (random > (100 / (unbreakingLevel + 1))) {
                        modification++;
                    }
                }
                meta.setDamage(meta.getDamage() - modification);
                if (meta.getDamage() >= item.getType().getMaxDurability()) {
                    item.setAmount(item.getAmount() - 1);
                    return;
                }
                item.setItemMeta(meta);

            } else {
                meta.setDamage(meta.getDamage() - modification);
                if (meta.getDamage() >= item.getType().getMaxDurability()) {
                    item.setAmount(item.getAmount() - 1);
                    return;
                }
                item.setItemMeta(meta);
            }
        }

    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        if (args.size() >= 2) {
            ArgumentChecker ac2 = checkSlot(args.get(1), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        if (args.size() >= 3) {
            ArgumentChecker ac2 = checkBoolean(args.get(2), isFinalVerification, getTemplate());
            if (!ac2.isValid()) return Optional.of(ac2.getError());
        }

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MODIFYDURABILITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MODIFYDURABILITY {number} [slot] [supportUnbreaking true or false]";
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
