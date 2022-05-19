package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.UUID;

import com.ssomar.score.events.PlaceholderLastDamageDealtEvent;
import com.ssomar.score.utils.NTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPlaceholders extends PlayerPlaceholdersAbstract{

	public PlayerPlaceholders() {
		super("player", true);
	}
}
