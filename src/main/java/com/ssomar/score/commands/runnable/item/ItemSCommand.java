package com.ssomar.score.commands.runnable.item;

import com.ssomar.score.commands.runnable.SCommandToExec;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;


public interface ItemSCommand {

    void run(@Nullable Player launcher, ItemStack item, SCommandToExec sCommandToExec);
}
