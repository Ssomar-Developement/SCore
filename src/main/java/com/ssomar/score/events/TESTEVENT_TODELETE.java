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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TESTEVENT_TODELETE implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteractEvent1(PlayerInteractEvent e) {
        SsomarDev.testMsg("DEBUG INTERACT 1>>"+e.isCancelled());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        SsomarDev.testMsg("DEBUG INTERACT 2>>"+e.isCancelled());
    }
}
