package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTType;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class TESTEVENT_TODELETE  implements Listener {



    @EventHandler
    public void PlayerInteractEntityEvent(PlayerInteractEntityEvent e) {

        Entity entity = e.getRightClicked();
        NBTEntity nbtent = new NBTEntity(entity);
        for(String key : nbtent.getKeys()) {
            NBTType type = nbtent.getType(key);
            SsomarDev.testMsg("key: " + key+ " value: " + nbtent.getString(key)+ " isNull: " + (nbtent.getBoolean(key) == null));
        }
    }
}
