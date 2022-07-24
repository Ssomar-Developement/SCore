package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.commands.runnable.ActionInfo;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface EntitySCommand {

    void run(Player p, Entity entity, List<String> args, ActionInfo aInfo);
}
