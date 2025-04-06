package com.ssomar.score.features.custom.required;

import com.ssomar.score.utils.placeholders.StringPlaceholder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface RequiredPlayerInterface {

    boolean verify(Player player, Event event, StringPlaceholder sp);

    void take(Player player, StringPlaceholder sp);
}
