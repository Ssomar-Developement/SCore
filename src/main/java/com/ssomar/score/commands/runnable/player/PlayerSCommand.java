package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.commands.runnable.ActionInfo;
import org.bukkit.entity.Player;

import java.util.List;


public interface PlayerSCommand {

    void run(Player p, Player receiver, List<String> args, ActionInfo aInfo);
}
