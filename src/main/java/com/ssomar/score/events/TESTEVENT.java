package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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

        /*ItemStack item = p.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        SsomarDev.testMsg("Meta: " + meta, true);
        SsomarDev.testMsg("Meta blo: " + (meta instanceof BlockDataMeta), true);
        SsomarDev.testMsg("Meta sta: " + (meta instanceof BlockStateMeta), true);

        if (meta instanceof BlockDataMeta) {
            BlockDataMeta blockStateMeta = (BlockDataMeta) meta;
            SsomarDev.testMsg("BlockDataMeta: " + blockStateMeta.getAsString(), true);
        }

        if (meta instanceof BlockStateMeta) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            SsomarDev.testMsg("BlockStateMeta: " + blockStateMeta.getBlockState(), true);
            CreatureSpawner spawner = (CreatureSpawner) blockStateMeta.getBlockState();
            SsomarDev.testMsg("Spawner: " + spawner.getSpawnedType(), true);
            SsomarDev.testMsg("Spawner2: " + spawner.getSpawnedEntity().getAsString(), true);
        } */
       // PlayerInventory inv = p.getInventory();
       // inv.addItem(new ItemStack(Material.DIAMOND));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerToggleSneakEventt(PlayerToggleSneakEvent e) {
       /* Player p = e.getPlayer();
        Location loc = p.getLocation();
        // -1
        loc = loc.add(0, -1, 0);
        Block block = loc.getBlock();
        List<Loot> loots = new ArrayList<>();
        Drop drop = new Drop(loots, false, false, NexoBlocks.stringMechanic(block).getItemID());
        NexoBlocks.remove(block.getLocation(), null, drop); */
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
