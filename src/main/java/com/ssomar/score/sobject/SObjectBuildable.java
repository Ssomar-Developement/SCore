package com.ssomar.score.sobject;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public interface SObjectBuildable {
    ItemStack buildItem(int quantity, Optional<Player> creatorOpt);

    ItemStack buildItem(int quantity, Optional<Player> creatorOpt,  Map<String, Object> settings);

    Item dropItem(Location location, int amount);

    Item dropItem(Location location, int amount, Optional<Player> creatorOpt,  Map<String, Object> settings);

    boolean canBeStacked();

    String getItemName();
}

