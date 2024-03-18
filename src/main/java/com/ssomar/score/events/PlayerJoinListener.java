package com.ssomar.score.events;

import com.ssomar.score.commands.runnable.player.commands.absorption.AbsorptionManager;
import com.ssomar.score.commands.runnable.player.commands.sudoop.SUDOOPManager;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.SecurityOPQuery;
import com.ssomar.score.fly.FlyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void playerReconnexion(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (SUDOOPManager.getInstance().getPlayersThatMustBeDeOP().contains(p.getUniqueId())) {
            p.setOp(false);
            SecurityOPQuery.deletePlayerOP(Database.getInstance().connect(), p, true);
        }

        if (FlyManager.getInstance().isPlayerWithFly(p)) {
            p.setAllowFlight(true);
        }

        AbsorptionManager.getInstance().onConnect(p);
    }

   /*  @EventHandler
    public void playerPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        EquipmentSlot hand = e.getHand();

        e.setCancelled(true);
        p.stopAllSounds();
        p.setCooldown(item.getType(), 0);
    } */


   /*  @EventHandler(priority = EventPriority.HIGH)
    public void playerReconnexion(BlockPhysicsEvent e) {
        Block from = e.getSourceBlock();


        if(e.getChangedType().toString().contains("SUSPICIOUS") && from.getLocation().equals(e.getBlock().getLocation())){
            System.out.println("VALID BlockPhysicsEvent "+e.getChangedType());
            BlockData bd = from.getBlockData();
            if(bd instanceof Brushable){
                Brushable brushable = (Brushable) bd;
                System.out.println(">>> "+brushable.getDusted()+" / "+brushable.getMaximumDusted());
            }
        }
    }*/
}

