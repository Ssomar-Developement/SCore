package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.commands.runnable.SCommandToExec;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;


public interface PlayerSCommand {

    void run(@Nullable Player launcher, Player receiver, SCommandToExec sCommandToExec);
}
