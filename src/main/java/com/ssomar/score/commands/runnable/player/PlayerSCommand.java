package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.commands.runnable.ActionInfo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface PlayerSCommand {

    void run(@Nullable Player launcher, Player receiver, List<String> args, ActionInfo aInfo);
}
