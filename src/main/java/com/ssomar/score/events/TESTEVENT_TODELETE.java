package com.ssomar.score.events;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.sobject.sactivator.DetailedBlocks;
import com.ssomar.score.utils.safebreak.SafeBreak;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TESTEVENT_TODELETE implements Listener {

    @EventHandler
    public void PlayerInteractEntityEvent(PlayerInteractAtEntityEvent e) {

        /*SsomarDev.testMsg("PlayerInteractAtEntityEvent");
        Entity entity = e.getRightClicked();
        if(entity instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entity;
            armorStand.setVisible(true);
            SsomarDev.testMsg(" passengers ? " + armorStand.getPassengers().size());
            for(EquipmentSlot slot : EquipmentSlot.values()) {
                SsomarDev.testMsg(" slot: " + slot + " >>" + armorStand.getEquipment().getItem(slot).getType());
            }
            BukkitRunnable runnable3 = new BukkitRunnable() {
                @Override
                public void run() {
                    armorStand.remove();
                }
            };
            runnable3.runTaskLater(SCore.plugin, 60);

        }
        if(entity instanceof ItemFrame) {
            ItemFrame armorStand = (ItemFrame) entity;
            armorStand.setVisible(true);
        }*/
        /*Entity entity = e.getRightClicked();
        NBTEntity nbtent = new NBTEntity(entity);
        entity.getServer().broadcastMessage("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        for(String key : nbtent.getKeys()) {
            NBTType type = nbtent.getType(key);
            switch (type) {

                case NBTTagEnd:
                    break;
                case NBTTagByte:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getByte(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getByte(key));
                    break;
                case NBTTagShort:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getShort(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getShort(key));
                    break;
                case NBTTagInt:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getInteger(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getInteger(key));
                    break;
                case NBTTagLong:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getLong(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getLong(key));
                    break;
                case NBTTagFloat:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getFloat(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getFloat(key));
                    break;
                case NBTTagDouble:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getDouble(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getDouble(key));
                    break;
                case NBTTagByteArray:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getByteArray(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getByteArray(key));
                    break;
                case NBTTagIntArray:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getIntArray(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getIntArray(key));
                    break;
                case NBTTagString:
                    entity.getServer().broadcastMessage(key + ": " + nbtent.getString(key));
                    SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getString(key));
                    break;
                case NBTTagList:

                    break;
                case NBTTagCompound:
                    break;
            }

        }*/
        //entity.getServer().broadcastMessage("+++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {

       /* SsomarDev.testMsg("PlayerInteractEvent");
        if (e.getClickedBlock() != null) {
            SsomarDev.testMsg("ClickedBlock: " + e.getClickedBlock().getType());
            Block underBlock = e.getClickedBlock();
            Block block = underBlock.getRelative(BlockFace.UP);
            BlockPlaceEvent bbE = new BlockPlaceEvent(block, block.getState(), underBlock, e.getItem(), e.getPlayer(), true, EquipmentSlot.HAND);
            bbE.setCancelled(false);

            Bukkit.getPluginManager().callEvent(bbE);
        }
        e.setCancelled(true);
*/
    }
}
