package com.ssomar.score.commands.runnable.player;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.player.commands.*;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.ChatColor;

import java.util.*;

public class PlayerCommandManager implements CommandManager {

    private static PlayerCommandManager instance;

    private List<PlayerCommand> commands;

    public PlayerCommandManager() {
        List<PlayerCommand> commands = new ArrayList<>();
        commands.add(new Around());
        commands.add(new MobAround());
        commands.add(new SendBlankMessage());
        commands.add(new SendMessage());
        commands.add(new SendCenteredMessage());
        /* SUDOOP MUST BE BEFORE SUDO */
        commands.add(new SudoOp());
        commands.add(new Sudo());
        commands.add(new FlyOn());
        commands.add(new FlyOff());
        commands.add(new SetBlock());
        commands.add(new ReplaceBlock());
        commands.add(new CustomDash1());
        commands.add(new ProjectileCustomDash1());
        commands.add(new FrontDash());
        commands.add(new Glowing());
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
        commands.add(new Damage());
        commands.add(new LaunchEntity());
        commands.add(new Launch());
        if (!SCore.is1v12Less()) {
            commands.add(new LocatedLaunch());
            commands.add(new CropsGrowthBoost());
            /* No damageable class before 1.12 */
            commands.add(new ModifyDurability());
        }
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
        commands.add(new Head());
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
        commands.add(new CancelPickup());
        commands.add(new ForceDrop());
        commands.add(new CloseInventory());
        commands.add(new GravityEnable());
        commands.add(new GravityDisable());
        commands.add(new OpenWorkbench());
        commands.add(new MinecartBoost());
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

        this.commands = commands;
    }

    public static PlayerCommandManager getInstance() {
        if (instance == null) instance = new PlayerCommandManager();
        return instance;
    }

    public Optional<String> verifArgs(PlayerCommand pC, List<String> args) {
        return pC.verify(args, false);
    }

    public boolean isValidPlayerCommads(String entry) {
        for (PlayerCommand playerCommands : this.commands) {
            for (String name : playerCommands.getNames()) {
                if (entry.toUpperCase().startsWith(name.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getCommands(SPlugin sPlugin, List<String> commands, List<String> errorList, String id) {

        List<String> result = new ArrayList<>();

        for (String s : commands) {

            String command = StringConverter.coloredString(s);

            if (this.isValidPlayerCommads(s) && !s.contains("+++")) {
                PlayerCommand bc = (PlayerCommand) this.getCommand(command);
                List<String> args = this.getArgs(command);

                Optional<String> error = this.verifArgs(bc, args);
                error.ifPresent(value -> errorList.add(StringConverter.decoloredString(sPlugin.getNameDesign() + " " + value + " for item: " + id)));
            }
            result.add(command);
        }
        return result;
    }

    public Optional<String> verifCommand(String command) {

        command = StringConverter.coloredString(command);

        /*
         * if (command.contains("\\{")) command= command.replaceAll("\\{", ""); if
         * (command.contains("\\}")) command= command.replaceAll("\\}", "");
         */

        if (this.isValidPlayerCommads(command) && !command.contains("//") && !command.contains("+++")) {
            PlayerCommand bc = (PlayerCommand) this.getCommand(command);
            List<String> args = this.getArgs(command);

            Optional<String> error = this.verifArgs(bc, args);
            if (error.isPresent()) {
                return Optional.of("&4&lINVALID COMMAND &c" + " " + error.get());
            }
        }
        return Optional.empty();
    }

    public List<PlayerCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<PlayerCommand> commands) {
        this.commands = commands;
    }

    public Map<String, String> getCommandsDisplay() {
        Map<String, String> result = new HashMap<>();
        for (SCommand c : this.commands) {

            ChatColor extra = c.getExtraColor();
            if (extra == null) extra = ChatColor.DARK_PURPLE;

            ChatColor color = c.getColor();
            if (color == null) color = ChatColor.LIGHT_PURPLE;

            result.put(extra + "[" + color + "&l" + c.getNames().get(0) + extra + "]", c.getTemplate());
        }
        return result;
    }

    @Override
    public SCommand getCommand(String brutCommand) {
        for (PlayerCommand playerCommands : this.commands) {
            for (String name : playerCommands.getNames()) {
                if (brutCommand.toUpperCase().startsWith(name.toUpperCase())) {
                    return playerCommands;
                }
            }
        }
        return null;
    }


    @Override
    public List<String> getArgs(String command) {
        List<String> args = new ArrayList<>();
        boolean first = true;
        boolean second = command.toUpperCase().startsWith("FLY ON")
                || command.toUpperCase().startsWith("FLY OFF")
                || command.toUpperCase().startsWith("REGAIN HEALTH")
                || command.toUpperCase().startsWith("REGAIN FOOD");
        for (String s : command.split(" ")) {
            if (first) {
                first = false;
                continue;
            }
            if (second) {
                second = false;
                continue;
            }
            args.add(s);
        }
        return args;
    }

}
