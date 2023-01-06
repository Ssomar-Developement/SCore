package com.ssomar.score.commands;

import com.ssomar.executableblocks.executableblocks.activators.ActivatorEBFeature;
import com.ssomar.executableitems.executableitems.activators.ActivatorEIFeature;
import com.ssomar.particles.commands.Parameter;
import com.ssomar.particles.commands.Shape;
import com.ssomar.particles.commands.ShapesExamples;
import com.ssomar.particles.commands.ShapesManager;
import com.ssomar.score.SCore;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.cooldowns.CooldownsManager;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.SProjectilesEditor;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringConverter;
import com.ssomar.score.utils.Utils;
import com.ssomar.score.utils.messages.CenteredMessage;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.VariablesEditor;
import com.ssomar.score.variables.manager.VariablesManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
                case "variables":
                    this.runCommand(sender, "variables", args);
                    break;
                case "variables-create":
                    this.runCommand(sender, "variables-create", args);
                    break;
                case "variables-delete":
                    this.runCommand(sender, "variables-delete", args);
                    break;
                case "interact":
                    break;
                case "particles":
                    this.runCommand(sender, "particles", args);
                    break;
                case "particles-info":
                    this.runCommand(sender, "particles-info", args);
                    break;

                case "cooldowns":
                    CooldownsManager cd = CooldownsManager.getInstance();
                    cd.printInfo();
                    break;
                default:
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop | projectiles | projectiles-create | projectiles-delete | variables | variables-create | variables-delete ]"));
                    break;
            }
        } else {
            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument /sCore [ reload | inspect-loop | projectiles | projectiles-create | projectiles-delete | variables | variables-create | variables-delete ]"));
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
            case "variables":
                if(args.length >= 1) {
                    if (args[0].equalsIgnoreCase("info")) {
                        if (args.length >= 2) {
                            Optional<Variable> var = VariablesManager.getInstance().getVariable(args[1]);
                            if (var.isPresent()) {
                                sender.sendMessage(var.get().getValuesStr());
                            } else sender.sendMessage("Variable not found");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("list")){
                        sender.sendMessage(VariablesManager.getInstance().getVariableIdsListStr());
                    }

                    else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("modification")) {
                        String modifType = args[0];
                        String forType = args[1];
                        String varName = args[2];
                        String value = args[3];
                        Optional<Player> optPlayer = Optional.empty();
                        if (args.length >= 5 && forType.equalsIgnoreCase("player")) {
                            optPlayer = Optional.ofNullable(Bukkit.getPlayer(args[4]));
                        }

                        Optional<Variable> var = VariablesManager.getInstance().getVariable(varName);
                        if (var.isPresent()) {
                            if (modifType.equalsIgnoreCase("set")) {
                                Optional<String> err = var.get().setValue(optPlayer, value);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            } else if (modifType.equalsIgnoreCase("modification")) {
                                Optional<String> err = var.get().modifValue(optPlayer, value);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            }
                        } else sender.sendMessage("Variable not found");

                    }
                }
                else {
                    if (player != null) {
                        NewSObjectsManagerEditor.getInstance().startEditing(player, new VariablesEditor());
                    }
                }
                break;
            case "particles":
                ShapesManager shapesManager = ShapesManager.getInstance();
                String shapeName = "";
                String targetStr = "";
                String locationStr = "";
                Entity targetEntity = null;
                if (player != null) targetEntity = player;
                Location targetLocation = null;
                for (String arg : args) {
                    if (arg.contains("shape:")) {
                        try {
                            shapeName = arg.split(":")[1];
                        } catch (Exception e) {
                        }
                    } else if (arg.contains("target:")) {
                        try {
                            targetStr = arg.split(":")[1];
                        } catch (Exception e) {
                        }
                    } else if (arg.contains("location:")) {
                        try {
                            locationStr = arg.split(":")[1];
                        } catch (Exception e) {
                        }

                    }
                }
                if (!targetStr.isEmpty()) {
                    try {
                        targetEntity = Bukkit.getEntity(UUID.fromString(targetStr));
                    } catch (Exception e) {
                        SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid target (" + targetStr + ") for the command /score particles");
                        return;
                    }
                }

                if (targetEntity != null) targetLocation = targetEntity.getLocation();

                if (!locationStr.isEmpty()) {
                    try {
                        String[] locStr = locationStr.split(",");
                        targetLocation = new Location(AllWorldManager.getWorld(locStr[0]).get(), Double.parseDouble(locStr[1]), Double.parseDouble(locStr[2]), Double.parseDouble(locStr[3]));
                        targetEntity = null;
                    } catch (Exception e) {
                        SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid location for the command /score particles");
                        return;
                    }
                }

                Optional<Shape> shapeOpt = shapesManager.getShape(shapeName);
                if (shapeOpt.isPresent()) {
                    Shape shape = shapeOpt.get();
                    shape.getParameters().load(args, targetEntity, targetLocation);
                    shape.run(shape.getParameters());
                    SendMessage.sendMessageNoPlch(sender, "&2[SCore] &aShape executed !");
                } else {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid shape for the command &6/score particles");
                    StringBuilder shapesList = new StringBuilder();
                    for (String s : ShapesManager.getInstance().getShapesNames()) {
                        shapesList.append(s).append("&8,&7 ");
                    }
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cShapes list: &7" + shapesList.toString());
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cExample &6/score particles &eshape:blackhole");
                }

                break;
            case "particles-info":
                ShapesManager shapesManager2 = ShapesManager.getInstance();
                String shapeName2 = "";
                for (String arg : args) {
                    if (arg.contains("shape:")) {
                        try {
                            shapeName2 = arg.split(":")[1];
                        } catch (Exception e) {
                        }
                    }
                }

                Optional<Shape> shapeOpt2 = shapesManager2.getShape(shapeName2);
                if (shapeOpt2.isPresent()) {
                    Shape shape = shapeOpt2.get();
                    SendMessage.sendMessageNoPlch(sender, "&2[SCore] &aFor the shape &e" + shape.getName() + " &ayou should configure the following parameters:");
                    StringBuilder parametersList = new StringBuilder();
                    parametersList.append("&6particle &7type &eclass org.bukkit.Particle\n");
                    for (Parameter parameter : shape.getParameters()) {
                        parametersList.append("&6").append(parameter.getName()).append(" &7type &e").append(parameter.getValue().getClass()).append("\n");
                    }
                    parametersList.append("&7&oFor more info about this shape check the code directly: &aauthor: &eCryptoMorin &alibrary: &eXParticle\n\n");
                    SendMessage.sendMessageNoPlch(sender, parametersList.toString());


                    TextComponent link = new TextComponent(StringConverter.coloredString(CenteredMessage.convertIntoCenteredMessage("&a&l[CLICK HERE]")));
                    link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/CryptoMorin/XSeries/blob/master/src/main/java/com/cryptomorin/xseries/particles/XParticle.java"));
                    sender.spigot().sendMessage(link);
                    sender.sendMessage(" ");

                    Optional<String> example = ShapesExamples.getInstance().getExample(shape.getName());
                    if (!example.isPresent()) {
                        SendMessage.sendMessageNoPlch(sender, CenteredMessage.convertIntoCenteredMessage("&cNO EXAMPLE AVAILABLE FOR THIS SHAPE"));
                    } else {
                        TextComponent copy = new TextComponent(StringConverter.coloredString(CenteredMessage.convertIntoCenteredMessage("&e&l[CLICK HERE TO COPY THE EXAMPLE]")));
                        copy.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, example.get()));
                        sender.spigot().sendMessage(copy);
                    }


                } else {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid shape for the command &6/score particles-info");
                    StringBuilder shapesList = new StringBuilder();
                    for (String s : ShapesManager.getInstance().getShapesNames()) {
                        shapesList.append(s).append("&8,&7 ");
                    }
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cShapes list: &7" + shapesList.toString());
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cExample &6/score particles-info &eshape:blackhole");
                }
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
                    if (args.length >= 1) {
                        if (SProjectilesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cError this id already exist re-enter &6/score projectiles-create ID &7&o(ID is the id you want for your new projectile)"));
                            break;
                        }
                        SProjectile sProjectile = new SProjectile(args[0], "plugins/SCore/projectiles/" + args[0] + ".yml");
                        sProjectile.save();
                        SProjectilesManager.getInstance().addLoadedObject((SProjectile) sProjectile);
                        sProjectile.openEditor(player);
                        break;
                    }
                    player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new projectile type &e/score projectiles-create ID &7&o(ID is the id you want for your new projectile)"));
                }
                break;
            case "projectiles-delete":
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score projectiles-delete {projID} confirm"));
                        return;
                    }
                    Optional<SProjectile> sProjOpt = SProjectilesManager.getInstance().getLoadedObjectWithID(args[0]);
                    if (sProjOpt.isPresent()) {
                        SProjectilesManager.getInstance().deleteObject(args[0]);
                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aProjectile file (&e" + args[0] + ".yml&a) deleted !"));
                        break;
                    }
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cProjectile file not found (&6" + args[0] + ".yml&c) so it can't be deleted !"));
                    break;
                }
                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score projectiles-delete {projID} confirm"));
                break;
            case "variables-create":
                if (player != null) {
                    if (args.length >= 1) {
                        if (VariablesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cError this id already exist re-enter &6/score projectiles-create ID &7&o(ID is the id you want for your new projectile)"));
                            break;
                        }
                        Variable var = new Variable(args[0], "plugins/SCore/variables/" + args[0] + ".yml");
                        var.save();
                        VariablesManager.getInstance().addLoadedObject((Variable) var);
                        var.openEditor(player);
                        break;
                    }
                    player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new projectile type &e/score projectiles-create ID &7&o(ID is the id you want for your new projectile)"));
                }
                break;
            case "variables-delete":
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score variables-delete {varID} confirm"));
                        return;
                    }

                    Optional<Variable> sProjOpt = VariablesManager.getInstance().getLoadedObjectWithID(args[0]);
                    if (sProjOpt.isPresent()) {
                        VariablesManager.getInstance().deleteObject(args[0]);
                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aVariable file (&e" + args[0] + ".yml&a) deleted !"));
                    } else {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cVariable file not found (&6" + args[0] + ".yml&c) so it can't be deleted !"));
                    }
                } else
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score variables-delete {varID} confirm"));
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
                arguments.add("particles");
                arguments.add("particles-info");
                arguments.add("variables");
                arguments.add("variables-create");
                arguments.add("variables-delete");

                Collections.sort(arguments);
                return arguments;
            }
        }
        return null;
    }

}
