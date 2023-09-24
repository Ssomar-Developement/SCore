package com.ssomar.score.commands.runnable.entity.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.EntityCommand;
import com.ssomar.score.utils.strings.StringJoiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SendMessage extends EntityCommand {

	@Override
	public void run(final Player p, final Entity entity, final List<String> args, final ActionInfo aInfo) {
		sm.sendMessage(p, StringJoiner.join(args, " "));
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
		names.add("SENDMESSAGE");
		return names;
	}

	@NotNull
	@Override
	public String getTemplate() {
		return "SENDMESSAGE {you msg here}";
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
