package com.ssomar.score.events;

import org.bukkit.event.Listener;

public class TESTEVENT implements Listener {


    /*@EventHandler(priority = EventPriority.LOWEST)
    public final void onPlayerToggleSneakEvent(final PlayerToggleSneakEvent e) {

        for (Material mat : Material.values()) {
            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) meta.setDisplayName(("aaaaaaaaaaaaaaaaaaaaaaaa"));
            item.setItemMeta(meta);
            Inventory inv = Bukkit.createInventory(null, 18);
            inv.addItem(item);
            for (ItemStack it : inv.getContents()) {
                if (it == null) continue;
                else {
                    ItemMeta meta2 = it.getItemMeta();
                    if (meta2 == null) continue;
                    meta2.setDisplayName(("bbbbbbbbbbbbbbbbbbbb");
                    it.setItemMeta(meta2);

                    String currentName = it.getItemMeta().getDisplayName();
                    if (currentName.contains("aaaaaaaa"))
                        System.out.println("MATERIAL >> " + mat + "meta AFTER>>>>>>>>>: " + currentName);
                }
            }
        }
    }*/

    /* @EventHandler(priority = EventPriority.LOWEST)
    public final void onPlayerToggleSneakEvent(final PlayerToggleSneakEvent e) {

        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("aaaaaaaaaaaaaaaaaaaaaaaa");
        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.applyNBT(item);

        ItemMeta meta2 = item.getItemMeta();
        System.out.println("meta2 AFTER>>>>>>>>>: " + meta2.getDisplayName());
    }*/
}
