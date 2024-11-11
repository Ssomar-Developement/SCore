package com.ssomar.score.events;

import org.bukkit.event.Listener;

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

    /* @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        ItemStack item = p.getItemInHand();
        SsomarDev.testMsg("COOLDOWN ITEM "+item, true);
        SsomarDev.testMsg("COOLDOWN ITEM ? "+p.hasCooldown(item), true);
    } */


}
