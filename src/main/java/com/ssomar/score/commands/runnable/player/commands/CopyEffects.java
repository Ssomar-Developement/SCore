package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;

/* COPYEFFECTS */
public final class CopyEffects extends PlayerCommand {

	@Override
	public void run(@Nullable final Player player, final Player receiver, final List<String> args, final ActionInfo aInfo) {
		if (player == null || player.isDead() || receiver.isDead())
			return;

		player.addPotionEffects(new HashSet<>(receiver.getActivePotionEffects()));
	}

	@NotNull
	@Override
	public Optional<String> verify(final List<String> args, final boolean isFinalVerification) {
		return Optional.empty();
	}

	@NotNull
	@Override
	public List<String> getNames() {
		final List<String> names = new ArrayList<>();

		names.add("COPYEFFECTS");

		return names;
	}

	@NotNull
	@Override
	public String getTemplate() {
		return "COPYEFFECTS";
	}

	@Nullable
	@Override
	public ChatColor getColor() {
		return null;
	}

	@Nullable
	@Override
	public ChatColor getExtraColor() {
		return null;
	}
}
