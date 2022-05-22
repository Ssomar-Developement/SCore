package com.ssomar.testRecode.features.custom.required;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface RequiredPlayerInterface {

    boolean verify(Player player, Event event);

    void take(Player player);
}
