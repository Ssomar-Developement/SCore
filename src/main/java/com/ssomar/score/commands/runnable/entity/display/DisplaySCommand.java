package com.ssomar.score.commands.runnable.entity.display;

import com.ssomar.score.commands.runnable.ActionInfo;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface DisplaySCommand {

    void run(Player p, Entity entity, List<String> args, ActionInfo aInfo);
}
