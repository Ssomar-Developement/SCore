package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForceDrop extends MixedCommand {

    @Override
    public void run(Player p, Entity receiver, List<String> args, ActionInfo aInfo) {

        int slot = Double.valueOf(args.get(0)).intValue();

        ItemStack toDrop = null;

        if(receiver instanceof Player) {

            PlayerInventory inventory = ((Player)receiver).getInventory();

            if (slot == -1)
                slot = inventory.getHeldItemSlot();

            toDrop = inventory.getItem(slot);
            inventory.clear(slot);
        }
        else {

            if(!(receiver instanceof LivingEntity)) return;
            LivingEntity livingReceiver = (LivingEntity) receiver;

            EntityEquipment equipment = livingReceiver.getEquipment();
            if(equipment == null) return;
            switch (slot){
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

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {

        if (args.size() < 1) return Optional.of(notEnoughArgs + getTemplate());

        ArgumentChecker ac = checkSlot(args.get(0), isFinalVerification, getTemplate());
        if (!ac.isValid()) return Optional.of(ac.getError());

        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("FORCEDROP");
        return names;
    }

    @Override
    public String getTemplate() {
        return "FORCEDROP {slot -1 for main_hand}";
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
