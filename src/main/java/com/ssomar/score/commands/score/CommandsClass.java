package com.ssomar.score.commands.score;

import com.ssomar.executableblocks.executableblocks.activators.ActivatorEBFeature;
import com.ssomar.executableitems.executableitems.activators.ActivatorEIFeature;
import com.ssomar.particles.commands.Parameter;
import com.ssomar.particles.commands.Shape;
import com.ssomar.particles.commands.ShapesExamples;
import com.ssomar.particles.commands.ShapesManager;
import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.CommandsExecutor;
import com.ssomar.score.commands.runnable.block.BlockCommandManager;
import com.ssomar.score.commands.runnable.block.BlockRunCommandsBuilder;
import com.ssomar.score.commands.runnable.entity.EntityCommandManager;
import com.ssomar.score.commands.runnable.entity.EntityRunCommandsBuilder;
import com.ssomar.score.commands.runnable.player.PlayerCommandManager;
import com.ssomar.score.commands.runnable.player.PlayerRunCommandsBuilder;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.features.custom.activators.activator.NewSActivator;
import com.ssomar.score.features.custom.cooldowns.CooldownsManager;
import com.ssomar.score.features.custom.loop.LoopFeatures;
import com.ssomar.score.hardness.hardness.Hardness;
import com.ssomar.score.hardness.hardness.HardnessesEditor;
import com.ssomar.score.hardness.hardness.manager.HardnessesManager;
import com.ssomar.score.projectiles.SProjectile;
import com.ssomar.score.projectiles.SProjectilesEditor;
import com.ssomar.score.projectiles.manager.SProjectilesManager;
import com.ssomar.score.sobject.menu.NewSObjectsManagerEditor;
import com.ssomar.score.usedapi.AllWorldManager;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.messages.CenteredMessage;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.VariablesEditor;
import com.ssomar.score.variables.manager.VariablesManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
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
                case "hardnesses":
                    this.runCommand(sender, "hardnesses", args);
                    break;
                case "hardnesses-create":
                    this.runCommand(sender, "hardnesses-create", args);
                    break;
                case "hardnesses-delete":
                    this.runCommand(sender, "hardnesses-delete", args);
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
                case "run-player-command":
                    this.runCommand(sender, "run-player-command", args);
                    break;
                case "run-entity-command":
                    this.runCommand(sender, "run-entity-command", args);
                    break;
                case "run-block-command":
                    this.runCommand(sender, "run-block-command", args);
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

            case "variables":
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("info")) {
                        if (args.length >= 2) {
                            Optional<Variable> var = VariablesManager.getInstance().getVariable(args[1]);
                            if (var.isPresent()) {
                                SendMessage.sendMessageFinal(sender, var.get().getValuesStr(), false);
                            } else sender.sendMessage("Variable not found");
                        }
                    } else if (args[0].equalsIgnoreCase("list")) {
                        sender.sendMessage(VariablesManager.getInstance().getVariableIdsListStr());
                    } else if (args[0].equalsIgnoreCase("set")
                            || args[0].equalsIgnoreCase("modification")
                            || args[0].equalsIgnoreCase("list-add")
                            || args[0].equalsIgnoreCase("list-remove")
                            || args[0].equalsIgnoreCase("clear")) {
                        int argIndex = 0;

                        String modifType = args[argIndex];
                        argIndex++;
                        String forType = args[argIndex];
                        argIndex++;
                        String varName = args[argIndex];
                        argIndex++;
                        String value = "";
                        // NO NEED VALUE FOR REMOVE
                        if (!args[0].equalsIgnoreCase("list-remove") && !args[0].equalsIgnoreCase("clear")) {
                            value = args[3];
                            argIndex++;
                        }
                        Optional<OfflinePlayer> optPlayer = Optional.empty();
                        if (args.length >= argIndex + 1 && forType.equalsIgnoreCase("player")) {
                            try {
                                optPlayer = Optional.ofNullable(Bukkit.getOfflinePlayer(args[argIndex]));
                            } catch (Exception ignored) {
                                ignored.printStackTrace();
                            }
                        }
                        Optional<Integer> indexOpt = Optional.empty();
                        if (args.length >= argIndex + 1 && (modifType.equalsIgnoreCase("list-add") || modifType.equalsIgnoreCase("list-remove"))) {
                            for (int i = argIndex; i < args.length; i++) {
                                if (args[i].contains("index:")) {
                                    try {
                                        indexOpt = Optional.of(Integer.parseInt(args[i].replace("index:", "")));
                                    } catch (NumberFormatException e) {
                                        sender.sendMessage("Invalid index");
                                    }
                                }
                            }
                        }

                        Optional<String> valueOpt = Optional.empty();
                        if (args.length >= argIndex + 1 && (modifType.equalsIgnoreCase("list-remove"))) {
                            for (int i = argIndex; i < args.length; i++) {
                                if (args[i].contains("value:")) {
                                    try {
                                        valueOpt = Optional.of(args[i].replace("value:", ""));
                                    } catch (Exception e) {
                                        sender.sendMessage("Invalid value");
                                    }
                                }
                            }
                        }

                        Optional<Variable> var = VariablesManager.getInstance().getVariable(varName);
                        if (var.isPresent()) {
                            if (modifType.equalsIgnoreCase("set")) {
                                Optional<String> err = var.get().setValue(optPlayer, value);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            } else if (modifType.equalsIgnoreCase("modification")) {
                                Optional<String> err = var.get().modifValue(optPlayer, value);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            } else if (modifType.equalsIgnoreCase("list-add")) {
                                Optional<String> err = var.get().addValue(optPlayer, value, indexOpt);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            } else if (modifType.equalsIgnoreCase("list-remove")) {
                                Optional<String> err = var.get().removeValue(optPlayer, indexOpt, valueOpt);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            } else if (modifType.equalsIgnoreCase("clear")) {
                                Optional<String> err = var.get().clearValue(optPlayer);
                                if (err.isPresent()) sender.sendMessage(err.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET));
                            }

                            VariablesManager.getInstance().updateLoadedMySQL(var.get().getId(), VariablesManager.MODE.EXPORT);
                        } else sender.sendMessage("Variable not found");
                    }
                } else {
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
            case "projectiles":
                if (player != null) {
                    NewSObjectsManagerEditor.getInstance().startEditing(player, new SProjectilesEditor());
                }
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
            case "hardnesses":
                if (player != null) {
                    NewSObjectsManagerEditor.getInstance().startEditing(player, new HardnessesEditor());
                }
                break;
            case "hardnesses-create":
                if (player != null) {
                    if (args.length >= 1) {
                        if (HardnessesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cError this id already exist re-enter &6/score hardnesses-create ID &7&o(ID is the id you want for your new hardness)"));
                            break;
                        }
                        Hardness hard = new Hardness(args[0], "plugins/SCore/hardnesses/" + args[0] + ".yml");
                        hard.save();
                        HardnessesManager.getInstance().addLoadedObject((Hardness) hard);
                        hard.openEditor(player);
                        break;
                    }
                    player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new hardnesse type &e/score hardnesses-create ID &7&o(ID is the id you want for your new hardnesse)"));
                }
                break;
            case "hardnesses-delete":
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score hardnesses-delete {hardID} confirm"));
                        return;
                    }
                    Optional<Hardness> sProjOpt = HardnessesManager.getInstance().getLoadedObjectWithID(args[0]);
                    if (sProjOpt.isPresent()) {
                        HardnessesManager.getInstance().deleteObject(args[0]);
                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aHardness file (&e" + args[0] + ".yml&a) deleted !"));
                        break;
                    }
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cHardness file not found (&6" + args[0] + ".yml&c) so it can't be deleted !"));
                    break;
                }
                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score hardnesses-delete {hardID} confirm"));
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
                        VariablesManager.getInstance().deleteLoadedMYSQL(args[0]);
                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aVariable file (&e" + args[0] + ".yml&a) deleted !"));
                    } else {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cVariable file not found (&6" + args[0] + ".yml&c) so it can't be deleted !"));
                    }
                } else
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm the delete type &6/score variables-delete {varID} confirm"));
                break;

            case "run-player-command":
                Optional<Player> playerOpt = Optional.empty();
                String cmd = "";
                for (String arg : args) {
                    if (arg.startsWith("player:")) {
                        String playerName = arg.replace("player:", "");
                        try {
                            UUID playerUUID = UUID.fromString(playerName);
                            playerOpt = Optional.ofNullable(Bukkit.getPlayer(playerUUID));
                        } catch (Exception e) {
                            playerOpt = Optional.ofNullable(Bukkit.getPlayer(playerName));
                        }
                    } else cmd += arg + " ";
                }
                if (!playerOpt.isPresent()) {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cError: &7&oYou must specify a player with &6player:PLAYER_NAME&7&o or &6player:PLAYER_UUID");
                    return;
                }

                cmd = cmd.trim();

                ActionInfo info = new ActionInfo("run-player-command", new StringPlaceholder());
                UUID uuid = playerOpt.get().getUniqueId();
                info.setLauncherUUID(uuid);
                info.setReceiverUUID(uuid);
                StringPlaceholder sp = new StringPlaceholder();
                sp.setPlayerPlcHldr(uuid);
                info.setSp(sp);

                PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(Arrays.asList(cmd), info);
                CommandsExecutor.runCommands(builder);

                break;
            case "run-entity-command":
                Optional<Entity> entityOptional = Optional.empty();
                String entityCmd = "";
                for (String arg : args) {
                    if (arg.startsWith("entity:")) {
                        String entityName = arg.replace("entity:", "");
                        try {
                            UUID playerUUID = UUID.fromString(entityName);
                            entityOptional = Optional.ofNullable(Bukkit.getEntity(playerUUID));
                        } catch (Exception e) {}
                    } else entityCmd += arg + " ";
                }
                if (!entityOptional.isPresent()) {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cError: &7&oYou must specify an entity with &6entity:ENTITY_UUID");
                    return;
                }

                entityCmd = entityCmd.trim();

                ActionInfo infoEntity = new ActionInfo("run-entity-command", new StringPlaceholder());
                infoEntity.setEntityUUID(entityOptional.get().getUniqueId());
                infoEntity.setReceiverUUID(entityOptional.get().getUniqueId());

                EntityRunCommandsBuilder entityRunCommandsBuilder = new EntityRunCommandsBuilder(Arrays.asList(entityCmd), infoEntity);
                CommandsExecutor.runCommands(entityRunCommandsBuilder);

                break;
            case "run-block-command":
                Optional<Player> playerOpt2 = Optional.empty();
                Optional<Block> blockOpt = Optional.empty();
                String blockCmd = "";
                for (String arg : args) {
                    if (arg.startsWith("player:")) {
                        String playerName = arg.replace("player:", "");
                        try {
                            UUID playerUUID = UUID.fromString(playerName);
                            playerOpt2 = Optional.ofNullable(Bukkit.getPlayer(playerUUID));
                        } catch (Exception e) {
                            playerOpt2 = Optional.ofNullable(Bukkit.getPlayer(playerName));
                        }
                    }
                    else if (arg.startsWith("block:")) {
                        String blockLoc = arg.replace("block:", "");
                        try {
                            String loc[] = blockLoc.split(",");
                            Optional<World> world = AllWorldManager.getWorld(loc[0]);
                            double x = Double.parseDouble(loc[1]);
                            double y = Double.parseDouble(loc[2]);
                            double z = Double.parseDouble(loc[3]);

                            blockOpt = Optional.ofNullable(world.get().getBlockAt(new Location(world.get(), x, y, z)));

                        } catch (Exception e) {}
                    } else blockCmd += arg + " ";
                }
                if (!blockOpt.isPresent()) {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cError: &7&oYou must specify a block with &6block:WORLD,X,Y,Z");
                    return;
                }

                blockCmd = blockCmd.trim();

                ActionInfo infoBlock = new ActionInfo("run-block-command", new StringPlaceholder());
                Location blockLocation = blockOpt.get().getLocation();
                infoBlock.setBlockLocationX(blockLocation.getBlockX());
                infoBlock.setBlockLocationY(blockLocation.getBlockY());
                infoBlock.setBlockLocationZ(blockLocation.getBlockZ());
                infoBlock.setBlockLocationWorld(blockLocation.getWorld().getUID());
                infoBlock.setOldBlockMaterialName(blockOpt.get().getType().name());
                playerOpt2.ifPresent(value -> infoBlock.setLauncherUUID(value.getUniqueId()));

                BlockRunCommandsBuilder blockRunCommandsBuilder = new BlockRunCommandsBuilder(Arrays.asList(blockCmd), infoBlock);
                CommandsExecutor.runCommands(blockRunCommandsBuilder);

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
                arguments.add("hardnesses");
                arguments.add("hardnesses-create");
                arguments.add("hardnesses-delete");
                arguments.add("particles");
                arguments.add("particles-info");
                arguments.add("variables");
                arguments.add("variables-create");
                arguments.add("variables-delete");
                arguments.add("run-player-command");
                arguments.add("run-entity-command");
                arguments.add("run-block-command");

                List<String> argumentsPerm = new ArrayList<String>();
                for (String str : arguments) {
                    if (sender.hasPermission("score.cmd." + command) || sender.hasPermission("score.cmds") || sender.hasPermission("score.*")) {
                        argumentsPerm.add(str);
                    }
                }

                Collections.sort(argumentsPerm);
                return argumentsPerm;
            } else if (args.length >= 2) {

                switch (args[0]) {
                    case "variables":
                        if (args.length == 2) {
                            arguments.add("info");
                            arguments.add("list");
                            arguments.add("set");
                            arguments.add("modification");
                            arguments.add("list-add");
                            arguments.add("list-remove");
                            arguments.add("clear");
                        } else if (args.length == 3 && args[1].equalsIgnoreCase("info")) {
                            arguments.addAll(VariablesManager.getInstance().getVariableIdsList());
                        } else if (args.length == 3 && (args[1].equalsIgnoreCase("set")
                                || args[1].equalsIgnoreCase("modification")
                                || args[1].equalsIgnoreCase("clear")
                                || args[1].equalsIgnoreCase("list-add")
                                || args[1].equalsIgnoreCase("list-remove"))) {
                            arguments.add("global");
                            arguments.add("player");
                        } else if (args.length == 4 && (args[1].equalsIgnoreCase("set")
                                || args[1].equalsIgnoreCase("modification")
                                || args[1].equalsIgnoreCase("clear")
                                || args[1].equalsIgnoreCase("list-add")
                                || args[1].equalsIgnoreCase("list-remove"))) {
                            arguments.addAll(VariablesManager.getInstance().getVariableIdsList());
                        }

                    case "run-player-command":
                        if (args.length == 2) {
                            arguments.add("player:");
                        } else if (args.length == 3 && args[1].startsWith("player:")) {
                            arguments.addAll(PlayerCommandManager.getInstance().getCommandsDisplay().values());
                            for(int i = 0; i < arguments.size(); i++) {
                                String arg = arguments.get(i);
                                if(arg.length() > 50) {
                                    arguments.set(i, arg.substring(0, 45)+"...");
                                }
                            }
                        }
                        break;
                    case "run-entity-command":
                        if (args.length == 2) {
                            arguments.add("entity:");
                        } else if (args.length == 3 && args[1].startsWith("entity:")) {
                            arguments.addAll(EntityCommandManager.getInstance().getCommandsDisplay().values());
                            for(int i = 0; i < arguments.size(); i++) {
                                String arg = arguments.get(i);
                                if(arg.length() > 50) {
                                    arguments.set(i, arg.substring(0, 45)+"...");
                                }
                            }
                        }
                        break;
                    case "run-block-command":
                        if (args.length == 2) {
                            arguments.add("block:");
                        } else if (args.length == 3 && args[1].startsWith("block:")) {
                            arguments.addAll(BlockCommandManager.getInstance().getCommandsDisplay().values());
                            for(int i = 0; i < arguments.size(); i++) {
                                String arg = arguments.get(i);
                                if(arg.length() > 50) {
                                    arguments.set(i, arg.substring(0, 45)+"...");
                                }
                            }
                        }
                        break;
                }
                Collections.sort(arguments);
                return arguments;
            }
        }
        return null;
    }

}
