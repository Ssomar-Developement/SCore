package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.api.executableitems.ExecutableItemsAPI;
import com.ssomar.score.api.executableitems.config.ExecutableItemsManagerInterface;
import com.ssomar.score.commands.runnable.CommandSetting;
import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class ForceDrop extends MixedCommand {

    public ForceDrop() {
        CommandSetting slotSetting = new CommandSetting("slot", 0, Integer.class, -1, true);
        slotSetting.setSlot(true);
        CommandSetting eiIdSetting = new CommandSetting("ei_id", -1, String.class, null);
        List<CommandSetting> settings = getSettings();
        settings.add(slotSetting);
        settings.add(eiIdSetting);
        setNewSettingsMode(true);
    }

    @Override
    public void run(Player p, Entity receiver, SCommandToExec sCommandToExec) {
        String eiId = (String) sCommandToExec.getSettingValue("ei_id");

        if (eiId != null && !eiId.isEmpty()) {
            // ei_id mode: drop all matching ExecutableItems from the receiver's inventory
            if (!(receiver instanceof Player)) return;
            if (!SCore.hasExecutableItems) return;
            Player target = (Player) receiver;
            if (target.isDead()) return;

            Location loc = target.getLocation();
            ExecutableItemsManagerInterface manager = ExecutableItemsAPI.getExecutableItemsManager();
            Inventory inventory = target.getInventory();
            for (ItemStack item : inventory) {
                if (target.isDead()) return;
                if (item == null) continue;
                if (manager.getExecutableItem(item).isPresent()) {
                    if (manager.getExecutableItem(item).get().getId().equalsIgnoreCase(eiId)) {
                        ItemStack copy = item.clone();
                        item.setAmount(0);
                        loc.getWorld().dropItem(loc, copy);
                    }
                }
            }
        } else {
            // slot mode: drop the item from the given inventory slot
            int slot = (Integer) sCommandToExec.getSettingValue("slot");

            ItemStack toDrop = null;

            if (receiver instanceof Player) {
                PlayerInventory inventory = ((Player) receiver).getInventory();
                if (slot == -1) slot = inventory.getHeldItemSlot();
                toDrop = inventory.getItem(slot);
                inventory.clear(slot);
            } else {
                if (!(receiver instanceof LivingEntity)) return;
                LivingEntity livingReceiver = (LivingEntity) receiver;
                EntityEquipment equipment = livingReceiver.getEquipment();
                if (equipment == null) return;
                switch (slot) {
                    case -1: {
                        toDrop = equipment.getItemInMainHand();
                        equipment.setItemInMainHand(null);
                        break;
                    }
                    case 40: {
                        toDrop = equipment.getItemInOffHand();
                        equipment.setItemInOffHand(null);
                        break;
                    }
                    case 36: {
                        toDrop = equipment.getBoots();
                        equipment.setBoots(null);
                        break;
                    }
                    case 37: {
                        toDrop = equipment.getLeggings();
                        equipment.setLeggings(null);
                        break;
                    }
                    case 38: {
                        toDrop = equipment.getChestplate();
                        equipment.setChestplate(null);
                        break;
                    }
                    case 39: {
                        toDrop = equipment.getHelmet();
                        equipment.setHelmet(null);
                        break;
                    }
                }
            }
            if (toDrop != null) receiver.getLocation().getWorld().dropItem(receiver.getLocation(), toDrop);
        }
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORCE_DROP");
        names.add("FORCEDROP");
        names.add("DROPSPECIFICEI");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORCE_DROP slot:-1 | FORCE_DROP ei_id:MyExecutableItem";
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
