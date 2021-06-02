package com.ssomar.score.commands.runnable.block;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;

public interface BlockCommandInterface {

	public abstract void run(Player p, Block block, Material oldMaterial, List<String> args, ActionInfo aInfo, boolean silenceOutput);

	public abstract String verify(List<String> args);
}
