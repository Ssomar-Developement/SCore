package com.ssomar.score.sobject;


import org.bukkit.Location;
import org.bukkit.entity.Item;

public interface SObjectDroppable {

    Item dropItem(Location location, int amount);

}
