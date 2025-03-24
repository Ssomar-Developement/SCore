package com.ssomar.score.commands.runnable.item;

import com.ssomar.score.commands.runnable.SCommandToExec;
import com.ssomar.score.utils.DynamicMeta;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;


public interface ItemMetaSCommand {

    void run(@Nullable Player launcher, DynamicMeta meta, SCommandToExec sCommandToExec);
}
