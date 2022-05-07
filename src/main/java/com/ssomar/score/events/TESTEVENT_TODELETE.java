package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTType;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class TESTEVENT_TODELETE  implements Listener {



    @EventHandler
    public void PlayerInteractEntityEvent(PlayerInteractEntityEvent e) {

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

    /*@EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {

        if(e.getClickedBlock() != null) {
            Block block = e.getClickedBlock();
            com.ssomar.test.Test.test(block, e.getPlayer(), e.getPlayer().getInventory().getItem(0));
        }

    }*/
}
