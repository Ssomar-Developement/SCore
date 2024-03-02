package com.ssomar.score.commands.runnable.mixed_player_entity;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.mixed_player_entity.commands.*;
import com.ssomar.score.commands.runnable.mixed_player_entity.commands.equipmentvisualreplace.EquipmentVisualCancel;
import com.ssomar.score.commands.runnable.mixed_player_entity.commands.equipmentvisualreplace.EquipmentVisualReplace;

import java.util.ArrayList;
import java.util.List;

public class MixedCommandsManager extends CommandManager<SCommand> {

    private static MixedCommandsManager instance;

    public MixedCommandsManager() {
        List<SCommand> commands = new ArrayList<>();
        commands.add(new AllMobs());
        commands.add(new AllPlayers());
        commands.add(new AnimationBreakBoots());
        commands.add(new AnimationBreakChestplate());
        commands.add(new AnimationBreakHand());
        commands.add(new AnimationBreakHelmet());
        commands.add(new AnimationBreakLeggings());
        commands.add(new AnimationBreakOffHand());
        commands.add(new AnimationHurt());
        commands.add(new AnimationSwingMainHand());
        commands.add(new AnimationSwingOffHand());
        commands.add(new AnimationTeleportEnder());
        commands.add(new AnimationTotem());
        commands.add(new BackDash());
        commands.add(new Burn());
        commands.add(new ConsoleMessage());
        commands.add(new CopyEffects());
        commands.add(new CustomDash1());
        commands.add(new CustomDash2());
        commands.add(new CustomDash3());
        //commands.add(new Customtest());
        commands.add(DamageBoost.getInstance());
        commands.add(new DamageNoKnockback());
        commands.add(DamageResistance.getInstance());
        commands.add(new Damage());
        commands.add(new ForceDrop());
        commands.add(new FrontDash());
        commands.add(new GlacialFreeze());
        commands.add(new Glowing());
        commands.add(new Invulnerability());
        commands.add(new Jump());
        commands.add(new LaunchEntity());
        commands.add(new MobNearest());
        commands.add(new Nearest());
        commands.add(new OpMessage());
        commands.add(new RegainHealth());
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
        commands.add(new TranferItem());
        commands.add(new UnsafeTeleportOnCursor());
        commands.add(new WorldTeleport());

        if (!SCore.is1v12Less()) {
            commands.add(new EquipmentVisualReplace());
            commands.add(new EquipmentVisualCancel());
        }

        setCommands(commands);
    }

    public static MixedCommandsManager getInstance() {
        if (instance == null) instance = new MixedCommandsManager();
        return instance;
    }
}
