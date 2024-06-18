package com.ssomar.score.hardness;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RealHardnessModifier implements HardnessModifier{

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isTriggered(Player player, Block block, ItemStack tool) {
        return true;
    }

    @Override
    public void breakBlock(Player player, Block block, ItemStack tool) {
        block.breakNaturally();
    }

    @Override
    public long getPeriod(Player player, Block block, ItemStack tool) {
        return 10;
    }

    @Override
    public boolean isPeriodInTicks() {
        return false;
    }
}
