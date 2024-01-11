package com.ssomar.customblockdata.events.fallingblock;

import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FallingBlockStartFallingEvent extends EntityChangeBlockEvent {

    private EntityChangeBlockEvent event;
    public FallingBlockStartFallingEvent(EntityChangeBlockEvent event) {
        super(event.getEntity(), event.getBlock(), event.getTo().createBlockData());
        this.event = event;
    }

    @Override
    public void setCancelled(boolean cancel) {
        event.setCancelled(cancel);
    }
}
