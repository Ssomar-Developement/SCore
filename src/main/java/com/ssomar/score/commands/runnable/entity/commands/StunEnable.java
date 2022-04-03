package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.StunEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class StunEnable extends EntityCommand {

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		if(!(entity instanceof LivingEntity)) return;
		LivingEntity receiver = (LivingEntity)entity;
		receiver.setAI(false);
		Location correctAnimation = receiver.getLocation();
		correctAnimation.setPitch(45);
		receiver.teleport(correctAnimation);
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