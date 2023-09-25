package com.ssomar.score.commands.runnable.block.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.block.BlockCommand;
import com.ssomar.score.utils.strings.StringJoiner;

public class SendMessage extends BlockCommand {

	@Override
	public void run(@Nullable final Player player, @NotNull final Block block, final Material oldMaterial, final List<String> args, final ActionInfo aInfo) {
		if (player != null)
			sm.sendMessage(player, StringJoiner.join(args, " "));
	}

	@NotNull
	@Override
	public Optional<String> verify(final List<String> args, final boolean isFinalVerification) {
		final String error = "";
		return error.isEmpty() ? Optional.empty() : Optional.of(error);
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
