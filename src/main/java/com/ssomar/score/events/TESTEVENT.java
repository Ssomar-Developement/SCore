package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class TESTEVENT implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
        if (!e.isSneaking()) return;

        Player p = e.getPlayer();
        // player named Ssomar only
        if (!p.getName().equals("Ssomar")) return;

        Block block = p.getTargetBlock(null, 5);
        SsomarDev.testMsg("Block: " + block.getType(), true);

       // ShulkerPacketUtil shulkerPacketUtils = new ShulkerPacketUtil();
        //int text = shulkerPacketUtils.sendClientTextDisplay(p, block.getLocation().add(0,2,0));
        //int shulker = shulkerPacketUtils.sendClientShulker(p, block.getLocation().add(0,2,0));
        //shulkerPacketUtils.mountShulker(p, shulker, text);

        //shulkerPacketUtils.removeClientShulker(p, shulker);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEvent(ProjectileHitEvent e) {
       Block block = e.getHitBlock();
        // SsomarDev.testMsg("Block: " + block.getType(), true);


    }

    /*@EventHandler(priority = EventPriority.LOWEST)
    public void playerReconnexion(PlayerLinksSendEvent e) {
        Player p = e.getPlayer();
        ResourcePackRequest request = ResourcePackRequest.resourcePackRequest().required(true).callback(new ResourcePackCallbackTest()).build();
        p.sendResourcePacks(request);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerReconnexion(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ResourcePackRequest request = ResourcePackRequest.resourcePackRequest().callback(new ResourcePackCallbackTest()).build();
        p.sendResourcePacks(request);
    }*/


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
