package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringJoiner;

/* OPMESSAGE {text} */
public final class OpMessage extends PlayerCommand {

	@Override
	public void run(@Nullable final Player player, final Player receiver, final List<String> args, final ActionInfo aInfo) {
		final String joined = StringJoiner.join(args, " ");
		System.out.println(joined);

		for (final Player online : Bukkit.getServer().getOnlinePlayers())
			if (online.isOp())
				sm.sendMessage(online, joined);
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

		names.add("OPMESSAGE");

		return names;
	}

	@NotNull
	@Override
	public String getTemplate() {
		return "OPMESSAGE {text}";
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
