package com.ssomar.score.commands.runnable.entity;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.CommandManager;
import com.ssomar.score.commands.runnable.SCommand;
import com.ssomar.score.commands.runnable.entity.commands.*;
import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.ChatColor;

import java.util.*;


public class EntityCommandManager implements CommandManager {

    private static EntityCommandManager instance;

    private List<EntityCommand> commands;

    private EntityCommandManager() {
        commands = new ArrayList<>();
        commands.add(new TeleportPosition());
        commands.add(new TeleportEntityToPlayer());
        commands.add(new TeleportPlayerToEntity());
        commands.add(new SendMessage());
        commands.add(new Kill());
        commands.add(new ChangeToMythicMob());
        commands.add(new ChangeTo());
        commands.add(new DropItem());
        commands.add(new DropExecutableItem());
        commands.add(new Heal());
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
        if (!SCore.is1v11Less()) {
            commands.add(new ParticleCommand());
            commands.add(new GlacialFreeze());
        }
    }

    public static EntityCommandManager getInstance() {
        if (instance == null) instance = new EntityCommandManager();
        return instance;
    }

    /*
     *  return "" if no error else return the error
     */
    public Optional<String> verifArgs(EntityCommand eC, List<String> args) {
        return eC.verify(args, false);
    }

    public boolean isValidEntityCommand(String entry) {
        if (entry.contains(" ")) {
            entry = entry.split(" ")[0];
        }
        for (EntityCommand cmd : commands) {
            for (String name : cmd.getNames()) {
                if (entry.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getEntityCommands(SPlugin sPlugin, List<String> commands, List<String> errorList, String id) {

        List<String> result = new ArrayList<>();


        for (String s : commands) {

            String command = StringConverter.coloredString(s);

            /*
             * if (command.contains("\\{")) command= command.replaceAll("\\{", ""); if
             * (command.contains("\\}")) command= command.replaceAll("\\}", "");
             */

            if (EntityCommandManager.getInstance().isValidEntityCommand(s) && !s.contains("//") && !s.contains("+++")) {
                EntityCommand eC = (EntityCommand) this.getCommand(command);
                List<String> args = this.getArgs(command);

                Optional<String> error = this.verifArgs(eC, args);
                error.ifPresent(value -> errorList.add(StringConverter.decoloredString(sPlugin + " " + value + " for item: " + id)));
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

        if (this.isValidEntityCommand(command) && !command.contains("//") && !command.contains("+++")) {
            EntityCommand bc = (EntityCommand) this.getCommand(command);
            List<String> args = this.getArgs(command);

            Optional<String> error = this.verifArgs(bc, args);
            if (error.isPresent()) {
                return Optional.of("&4&lINVALID COMMAND &c" + " " + error.get());
            }
        }
        return Optional.empty();
    }

    public List<EntityCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<EntityCommand> commands) {
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
        for (EntityCommand cmd : commands) {
            for (String name : cmd.getNames()) {
                if (brutCommand.toUpperCase().startsWith(name.toUpperCase())) {
                    return cmd;
                }
            }
        }
        return null;
    }

    @Override
    public List<String> getArgs(String command) {
        List<String> args = new ArrayList<>();
        boolean first = true;
        boolean second = false;
        boolean third = false;
        if (command.toUpperCase().startsWith("TELEPORT POSITION")) second = true;
        else if (command.toUpperCase().startsWith("TELEPORT ENTITY TO PLAYER")
                || command.toUpperCase().startsWith("TELEPORT PLAYER TO ENTITY")) {
            second = true;
            third = true;
        }
        for (String s : command.split(" ")) {
            if (first) {
                first = false;
                continue;
            }
            if (second) {
                second = false;
                continue;
            }
            if (third) {
                third = false;
                continue;
            }
            args.add(s);
        }
        return args;
    }

}
