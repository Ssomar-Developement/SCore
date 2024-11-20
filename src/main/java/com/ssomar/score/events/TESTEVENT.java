package com.ssomar.score.events;

import org.bukkit.event.Listener;

public class TESTEVENT implements Listener {


    /* @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
        if(!e.isSneaking()) return;

        Player p = e.getPlayer();


        runTest1(p);
        runTest2(p);


    }

    public void runTest1(Player p){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();

        Attribute attribute = AttributeUtils.getAttribute("GENERIC_ARMOR");
        AttributeModifier modifier = new AttributeModifier("test", 10, AttributeModifier.Operation.ADD_NUMBER);
        LinkedHashMap<Attribute, AttributeModifier> map = new LinkedHashMap<>();
        map.put(attribute, modifier);

        AttributeUtils.addAttributeOnItemMeta(meta, Material.DIAMOND_SWORD, map, true ,true, true);

        item.setItemMeta(meta);

        meta = item.getItemMeta();

        Attribute attribute1 = AttributeUtils.getAttribute("GENERIC_MAX_HEALTH");
        AttributeModifier modifier1 = new AttributeModifier("test1", 10, AttributeModifier.Operation.ADD_NUMBER);
        LinkedHashMap<Attribute, AttributeModifier> map1 = new LinkedHashMap<>();
        map1.put(attribute1, modifier1);

        AttributeUtils.addAttributeOnItemMeta(meta, Material.DIAMOND_SWORD, map1, true ,true, true);

        item.setItemMeta(meta);

        p.getInventory().addItem(item);
    }

    public void runTest2(Player p){
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();

        LinkedHashMap<Attribute, AttributeModifier> map = new LinkedHashMap<>();


        AttributeUtils.addAttributeOnItemMeta(meta, Material.DIAMOND_SWORD, map, false ,true, true);

        item.setItemMeta(meta);


        p.getInventory().addItem(item);
    } */

    /* @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(StructureGrowEvent e) {

        Player p = e.getPlayer();
        SsomarDev.testMsg("ATTEMPT GROW ", true);
    }*/




}
