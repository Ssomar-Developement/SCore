package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.StunEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StunEnable extends PlayerCommand{

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		Location correctAnimation = receiver.getLocation();
		correctAnimation.setPitch(-10);
		receiver.teleport(correctAnimation);
		receiver.setGliding(true);
		StunEvent.stunPlayers.put(receiver.getUniqueId(), false);
		BukkitRunnable runnable3 = new BukkitRunnable() {
			@Override
			public void run() {
				if(StunEvent.stunPlayers.containsKey(receiver.getUniqueId())) StunEvent.stunPlayers.put(receiver.getUniqueId(), true);
			}
		};
		runnable3.runTaskLater(SCore.plugin, 20);
	}


	@Override
	public String verify(List<String> args) {
		String error ="";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("STUN_ENABLE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "STUN_ENABLE";
	}

	@Override
	public ChatColor getColor() {
		return null;
	}

	@Override
	public ChatColor getExtraColor() {
		return null;
	}

}