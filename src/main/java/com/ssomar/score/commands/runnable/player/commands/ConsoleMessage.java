package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringJoiner;

public final class ConsoleMessage extends PlayerCommand {

	@Override
	public void run(@Nullable final Player player, final Player receiver, final List<String> args, final ActionInfo aInfo) {
		System.out.println(StringJoiner.join(args, " "));
	}

	@NotNull
	@Override
	public Optional<String> verify(final List<String> args, final boolean isFinalVerification) {
		return args.isEmpty() ? Optional.of(notEnoughArgs + this.getTemplate()) : Optional.empty();
	}

	@NotNull
	@Override
	public List<String> getNames() {
		final List<String> names = new ArrayList<>();

		names.add("CONSOLEMESSAGE");

		return names;
	}

	@NotNull
	@Override
	public String getTemplate() {
		return "CONSOLEMESSAGE {text}";
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
