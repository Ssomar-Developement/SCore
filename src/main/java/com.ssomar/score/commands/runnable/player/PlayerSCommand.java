package com.ssomar.score.commands.runnable.player;

import java.util.List;

import org.bukkit.entity.Player;

import com.ssomar.score.commands.runnable.ActionInfo;


public interface PlayerSCommand {

    void run(Player p, Player receiver, List<String> args, ActionInfo aInfo);
}
