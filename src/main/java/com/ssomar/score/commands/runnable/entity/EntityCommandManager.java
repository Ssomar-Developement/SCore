package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.entity.commands.*;

import java.util.ArrayList;
import java.util.List;


public class EntityCommandManager extends CommandManager<EntityCommand> {

    private static EntityCommandManager instance;

    private EntityCommandManager() {
        List<EntityCommand> commands = new ArrayList<>();
        commands.add(new TeleportPosition());
        commands.add(new TeleportEntityToPlayer());
        commands.add(new TeleportPlayerToEntity());
        commands.add(new SendMessage());
        commands.add(new Kill());
        commands.add(new ChangeToMythicMob());
        commands.add(new ChangeTo());
        commands.add(new DamageResistance());
        commands.add(new DropItem());
        commands.add(new DropExecutableItem());
        commands.add(new DropExecutableBlock());
        commands.add(new Heal());
        commands.add(new Jump());
        commands.add(new DamageNoKnockback());
        commands.add(new Damage());
        commands.add(new SetBaby());
        commands.add(new SetAdult());
        commands.add(new SetAI());
        commands.add(new SetName());
        commands.add(new Burn());
        commands.add(new BackDash());
        commands.add(new CustomDash1());
        commands.add(new Glowing());
        commands.add(new SetGlow());
        commands.add(new Around());
        commands.add(new MobAround());
        commands.add(new RemoveGlow());
        commands.add(new StrikeLightning());
        commands.add(new StunEnable());
        commands.add(new StunDisable());
        commands.add(new StunDisable());
        commands.add(new PlayerRideOnEntity());
        commands.add(new Shear());
        commands.add(new Nearest());
        commands.add(new MobNearest());
        commands.add(new AngryAt());
        commands.add(new CustomDash2());
        commands.add(new CustomDash3());
        commands.add(new Spin());
        if (!SCore.is1v11Less()) {
            commands.add(new ParticleCommand());
            commands.add(new GlacialFreeze());
        }
        commands.add(new Customtest());
        setCommands(commands);
    }

    public static EntityCommandManager getInstance() {
        if (instance == null) instance = new EntityCommandManager();
        return instance;
    }
}
