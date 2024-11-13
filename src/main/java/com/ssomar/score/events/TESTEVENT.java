package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class TESTEVENT implements Listener {


    /* @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
        if(!e.isSneaking()) return;

        Player p = e.getPlayer();

        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        UseCooldownComponent useCooldownComponent = meta.getUseCooldown();
        useCooldownComponent.setCooldownGroup(NamespacedKey.fromString("its_a_test"));
        // REQUIRED TO NOT HAVE THE ISSUE useCooldownComponent.setCooldownSeconds(1);
        meta.setUseCooldown(useCooldownComponent);
        item.setItemMeta(meta);

        p.getInventory().addItem(item);
    } */

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(StructureGrowEvent e) {

        Player p = e.getPlayer();
        SsomarDev.testMsg("ATTEMPT GROW ", true);
    }




}
