package com.ssomar.score.commands.runnable.block;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.ssomar.score.commands.runnable.ActionInfo;
import org.jetbrains.annotations.Nullable;

public interface BlockSCommand {

    void run(@Nullable Player p, @NotNull Block block, Material oldMaterial, List<String> args, ActionInfo aInfo);
}
