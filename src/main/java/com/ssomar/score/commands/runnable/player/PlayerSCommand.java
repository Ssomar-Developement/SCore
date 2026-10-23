package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.commands.runnable.SCommandToExec;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;


public interface PlayerSCommand {

    /**
     * To further understand this method's arguments, here's an example:<br/>
     * - If you typed in a SCore player command in Target Commands, the <code>launcher</code>'s details
     * will return the one who triggered the activator while the <code>receiver</code>'s details
     * return the victim of the command. In an activator or some feature where only the Player Commands
     * editor exists, the player who triggered the activator can be both the <code>launcher</code> and the <code>receiver</code>.
     * @param launcher the one who executed the command
     * @param receiver the victim of the command
     * @param sCommandToExec
     */
    void run(@Nullable Player launcher, Player receiver, SCommandToExec sCommandToExec);
}
