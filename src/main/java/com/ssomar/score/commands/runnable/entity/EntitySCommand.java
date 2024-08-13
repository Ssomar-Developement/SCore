package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.commands.runnable.SCommandToExec;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface EntitySCommand {

    void run(Player p, Entity entity, SCommandToExec sCommandToExec);
}
