package com.ssomar.score.commands.runnable.mixed_player_entity;

import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.mixed_player_entity.commands.*;
import com.ssomar.score.commands.runnable.mixed_player_entity.commands.CopyEffects;

import java.util.ArrayList;
import java.util.List;

public class MixedCommandsManager extends CommandManager<SCommand> {

    private static MixedCommandsManager instance;

    public MixedCommandsManager() {
        List<SCommand> commands = new ArrayList<>();
        commands.add(new BackDash());
        commands.add(new Burn());
        commands.add(new CopyEffects());
        commands.add(new CustomDash1());
        commands.add(new CustomDash2());
        commands.add(new CustomDash3());
        //commands.add(new Customtest());
        commands.add(new Damage());
        commands.add(DamageBoost.getInstance());
        commands.add(new DamageNoKnockback());
        commands.add(DamageResistance.getInstance());
        commands.add(new ForceDrop());
        commands.add(new FrontDash());
        commands.add(new GlacialFreeze());
        commands.add(new Glowing());
        commands.add(new Invulnerability());
        commands.add(new Jump());
        commands.add(new RemoveBurn());
        commands.add(new RemoveGlow());
        commands.add(new SetGlow());
        commands.add(new SetHealth());
        commands.add(new SetPitch());
        commands.add(new SetYaw());
        commands.add(new Spin());
        commands.add(new StrikeLightning());
        commands.add(new StunDisable());
        commands.add(new StunEnable());
        commands.add(new TeleportOnCursor());
        commands.add(new UnsafeTeleportOnCursor());

        setCommands(commands);
    }

    public static MixedCommandsManager getInstance() {
        if (instance == null) instance = new MixedCommandsManager();
        return instance;
    }
}
