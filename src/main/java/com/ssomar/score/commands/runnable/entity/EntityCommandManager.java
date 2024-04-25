package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.entity.commands.*;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommandsManager;

import java.util.ArrayList;
import java.util.List;


public class EntityCommandManager extends CommandManager<SCommand> {

    private static EntityCommandManager instance;

    private EntityCommandManager() {
        List<SCommand> commands = new ArrayList<>();
        commands.add(new Around());
        commands.add(new TeleportPosition());
        commands.add(new TeleportEntityToPlayer());
        commands.add(new TeleportPlayerToEntity());
        commands.add(new SendMessage());
        commands.add(new Kill());
        commands.add(new ChangeToMythicMob());
        commands.add(new ChangeTo());
        commands.add(new DropItem());
        commands.add(new DropExecutableItem());
        commands.add(new DropExecutableBlock());
        commands.add(new Heal());
        commands.add(new MobAround());
        commands.add(new SetBaby());
        commands.add(new SetAdult());
        commands.add(new SetAI());
        commands.add(new SetName());
        commands.add(new PlayerRideOnEntity());
        commands.add(new Shear());
        commands.add(new AngryAt());
        commands.add(new Steal());
        commands.add(new If());
        if(SCore.is1v16Plus()){
            commands.add(new Awareness());
        }
        if (!SCore.is1v11Less()) {
            commands.add(new ParticleCommand());
        }

        commands.addAll(MixedCommandsManager.getInstance().getCommands());

        /* Sort by priority */
        commands.sort((c1, c2) -> {
            if (c1.getPriority() < c2.getPriority()) return 1;
            else if (c1.getPriority() > c2.getPriority()) return -1;
            else return 0;
        });

        setCommands(commands);
    }

    public static EntityCommandManager getInstance() {
        if (instance == null) instance = new EntityCommandManager();
        return instance;
    }
}
