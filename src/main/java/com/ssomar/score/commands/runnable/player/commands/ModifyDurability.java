package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
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

public class ModifyDurability extends PlayerCommand {

    public ModifyDurability() {
        CommandSetting modification = new CommandSetting("modification", 0, Integer.class, 50);
        CommandSetting slot = new CommandSetting("slot", 1, Integer.class, -1);
        CommandSetting supportUnbreaking = new CommandSetting("supportUnbreaking", 2, Boolean.class, false);
        List<CommandSetting> settings = getSettings();
        settings.add(modification);
        settings.add(slot);
        settings.add(supportUnbreaking);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Player receiver, SCommandToExec sCommandToExec) {
        int slot = (int) sCommandToExec.getSettingValue("slot");
        int modification = (int) sCommandToExec.getSettingValue("modification");
        boolean supportUnbreaking = (boolean) sCommandToExec.getSettingValue("supportUnbreaking");

        PlayerInventory pInv = receiver.getInventory();

        if (slot == -1) slot = pInv.getHeldItemSlot();

        ItemStack item = pInv.getItem(slot);
        if(item == null || !item.hasItemMeta()) return;

        if (item.getItemMeta() instanceof Damageable) {
            Damageable meta = (Damageable) item.getItemMeta();
            Map<Enchantment, Integer> enchants = item.getEnchantments();
            int unbreakingLevel = 0;
            Enchantment unbreaking = SCore.is1v20v5Plus() ? Enchantment.UNBREAKING : Enchantment.getByName("DURABILITY");
            if (supportUnbreaking && enchants.containsKey(unbreaking)) {
                unbreakingLevel = enchants.get(unbreaking);
            }

            int maxDura = item.getType().getMaxDurability();
            if(SCore.is1v21Plus() && meta.hasMaxDamage()) maxDura = meta.getMaxDamage();

            //SsomarDev.testMsg("Item " +item.getType()+" max dura "+ maxDura+" Modification: " + modification+ " Damge: " + meta.getDamage()+ " MaxDurability: " + item.getType().getMaxDurability()+ " UnbreakingLevel: " + unbreakingLevel, true);

            if (modification < 0) {
                for (int i = modification; i < 0; i++) {
                    int random = (int) (Math.random() * 100);
                    if (random > (100 / (unbreakingLevel + 1))) {
                        modification++;
                    }
                }
                meta.setDamage(meta.getDamage() - modification);
                if (meta.getDamage() >= maxDura) {
                    item.setAmount(item.getAmount() - 1);
                    return;
                }
                item.setItemMeta(meta);

            } else {
                meta.setDamage(meta.getDamage() - modification);
                if (meta.getDamage() >= maxDura) {
                    item.setAmount(item.getAmount() - 1);
                    return;
                }
                item.setItemMeta(meta);
            }
        }

    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("MODIFY_DURABILITY");
        names.add("MODIFYDURABILITY");
        return names;
    }

    @Override
    public String getTemplate() {
        return "MODIFY_DURABILITY modification:50 slot:-1 supportUnbreaking:false";
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
