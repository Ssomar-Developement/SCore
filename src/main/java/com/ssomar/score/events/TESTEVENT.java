package com.ssomar.score.events;

import com.ssomar.score.SsomarDev;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;

public class TESTEVENT implements Listener {


    @EventHandler
    public void onPlayerInputEvent(PlayerInputEvent event){
        SsomarDev.testMsg("PlayerInputEvent triggered: " + event.getPlayer().getName() + " - Forward: " + event.getInput(), true);
    }
}
