package com.ssomar.score.commands.runnable.entity.display;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.entity.commands.*;
import com.ssomar.score.commands.runnable.entity.display.commands.SetFurniture;
import com.ssomar.score.commands.runnable.mixed_player_entity.MixedCommandsManager;

import java.util.ArrayList;
import java.util.List;


public class DisplayCommandManager extends CommandManager<SCommand> {

    private static DisplayCommandManager instance;

    private DisplayCommandManager() {
        List<SCommand> commands = new ArrayList<>();
        commands.add(new SetFurniture());


        commands.add(new Around());
        commands.add(new TeleportPlayerToEntity());
        commands.add(new SendMessage());
        commands.add(new DropItem());
        commands.add(new DropExecutableItem());
        commands.add(new DropExecutableBlock());
        commands.add(new MobAround());
        commands.add(new PlayerRideOnEntity());
        commands.add(new If());
        if (!SCore.is1v11Less()) {
            commands.add(new ParticleCommand());
        }

        commands.addAll(MixedCommandsManager.getInstance().getDisplayCommands());

        /* Sort by priority */
        commands.sort((c1, c2) -> {
            if (c1.getPriority() < c2.getPriority()) return 1;
            else if (c1.getPriority() > c2.getPriority()) return -1;
            else return 0;
        });

        setCommands(commands);
    }

    public static DisplayCommandManager getInstance() {
        if (instance == null) instance = new DisplayCommandManager();
        return instance;
    }
}
