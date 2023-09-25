package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.player.commands.*;
import com.ssomar.score.commands.runnable.player.commands.Setlore;
import com.ssomar.score.commands.runnable.player.commands.equipmentvisualreplace.EquipmentVisualCancel;
import com.ssomar.score.commands.runnable.player.commands.equipmentvisualreplace.EquipmentVisualReplace;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandManager extends CommandManager<PlayerCommand> {

    private static PlayerCommandManager instance;

    public PlayerCommandManager() {
        List<PlayerCommand> commands = new ArrayList<>();
        commands.add(new Around());
        commands.add(new MobAround());
        commands.add(new Addlore());
        commands.add(new Setlore());
        commands.add(new SetItemColor());
        commands.add(new SetItemName());
        commands.add(new SetItemCustomModelData());
        commands.add(new SendBlankMessage());
        commands.add(new SendMessage());
        commands.add(new SendCenteredMessage());
        /* SUDOOP MUST BE BEFORE SUDO */
        commands.add(new SudoOp());
        commands.add(new Sudo());
        commands.add(new FlyOn());
        commands.add(new FlyOff());
        commands.add(new SetBlockPos());
        commands.add(new SetBlock());
        commands.add(new SetTempBlockPos());
        commands.add(new ReplaceBlock());
        commands.add(new CustomDash1());
        commands.add(new CustomDash2());
        commands.add(new CustomDash3());
        commands.add(new ProjectileCustomDash1());
        commands.add(new FrontDash());
        commands.add(new Glowing());
        commands.add(new SetGlow());
        commands.add(new RemoveGlow());
        commands.add(new BackDash());
        commands.add(new TeleportOnCursor());
        commands.add(new UnsafeTeleportOnCursor());
        commands.add(new WorldTeleport());
        commands.add(new SpawnEntityOnCursor());
        commands.add(DisableFlyActivation.getInstance());
        commands.add(DisableGlideActivation.getInstance());
        /* DAMAGE_RESISTANCE MUST BE BEFORE DAMAGE */
        commands.add(DamageBoost.getInstance());
        commands.add(DamageResistance.getInstance());
        commands.add(new Invulnerability());
        commands.add(new DamageNoKnockback());
        commands.add(new Damage());
        commands.add(new LaunchEntity());
        commands.add(new Launch());
        if (!SCore.is1v12Less()) {
            commands.add(new LocatedLaunch());
            commands.add(new CropsGrowthBoost());
            /* No damageable class before 1.12 */
            commands.add(new ModifyDurability());
            commands.add(new EquipmentVisualReplace());
            commands.add(new EquipmentVisualCancel());
        }
        if(SCore.is1v16Plus()){
            commands.add(new Absorption());
        }
        commands.add(new AllMobs());
        commands.add(new AllPlayers());
        commands.add(new Nearest());
        commands.add(new MobNearest());
        commands.add(new MixHotbar());
        commands.add(new Burn());
        commands.add(new Jump());
        commands.add(new RemoveBurn());
        commands.add(new SetHealth());
        commands.add(new SetExecutableBlock());
        commands.add(new SetMaterialCooldown());
        commands.add(new StrikeLightning());
        commands.add(new RegainHealth());
        commands.add(new RegainFood());
        commands.add(new RegainMagic());
        commands.add(new RegainSaturation());
        commands.add(new Head());
        commands.add(new Swaphand());
        commands.add(new Chestplate());
        commands.add(new Boots());
        commands.add(new Leggings());
        commands.add(new SetPitch());
        commands.add(new SetYaw());
        commands.add(new AnimationTotem());
        commands.add(new AnimationTeleportEnder());
        commands.add(new AnimationBreakBoots());
        commands.add(new AnimationBreakChestplate());
        commands.add(new AnimationBreakHand());
        commands.add(new AnimationBreakHelmet());
        commands.add(new AnimationBreakLeggings());
        commands.add(new AnimationBreakOffHand());
        commands.add(new AnimationHurt());
        commands.add(new AnimationSwingMainHand());
        commands.add(new AnimationSwingOffHand());
        commands.add(new CancelPickup());
        commands.add(new ForceDrop());
        commands.add(new CloseInventory());
        commands.add(new GravityEnable());
        commands.add(new GravityDisable());
        commands.add(new OpenWorkbench());
        commands.add(new MinecartBoost());
        commands.add(new Steal());
        commands.add(new FormatEnchantments());
        commands.add(new AddEnchantment());
        commands.add(new SortInventory());
        commands.add(new Oxygen());
        commands.add(new Bossbar());
        commands.add(new Spin());
        commands.add(new OpMessage());
        commands.add(new ConsoleMessage());
        commands.add(new RemoveEnchantment());
        commands.add(new Chat());
        commands.add(new DropSpecificEI());
        commands.add(new OpenChest());
        commands.add(new EICooldown());
        commands.add(new CopyEffects());
        commands.add(new AddItemAttribute());
        commands.add(new SetItemAttribute());
        commands.add(new SetArmorTrim());
        /* No EntityToggleGlideEvent in 1.11 -*/
        if (!SCore.is1v11Less()) {
            commands.add(new ActionbarCommand());
            commands.add(new StunEnable());
            commands.add(new StunDisable());
            commands.add(new ParticleCommand());
            commands.add(new GlacialFreeze());
            commands.add(new OpenEnderchest());
        }
        commands.add(XpBoost.getInstance());
        commands.add(new While());

        commands.add(new Customtest());

        setCommands(commands);
    }

    public static PlayerCommandManager getInstance() {
        if (instance == null) instance = new PlayerCommandManager();
        return instance;
    }
}
