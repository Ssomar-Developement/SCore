package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.commands.runnable.SCommandToExec;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface EntitySCommand {

    /**
     * Executes code when this custom command is called.
     * @param p (The user of the item)
     * @param entity (The victim of the command. Typecast it to player via (Player) when needed. Check spigot api for the finer details)
     * @param sCommandToExec (The list of arguments after the custom command)
     */
    void run(Player p, Entity entity, SCommandToExec sCommandToExec);
}
