package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.sevents.events.player.equip.armor.ArmorType;
import com.ssomar.sevents.events.player.equip.armor.PlayerEquipArmorEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class RemoveCancelDamageEventMetadataTagEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player)e.getDamager();
            if (damager.hasMetadata("cancelDamageEvent")) {
                damager.removeMetadata("cancelDamageEvent", (Plugin)SCore.plugin);
                return;
            }
        }
    }
}
