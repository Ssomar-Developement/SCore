package com.ssomar.score.hardness;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HardnessModifier {

    boolean isEnabled();

    boolean isTriggered(Player player, Block block, ItemStack tool);

    void breakBlock(Player player, Block block, ItemStack tool);

    long getPeriod(Player player, Block block, ItemStack tool);

    boolean isPeriodInTicks();
}