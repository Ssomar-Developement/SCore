package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class OwnerPlaceholders extends PlayerPlaceholdersAbstract {

    public OwnerPlaceholders() {
        super("owner", false);
    }
}