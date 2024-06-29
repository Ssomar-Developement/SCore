package com.ssomar.score.commands.runnable.mixed_player_entity;

import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.entity.EntitySCommand;
import com.ssomar.score.commands.runnable.entity.display.DisplaySCommand;
import com.ssomar.score.commands.runnable.player.PlayerSCommand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class MixedCommand extends SCommand implements PlayerSCommand, EntitySCommand, DisplaySCommand {
    @Override
    public void run(Player p, Player receiver, List<String> args, ActionInfo aInfo){
        this.run(p, (LivingEntity) receiver, args, aInfo);
    }
}
