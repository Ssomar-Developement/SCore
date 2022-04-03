package com.ssomar.score.commands.runnable.entity.commands;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.events.StunEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StunDisable extends EntityCommand {

	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {
		if(!(entity instanceof LivingEntity)) return;
		LivingEntity receiver = (LivingEntity)entity;
		receiver.setAI(true);
	}


	@Override
	public String verify(List<String> args) {
		String error ="";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("STUN_DISABLE");
		return names;
	}

	@Override
	public String getTemplate() {
		return "STUN_DISABLE";
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