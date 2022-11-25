package com.ssomar.score.commands;

import com.ssomar.executableblocks.executableblocks.activators.ActivatorEBFeature;
import com.ssomar.executableitems.executableitems.activators.ActivatorEIFeature;
import com.ssomar.particles.commands.Shape;
import com.ssomar.particles.commands.ShapesManager;
import com.ssomar.score.SCore;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.SProjectilesEditor;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandsClass implements CommandExecutor, TabExecutor {

    private final SendMessage sm = new SendMessage();

    private final SCore main;

    public CommandsClass(SCore main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {

            switch (args[0]) {
                case "reload":
                    this.runCommand(sender, "reload", args);
                    break;
                case "inspect-loop":
                    this.runCommand(sender, "inspect-loop", args);
                    break;
                case "projectiles":
                    this.runCommand(sender, "projectiles", args);
                    break;
                case "projectiles-create":
                    this.runCommand(sender, "projectiles-create", args);
                    break;
                case "projectiles-delete":
                    this.runCommand(sender, "projectiles-delete", args);
                    break;
                case "interact":
                    break;
                case "particles":
                    this.runCommand(sender, "particles", args);
                    break;
                default:
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop | projectiles | projectiles-create | projectiles-delete ]"));
                    break;
            }
        } else {
            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop | projectiles ]"));
        }
        return true;
    }

    public void runCommand(CommandSender sender, String command, String[] fullArgs) {

        String[] args;
        if (fullArgs.length > 1) {
            args = new String[fullArgs.length - 1];
            for (int i = 0; i < fullArgs.length; i++) {
                if (i == 0) continue;
                else args[i - 1] = fullArgs[i];
            }
        } else args = new String[0];
        Player player = null;
        if ((sender instanceof Player)) {
            player = (Player) sender;
            if (!(player.hasPermission("score.cmd." + command) || player.hasPermission("score.cmds") || player.hasPermission("score.*"))) {
                player.sendMessage(StringConverter.coloredString("&4[SCore] &cYou don't have the permission to execute this command: " + "&6score.cmd." + command));
                return;
            }
        }

        switch (command) {

            case "projectiles":
                if (player != null) {
                    NewSObjectsManagerEditor.getInstance().startEditing(player, new SProjectilesEditor());
                }
                break;
            case "particles":
                    ShapesManager shapesManager = ShapesManager.getInstance();
                    String shapeName = "";
                    String targetStr = "";
                    String locationStr = "";
                    Entity targetEntity = null;
                    if(player != null) targetEntity = player;
                    Location targetLocation = null;
                    for(String arg : args) {
                        if(arg.contains("shape:")) {
                            shapeName = arg.split(":")[1];
                        }
                        else if(arg.contains("target:")) {
                            targetStr = arg.split(":")[1];
                        }
                        else if(arg.contains("location:")) {
                            locationStr = arg.split(":")[1];
                        }
                    }
                    if(!targetStr.isEmpty()){
                        try {
                            targetEntity = Bukkit.getEntity(UUID.fromString(targetStr));
                        }catch (Exception e){
                            SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid target ("+targetStr+") for the command /score particles");
                            return;
                        }
                    }

                    if(targetEntity != null) targetLocation = targetEntity.getLocation();

                    if(!locationStr.isEmpty()){
                        try {
                            String[] locStr = locationStr.split(",");
                            targetLocation = new Location(AllWorldManager.getWorld(locStr[3]).get(), Double.parseDouble(locStr[0]), Double.parseDouble(locStr[1]), Double.parseDouble(locStr[2]));
                        }catch (Exception e){
                            SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid location for the command /score particles");
                            return;
                        }
                    }

                    Optional<Shape> shapeOpt = shapesManager.getShape(shapeName);
                    if(shapeOpt.isPresent()) {
                        Shape shape = shapeOpt.get();
                        shape.getParameters().load(args, targetEntity, targetLocation);
                        shape.run(shape.getParameters());
                        SendMessage.sendMessageNoPlch(sender, "&2[SCore] &aShape executed !");
                    }
                    else SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid shape for the command /score particles");

                break;
            case "reload":
                main.onReload();
                sm.sendMessage(sender, ChatColor.GREEN + "SCore has been reload");
                Utils.sendConsoleMsg("SCore reloaded !");
                break;
            case "inspect-loop":
                Map<NewSActivator, Integer> loops = LoopManager.getInstance().getLoopActivators();
                sm.sendMessage(sender, " ");
                sm.sendMessage(sender, "&8==== &7SCore contains &e" + loops.size() + " &7loop(s) &8====");
                sm.sendMessage(sender, "&7&o(The loop of ExecutableItems requires more performance when there are many players)");
                sm.sendMessage(sender, " ");
                for (NewSActivator sAct : loops.keySet()) {
                    LoopFeatures loop = null;
                    for (Object feature : sAct.getFeatures()) {
                        if (feature instanceof LoopFeatures) {
                            loop = (LoopFeatures) feature;
                        }
                    }
                    if (loop == null) continue;

                    int delay = loop.getDelay().getValue().get();
                    if (!loop.getDelayInTick().getValue()) delay = delay * 20;
                    if (SCore.hasExecutableItems && sAct instanceof ActivatorEIFeature) {
                        sm.sendMessage(sender, "&bEI LOOP > &7item: &e" + sAct.getParentObjectId() + " &7delay: &e" + delay + " &7(in ticks)");
                    } else if (SCore.hasExecutableBlocks && sAct instanceof ActivatorEBFeature) {
                        sm.sendMessage(sender, "&aEB LOOP > &7block: &e" + sAct.getParentObjectId() + " &7delay: &e" + delay + " &7(in ticks)");
                    }
                }
                sm.sendMessage(sender, " ");

                break;
            case "projectiles-create":
                if (player != null) {
                    if (args.length == 1) {
                        if (SProjectilesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cError this id already exist re-enter &6/score projectiles-create ID &7&o(ID is the id you want for your new projectile)"));
                        } else {
                            SProjectile sProjectile = new SProjectile(args[0], "plugins/SCore/projectiles/" + args[0] + ".yml");
                            sProjectile.save();
                            SProjectilesManager.getInstance().addLoadedObject(sProjectile);
                            sProjectile.openEditor(player);
                        }
                    } else {
                        player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new projectile type &e/score projectiles-create ID &7&o(ID is the id you want for your new projectile)"));
                    }
                }
                break;
            case "projectiles-delete":
                if (args.length == 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score projectiles-delete {projID} confirm"));
                        return;
                    }

                    Optional<SProjectile> sProjOpt = SProjectilesManager.getInstance().getLoadedObjectWithID(args[0]);
                    if(sProjOpt.isPresent()) {
                        SProjectile sProj = sProjOpt.get();
                        sProj.delete();
                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aProjectile file (&e" + args[0] + ".yml&a) deleted !"));
                    } else {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cProjectile file not found (&6" + args[0] + ".yml&c) so it can't be deleted !"));
                    }
                } else
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score projectiles-delete {projID} confirm"));
                break;
            default:
                break;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("score")) {
            ArrayList<String> arguments = new ArrayList<>();
            if (args.length == 1) {

                arguments.add("reload");
                arguments.add("inspect-loop");
                arguments.add("projectiles");
                arguments.add("projectiles-create");
                arguments.add("projectiles-delete");

                Collections.sort(arguments);
                return arguments;
            }
        }
        return null;
    }

}
