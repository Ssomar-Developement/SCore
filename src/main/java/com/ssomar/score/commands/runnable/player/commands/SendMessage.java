package com.ssomar.score.commands.runnable.player.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.player.PlayerCommand;
import com.ssomar.score.utils.strings.StringJoiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* SENDMESSAGE {message} */
public class SendMessage extends PlayerCommand {

	@Override
	public void run(final Player player, final Player receiver, final List<String> args, final ActionInfo aInfo) {
		if (!args.isEmpty())
			sm.sendMessage(receiver, StringJoiner.join(args, " "), false);
	}

	@NotNull
	@Override
	public Optional<String> verify(final List<String> args, final boolean isFinalVerification) {
		if (args.isEmpty())
			return Optional.of(notEnoughArgs + this.getTemplate());

		return Optional.empty();
	}

	@NotNull
	@Override
	public List<String> getNames() {
		final List<String> names = new ArrayList<>();
		names.add("SENDMESSAGE");
		return names;
	}

	@NotNull
	@Override
	public String getTemplate() {
		return "SENDMESSAGE {message}";
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
