package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ArgumentChecker;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.numbers.NTools;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.utils.strings.StringJoiner;

/**
 * A simple command that sends a boss bar to a player.
 * <p>
 * Syntax: BOSSBAR {ticks} {color} {text}.
 */
public final class Bossbar extends PlayerCommand {

	@Override
	public void run(final Player player, final Player receiver, final List<String> args, final ActionInfo aInfo) {
		final Integer duration = NTools.getInteger(args.get(0)).get();
		final BarColor color = BarColor.valueOf(args.get(1));

		final BossBar bossBar = Bukkit.createBossBar(StringJoiner.joinRange(2, StringConverter.coloredString(args)), color, BarStyle.SOLID);
		bossBar.addPlayer(receiver);

		Bukkit.getScheduler().runTaskLater(SCore.plugin, bossBar::removeAll, duration);
	}

	@NotNull
	@Override
	public Optional<String> verify(final List<String> args, final boolean isFinalVerification) {
		if (args.size() < 3)
			return Optional.of(notEnoughArgs + this.getTemplate());

		final ArgumentChecker ac = checkDouble(args.get(0), isFinalVerification, this.getTemplate());

		if (!ac.isValid())
			return Optional.of(ac.getError());

		final ArgumentChecker ac2 = checkBarColor(args.get(1), isFinalVerification, this.getTemplate());

		if (!ac2.isValid())
			return Optional.of(ac2.getError());

		return Optional.empty();
	}

	@NotNull
	@Override
	public List<String> getNames() {
		final List<String> names = new ArrayList<>();

		names.add("BOSSBAR");

		return names;
	}

	@NotNull
	@Override
	public String getTemplate() {
		return "BOSSBAR {ticks} {color} {text}";
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
