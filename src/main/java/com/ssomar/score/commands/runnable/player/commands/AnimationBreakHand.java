package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/* TOTEM_ANIMATION */
public class AnimationBreakHand extends PlayerCommand{

	@Override
	public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo) {
		receiver.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
	}

	@Override
	public String verify(List<String> args) {
		String error = "";

		return error;
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		names.add("~ANIMATION_BREAK_MAIN_HAND");
		names.add("BREAK_MAIN_HAND_ANIMATION");
		return names;
	}

	@Override
	public String getTemplate() {
		return "BREAK_MAIN_HAND_ANIMATION";
	}

	@Override
	public ChatColor getColor() {
		return ChatColor.AQUA;
	}

	@Override
	public ChatColor getExtraColor() {
		return ChatColor.GOLD;
	}
}
