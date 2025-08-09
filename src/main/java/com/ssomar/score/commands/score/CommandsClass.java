package com.ssomar.score.commands.score;

import com.ssomar.executableblocks.executableblocks.activators.ActivatorEBFeature;
import com.ssomar.executableevents.executableevents.activators.ActivatorEEFeature;
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
import com.ssomar.score.commands.score.clear.ClearCommand;
import com.ssomar.score.commands.score.clear.ClearType;
import com.ssomar.score.configs.messages.Message;
import com.ssomar.score.configs.messages.MessageMain;
import com.ssomar.score.events.loop.LoopManager;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.activators.activator.SActivator;
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
import com.ssomar.score.utils.emums.VariableType;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.messages.CenteredMessage;
import com.ssomar.score.utils.messages.SendMessage;
import com.ssomar.score.utils.placeholders.StringPlaceholder;
import com.ssomar.score.utils.strings.StringConverter;
import com.ssomar.score.utils.strings.StringJoiner;
import com.ssomar.score.variables.Variable;
import com.ssomar.score.variables.VariableForEnum;
import com.ssomar.score.variables.VariablesEditor;
import com.ssomar.score.variables.manager.VariablesManager;
import lombok.RequiredArgsConstructor;
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
import org.jetbrains.annotations.NotNull;

// Paper Dialog API imports (experimental)
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles the execution of /score.
 */
@RequiredArgsConstructor
public final class CommandsClass implements CommandExecutor, TabExecutor {

    /**
     * The {@link SendMessage} instance.
     */
    @NotNull
    private final SendMessage sm = new SendMessage();

    /**
     * The {@link SCore} plugin instance.
     */
    @NotNull
    private final SCore main;

    private final String[] commands = new String[]{"clear", "cooldowns", "dialog", "dialog-save", "hardnesses", "hardnesses-create", "hardnesses-delete", "inspect-loop", "particles", "particles-info", "projectiles", "projectiles-create", "projectiles-delete", "reload", "run-entity-command", "run-block-command", "run-player-command", "variables", "variables-create", "variables-define", "variables-delete", "webhook", "no-translated"};

    /**
     * Called when a {@link CommandSender} types /score.
     *
     * @param sender  the {@link CommandSender} who typed the command.
     * @param command the {@link Command} which was executed.
     * @param label   the label that was entered.
     * @param args    the passed command arguments.
     * @return {@code true} if the command was successfully executed, {@code false} otherwise.
     */
    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (args.length > 0) {
            String commandName = args[0].toLowerCase();
            if (Arrays.asList(commands).contains(commandName)) {
                this.runCommand(sender, commandName, args);
            } else {
                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument! Usage: /score &8[ &7" + StringJoiner.join(commands, " &c| &7") + " &8]"));
            }
        } else
            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument! Usage: /score &8[ &7" + StringJoiner.join(commands, " &c| &7") + " &8]"));

        return true;
    }

    /**
     * Runs the command for the given {@link CommandSender}.
     *
     * @param sender   the {@link CommandSender} running the command.
     * @param command  the command that was entered.
     * @param fullArgs the passed command arguments.
     */
    public void runCommand(final CommandSender sender, final String command, final String[] fullArgs) {
        final String[] args;

        if (fullArgs.length > 1) {
            args = new String[fullArgs.length - 1];

            for (int i = 0; i < fullArgs.length; i++)
                if (i != 0)
                    args[i - 1] = fullArgs[i];
        } else
            args = new String[0];

        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;

            if (!(player.hasPermission("score.cmd." + command) || player.hasPermission("score.cmds") || player.hasPermission("score.*"))) {
                player.sendMessage(StringConverter.coloredString("&4[SCore] &cYou don't have the permission to execute this command: &6score.cmd." + command + "&c."));

                return;
            }
        }

        switch (command.toLowerCase()) {
            case "no-translated":
                SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cNo translated text sent in your console. &7(You can send it to Ssomar in the suggestions channel on the discord).");
                Utils.sendConsoleFlatMsg(SCore.plugin, FeatureSettingsSCore.getAllNonTranslated());
                break;
            case "clear":
                ClearCommand.clearCmd(SCore.plugin, sender, args);
                break;
            case "cooldowns":
                if (args.length >= 1) {
                    switch (args[0]) {
                        case "clear":
                            if (args.length < 2) {
                                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid argument! Usage: /score cooldowns clear &7cooldownId"));
                                return;
                            }
                            String cooldownId = args[1];
                            UUID uuid = null;
                            if (args.length >= 3) {
                                try {
                                    uuid = UUID.fromString(args[2]);
                                } catch (final Exception e) {
                                    try {
                                        uuid = Bukkit.getPlayer(args[2]).getUniqueId();
                                    } catch (final Exception e2) {
                                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid UUID or player name : &6" + args[2]));
                                    }
                                }
                            }
                            CooldownsManager.getInstance().clearCooldown(cooldownId, uuid);
                            sender.sendMessage(StringConverter.coloredString("&2[SCore] &aCooldown &e" + cooldownId + " &acleared!"));
                            break;
                    }
                } else {
                    CooldownsManager.getInstance().printInfo();
                    sender.sendMessage(StringConverter.coloredString("&2[SCore] &aCooldowns printed in console!"));
                }
                break;
            case "dialog":
                if (args.length >= 1 && args[0].equalsIgnoreCase("example")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cThis command can only be used by players!"));
                        return;
                    }
                    
                    Player player = (Player) sender;
                    
                    // Check if running on Paper and version supports Dialog API
                    if (!SCore.isPaper() || !SCore.is1v21Plus()) {
                        player.sendMessage(StringConverter.coloredString("&4[SCore] &cDialog API requires Paper 1.21+ server!"));
                        return;
                    }
                    
                    try {
                        openDialogExample(player);
                    } catch (Exception e) {
                        player.sendMessage(StringConverter.coloredString("&4[SCore] &cDialog API is not available or not fully supported yet. Error: " + e.getMessage()));
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cUsage: /score dialog example"));
                }
                break;
            case "dialog-save":
                // This command is called by the native dialog system to save responses
                if (args.length >= 5) {
                    try {
                        saveDialogResponse(sender, args);
                    } catch (Exception e) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cError saving dialog response: " + e.getMessage()));
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cThis command is used internally by the dialog system."));
                }
                break;
            case "variables":
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("info")) {
                        if (args.length >= 2) {
                            final Optional<Variable> var = VariablesManager.getInstance().getVariable(args[1]);

                            if (var.isPresent())
                                SendMessage.sendMessageFinal(sender, var.get().getValuesStr(), false);
                            else
                                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cVariable (&6" + args[1] + ") &cnot found!"));
                        }
                    } else if (args[0].equalsIgnoreCase("list"))
                        sender.sendMessage(VariablesManager.getInstance().getVariableIdsListStr());
                    else if (args[0].equalsIgnoreCase("set")
                            || args[0].equalsIgnoreCase("modification")
                            || args[0].equalsIgnoreCase("list-add")
                            || args[0].equalsIgnoreCase("list-remove")
                            || args[0].equalsIgnoreCase("clear")) {
                        int argIndex = 0;

                        final String modifType = args[argIndex];
                        argIndex++;

                        final String forType = args[argIndex];
                        argIndex++;

                        final String varName = args[argIndex];
                        argIndex++;

                        String value = "";

                        // No value is needed for remove.
                        if (!args[0].equalsIgnoreCase("list-remove") && !args[0].equalsIgnoreCase("clear")) {
                            if (args.length > argIndex) value = args[argIndex];
                            else {
                                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid value!"));
                                return;
                            }

                            argIndex++;
                        }

                        Optional<OfflinePlayer> optPlayer = Optional.empty();
                        String playerStr = "";

                        if (args.length >= argIndex + 1 && forType.equalsIgnoreCase("player"))
                            try {
                                playerStr = args[argIndex];
                                optPlayer = Optional.of(Bukkit.getOfflinePlayer(args[argIndex]));
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }

                        Optional<Integer> indexOpt = Optional.empty();

                        if (args.length >= argIndex + 1 && (modifType.equalsIgnoreCase("list-add") || modifType.equalsIgnoreCase("list-remove")))
                            for (int i = argIndex; i < args.length; i++)
                                if (args[i].contains("index:"))
                                    try {
                                        indexOpt = Optional.of(Integer.parseInt(args[i].replace("index:", "")));
                                    } catch (final NumberFormatException e) {
                                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid index!"));
                                    }

                        Optional<String> valueOpt = Optional.empty();

                        if (args.length >= argIndex + 1 && (modifType.equalsIgnoreCase("list-remove")))
                            for (int i = argIndex; i < args.length; i++)
                                if (args[i].contains("value:"))
                                    try {
                                        valueOpt = Optional.of(args[i].replace("value:", ""));
                                    } catch (final Exception e) {
                                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid value!"));
                                    }

                        final Optional<Variable> variableOpt = VariablesManager.getInstance().getVariable(varName);

                        if (variableOpt.isPresent()) {
                            String variableValueSetMsg = MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_VALUE_SET)
                                    .replace("%player%", (optPlayer.map(OfflinePlayer::getName).orElse("null")))
                                    .replace("%variable_name%", variableOpt.get().getId())
                                    .replace("%variable_value%", value);
                            if (modifType.equalsIgnoreCase("set")) {
                                final Optional<String> errorOpt = variableOpt.get().setValue(optPlayer, value);

                                if (errorOpt.isPresent())
                                    sender.sendMessage(errorOpt.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, variableValueSetMsg);

                            } else if (modifType.equalsIgnoreCase("modification")) {
                                final Optional<String> errorOpt = variableOpt.get().modifValue(optPlayer, value);

                                if (errorOpt.isPresent())
                                    sender.sendMessage(errorOpt.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, variableValueSetMsg);
                            } else if (modifType.equalsIgnoreCase("list-add")) {
                                final Optional<String> errorOpt = variableOpt.get().addValue(optPlayer, value, indexOpt);

                                if (errorOpt.isPresent())
                                    sender.sendMessage(errorOpt.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, variableValueSetMsg);
                            } else if (modifType.equalsIgnoreCase("list-remove")) {
                                final Optional<String> errorOpt = variableOpt.get().removeValue(optPlayer, indexOpt, valueOpt);

                                if (errorOpt.isPresent())
                                    sender.sendMessage(errorOpt.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, variableValueSetMsg);
                            } else if (modifType.equalsIgnoreCase("clear")) {
                                final Optional<String> errorOpt;

                                if (playerStr.equalsIgnoreCase("all"))
                                    errorOpt = variableOpt.get().clearAllValues();
                                else errorOpt = variableOpt.get().clearValue(optPlayer);

                                if (errorOpt.isPresent())
                                    sender.sendMessage(errorOpt.get());
                                else
                                    SendMessage.sendMessageNoPlch(sender, variableValueSetMsg);
                            }

                            VariablesManager.getInstance().updateLoadedMySQL(variableOpt.get().getId(), VariablesManager.MODE.EXPORT);
                        } else
                            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cVariable (&6" + args[1] + ") &cnot found!"));
                    } else if (args[0].equalsIgnoreCase("set-default")) {
                        int argIndex = 0;

                        final String modifType = args[argIndex];
                        argIndex++;

                        final String varName = args[argIndex];
                        argIndex++;

                        String value = args[argIndex];

                        final Optional<Variable> variableOpt = VariablesManager.getInstance().getVariable(varName);

                        if (variableOpt.isPresent()) {
                            if (modifType.equalsIgnoreCase("set-default")) {
                                variableOpt.get().getDefaultValue().setValue(value);

                                SendMessage.sendMessageNoPlch(sender, MessageMain.getInstance().getMessage(SCore.plugin, Message.VARIABLE_DEFAULT_VALUE_SET));
                            }

                            VariablesManager.getInstance().updateLoadedMySQL(variableOpt.get().getId(), VariablesManager.MODE.EXPORT);
                        } else
                            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cVariable (&6" + args[1] + ") &cnot found!"));
                    }
                } else if (player != null)
                    NewSObjectsManagerEditor.getInstance().startEditing(player, new VariablesEditor());

                break;
            case "particles":
                String shapeName = "";
                String targetStr = "";
                String locationStr = "";
                Entity targetEntity = null;

                if (player != null)
                    targetEntity = player;

                Location targetLocation = null;

                for (final String arg : args) {
                    if (arg.contains("shape:"))
                        try {
                            shapeName = arg.split(":")[1];
                        } catch (final Exception ignored) {
                        }
                    else if (arg.contains("target:"))
                        try {
                            targetStr = arg.split(":")[1];
                        } catch (final Exception ignored) {
                        }
                    else if (arg.contains("location:"))
                        try {
                            locationStr = arg.split(":")[1];
                        } catch (final Exception ignored) {
                        }
                }
                if (!targetStr.isEmpty())
                    try {
                        targetEntity = Bukkit.getEntity(UUID.fromString(targetStr));
                    } catch (final Exception e) {
                        SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid target (" + targetStr + ") for the command &6/score particles&c.");
                        return;
                    }

                if (targetEntity != null)
                    targetLocation = targetEntity.getLocation();

                if (!locationStr.isEmpty())
                    try {
                        final String[] locStr = locationStr.split(",");
                        targetLocation = new Location(AllWorldManager.getWorld(locStr[0]).get(), Double.parseDouble(locStr[1]), Double.parseDouble(locStr[2]), Double.parseDouble(locStr[3]));
                        targetEntity = null;
                    } catch (final Exception e) {
                        SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid location for the command /score particles.");

                        return;
                    }

                final Optional<Shape> shapeOpt = ShapesManager.getInstance().getShape(shapeName);

                if (shapeOpt.isPresent()) {
                    final Shape shape = shapeOpt.get();

                    shape.getParameters().load(args, targetEntity, targetLocation);
                    shape.run(shape.getParameters(), targetEntity);

                    SendMessage.sendMessageNoPlch(sender,MessageMain.getInstance().getMessage(SCore.plugin, Message.SHAPE_EXECUTED));
                } else {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid shape for the command &6/score particles&c.");

                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cShapes: &7" + StringJoiner.join(ShapesManager.getInstance().getShapesNames(), "&8, &7") + "&c.");
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cExample: &6/score particles &eshape:blackhole&c.");
                }

                break;
            case "particles-info":
                String shapeName2 = "";

                for (final String arg : args)
                    if (arg.contains("shape:"))
                        try {
                            shapeName2 = arg.split(":")[1];
                        } catch (final Exception ignored) {
                        }

                final Optional<Shape> shapeOpt2 = ShapesManager.getInstance().getShape(shapeName2);

                if (shapeOpt2.isPresent()) {
                    final Shape shape = shapeOpt2.get();
                    SendMessage.sendMessageNoPlch(sender, "&2[SCore] &aYou should configure the following parameters for shape &e" + shape.getName() + "&a:");

                    final StringBuilder parametersList = new StringBuilder();
                    parametersList.append("&6particle &7type &eclass org.bukkit.Particle\n");

                    for (final Parameter<?> parameter : shape.getParameters())
                        parametersList
                                .append("&6")
                                .append(parameter.getName())
                                .append(" &7type &e")
                                .append(parameter.getValue().getClass())
                                .append("\n");

                    parametersList.append("&7&oFor more info about this shape check &ethe wiki\n\n");
                    SendMessage.sendMessageNoPlch(sender, parametersList.toString());

                    final TextComponent link = new TextComponent(StringConverter.coloredString(CenteredMessage.convertIntoCenteredMessage("&a&l[CLICK HERE]")));
                    link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.ssomar.com/tools-for-all-plugins-score/score-particles"));

                    sender.spigot().sendMessage(link);
                    sender.sendMessage(" ");

                    final Optional<String> exampleOpt = ShapesExamples.getInstance().getExample(shape.getName());

                    if (!exampleOpt.isPresent())
                        SendMessage.sendMessageNoPlch(sender, CenteredMessage.convertIntoCenteredMessage("&cNO EXAMPLE AVAILABLE FOR THIS SHAPE"));
                    else {
                        final TextComponent copy = new TextComponent(StringConverter.coloredString(CenteredMessage.convertIntoCenteredMessage("&e&l[CLICK HERE TO COPY THE EXAMPLE]")));
                        copy.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, exampleOpt.get()));

                        sender.spigot().sendMessage(copy);
                    }


                } else {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cInvalid shape for the command &6/score particles-info&c.");

                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cShapes: &7" + StringJoiner.join(ShapesManager.getInstance().getShapesNames(), "&8, &7") + "&c.");
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cExample: &6/score particles-info &eshape:blackhole&c.");
                }
                break;
            case "reload":
                this.main.onReload();

                this.sm.sendMessage(sender, ChatColor.GREEN + "SCore has been reloaded.");
                Utils.sendConsoleMsg("SCore reloaded!");

                break;
            case "inspect-loop":
                final Map<SActivator, Integer> loops = LoopManager.getInstance().getLoopActivators();

                this.sm.sendMessage(sender, " ");
                this.sm.sendMessage(sender, "&8==== &7SCore contains &e" + loops.size() + " &7loop(s) &8====");
                this.sm.sendMessage(sender, "&7&o(The loop of ExecutableItems requires more performance when there are many players)");
                this.sm.sendMessage(sender, " ");

                for (final SActivator sAct : loops.keySet()) {
                    LoopFeatures loop = null;

                    for (final Object feature : sAct.getFeatures())
                        if (feature instanceof LoopFeatures)
                            loop = (LoopFeatures) feature;

                    if (loop == null)
                        continue;

                    int delay = loop.getDelay().getValue().get();

                    if (!loop.getDelayInTick().getValue())
                        delay *= 20;

                    if (SCore.hasExecutableItems && sAct instanceof ActivatorEIFeature)
                        this.sm.sendMessage(sender, "&bEI LOOP > &7item: &e" + sAct.getParentObjectId() + " &7delay: &e" + delay + " &7(in ticks)");
                    else if (SCore.hasExecutableBlocks && sAct instanceof ActivatorEBFeature)
                        this.sm.sendMessage(sender, "&aEB LOOP > &7block: &e" + sAct.getParentObjectId() + " &7delay: &e" + delay + " &7(in ticks)");
                    else if (SCore.hasExecutableEvents && sAct instanceof ActivatorEEFeature)
                        this.sm.sendMessage(sender, "&6EE LOOP > &7event: &e" + sAct.getParentObjectId() + " &7delay: &e" + delay + " &7(in ticks)");

                }

                this.sm.sendMessage(sender, " ");
                break;
            case "projectiles":
                if (player != null)
                    NewSObjectsManagerEditor.getInstance().startEditing(player, new SProjectilesEditor());

                break;
            case "projectiles-create":
                if (player != null) {
                    if (args.length >= 1) {
                        if (SProjectilesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cThis ID already exists! Retype &6/score projectiles-create ID &7&o(ID is the ID you want for your new projectile)&c."));

                            break;
                        }

                        final SProjectile sProjectile = new SProjectile(args[0], "plugins/SCore/projectiles/" + args[0] + ".yml");

                        sProjectile.save();
                        SProjectilesManager.getInstance().addLoadedObject(sProjectile);
                        sProjectile.openEditor(player);

                        break;
                    }

                    player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new projectile, type &e/score projectiles-create ID &7&o(ID is the ID you want for your new projectile)&a."));
                }

                break;
            case "projectiles-delete":
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm deletion, type &6/score projectiles-delete {projID} confirm&c."));

                        return;
                    }

                    final Optional<SProjectile> sProjectileOpt = SProjectilesManager.getInstance().getLoadedObjectWithID(args[0]);

                    if (sProjectileOpt.isPresent()) {
                        SProjectilesManager.getInstance().deleteObject(args[0]);

                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aProjectile file (&e" + args[0] + ".yml&a) deleted!"));
                        break;
                    }

                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cProjectile file (&6" + args[0] + ".yml&c) not found!"));
                    break;
                }

                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm deletion, type &6/score projectiles-delete {projID} confirm&c."));
                break;
            case "hardnesses":
                if (player != null)
                    NewSObjectsManagerEditor.getInstance().startEditing(player, new HardnessesEditor());

                break;
            case "hardnesses-create":
                if (player != null) {
                    if (args.length >= 1) {
                        if (HardnessesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cThis ID already exists! Retype &6/score hardnesses-create ID &7&o(ID is the ID you want for your new hardness)&c."));

                            break;
                        }

                        final Hardness hard = new Hardness(args[0], "plugins/SCore/hardnesses/" + args[0] + ".yml");

                        hard.save();
                        HardnessesManager.getInstance().addLoadedObject(hard);
                        hard.openEditor(player);

                        break;
                    }

                    player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new hardness, type &e/score hardnesses-create ID &7&o(ID is the ID you want for your new hardness)&a."));
                }

                break;
            case "hardnesses-delete":
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm deletion, type &6/score hardnesses-delete {hardID} confirm&c."));

                        return;
                    }

                    final Optional<Hardness> hardnessOpt = HardnessesManager.getInstance().getLoadedObjectWithID(args[0]);

                    if (hardnessOpt.isPresent()) {
                        HardnessesManager.getInstance().deleteObject(args[0]);

                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aHardness file (&e" + args[0] + ".yml&a) deleted!"));
                        break;
                    }

                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cHardness file (&6" + args[0] + ".yml&c) not found!"));
                    break;
                }

                sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm deletion, type &6/score hardnesses-delete {hardID} confirm&c."));
                break;
            case "variables-create":
                if (player != null) {
                    if (args.length >= 1) {
                        if (VariablesManager.getInstance().getAllObjects().contains(args[0])) {
                            player.sendMessage(StringConverter.coloredString("&4[SCore] &cThis ID already exists! Retype &6/score variables-create ID &7&o(ID is the ID you want for your new variable)&c."));

                            break;
                        }

                        final Variable variable = new Variable(args[0], "plugins/SCore/variables/" + args[0] + ".yml");

                        variable.save();
                        VariablesManager.getInstance().addLoadedObject(variable);
                        variable.openEditor(player);

                        break;
                    }

                    player.sendMessage(StringConverter.coloredString("&2[SCore] &aTo create a new variable, type &e/score variables-create ID &7&o(ID is the ID you want for your new variable)&a."));
                }

                break;


            case "webhook":
                // Expect: /score webhook <url> <true|false> <message...>
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /score webhook <url> <true|false> <message...>");
                    break;
                }

                String webhookUrl = args[0];
                boolean debug;
                try {
                    debug = Boolean.parseBoolean(args[1]);
                } catch (Exception ex) {
                    sender.sendMessage(ChatColor.RED + "Second argument must be true or false.");
                    break;
                }

                // Build the message from the remaining arguments
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]);
                    if (i < args.length - 1) sb.append(" ");
                }
                String rawMessage = sb.toString();

                if (debug) {
                    sender.sendMessage("Sending webhook...");
                }

                // Escape for JSON
                String escaped = rawMessage
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "");
                String jsonPayload = "{\"content\":\"" + escaped + "\"}";

                // Perform HTTP POST asynchronously
                Runnable sendWebhook = () -> {
                    boolean success = false;
                    try {
                        URL url = new URL(webhookUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setDoOutput(true);

                        byte[] body = jsonPayload.getBytes(StandardCharsets.UTF_8);
                        conn.setFixedLengthStreamingMode(body.length);

                        try (OutputStream os = conn.getOutputStream()) {
                            os.write(body);
                        }

                        int responseCode = conn.getResponseCode();
                        conn.disconnect();
                        success = (responseCode >= 200 && responseCode < 300);
                    } catch (Exception ex) {
                        success = false;
                    }

                    if (debug) {
                        boolean finalSuccess = success;
                        Bukkit.getScheduler().runTask(SCore.plugin, () -> {
                            if (finalSuccess) {
                                sender.sendMessage("Webhook §asuccessfully sent.§r");
                            } else {
                                sender.sendMessage("Webhook §cunsuccessfully sent.§r");
                            }
                        });
                    }
                };
                SCore.schedulerHook.runAsyncTask(sendWebhook, 0);

                break;     
                
                
            
            case "variables-define":
                try {
                    if (args.length < 3) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cCommand format: /score variables-define {var_id} {type} {for} [icon_material] [default_values...]"));
                        return;
                    }
                    // Get default value, if it exists. list will not have this.
                    String variablePath = "";
                    if (args[0].contains(".")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cPlease, no \".\" in variable name."));
                        return;
                    } else {
                        // No path specified, do as usual.
                        variablePath = "plugins/SCore/variables/" + args[0] + ".yml";
                    }
                    Optional<Variable> varOpt = VariablesManager.getInstance().getVariable(args[0]);
                    if (varOpt.isPresent()) return;
                    final Variable variable = new Variable(args[0], variablePath);


                    List<String> argList = new ArrayList<>(); // Default Value
                    if (args.length > 4) {
                        argList = Arrays.asList(Arrays.copyOfRange(args, 4, args.length)); // Remaining arguments as a list
                    }
                    String formattedArgs = String.join(" ", argList);

                    // Type Check
                    switch (args[1].toLowerCase()) {
                        case "string":
                            variable.getType().setValue(Optional.of(VariableType.STRING));
                            break;
                        case "list":
                            variable.getType().setValue(Optional.of(VariableType.LIST));
                            break;
                        case "number":
                            variable.getType().setValue(Optional.of(VariableType.NUMBER));
                            break;
                        default:
                            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cYou must pick between STRING, LIST, OR NUMBER for the type! You picked: " + args[1]));
                            return;
                    }

                    if (args[2].equalsIgnoreCase("global"))
                        variable.getForFeature().setValue(Optional.of(VariableForEnum.GLOBAL));
                    else if (args[2].equalsIgnoreCase("player"))
                        variable.getForFeature().setValue(Optional.of(VariableForEnum.PLAYER));
                    else {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cYou must pick between PLAYER or GLOBAL for the scope!"));
                        return;
                    }


                    //Icon Check
                    if (args.length >= 4) {
                        try {
                            variable.getIcon().setValue(Optional.ofNullable(Material.getMaterial(args[3].toUpperCase())));
                        } catch (Exception n) {
                            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cInvalid material \"" + args[3] + "\" Check https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html"));
                            return;
                        }
                    }

                    // For non-lists, check if default value is specified. If yes, then set. Otherwise, leave empty.
                    if (!args[1].equalsIgnoreCase("list") && !formattedArgs.isEmpty())
                        variable.getDefaultValue().setValue(formattedArgs);


                    File varFile = new File(variablePath);
                    if (!varFile.getParentFile().exists()) {
                        varFile.getParentFile().mkdirs();
                    }
                    if (!varFile.exists()) {
                        varFile.createNewFile();
                    }


                    // Save to disk and load into the game
                    variable.save();
                    VariablesManager.getInstance().addLoadedObject(variable);


                    // Success
                    sender.sendMessage(StringConverter.coloredString("&2[SCore] &aSuccess defining variable: " + args[0]));

                } catch (Exception e) {
                    sender.sendMessage("&4[SCore] &cError defining variable: " + e.getMessage());
                }
                break;

            case "variables-delete":
                if (args.length >= 2) {
                    if (!args[1].equalsIgnoreCase("confirm")) {
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm deletion, type &6/score variables-delete {varID} confirm&c."));
                        return;
                    }

                    final Optional<Variable> variableOpt = VariablesManager.getInstance().getLoadedObjectWithID(args[0]);

                    if (variableOpt.isPresent()) {
                        VariablesManager.getInstance().deleteObject(args[0]);
                        VariablesManager.getInstance().deleteLoadedMYSQL(args[0]);

                        sender.sendMessage(StringConverter.coloredString("&2[SCore] &aVariable file (&e" + args[0] + ".yml&a) deleted!"));
                    } else
                        sender.sendMessage(StringConverter.coloredString("&4[SCore] &cVariable file (&6" + args[0] + ".yml&c) not found!"));
                } else
                    sender.sendMessage(StringConverter.coloredString("&4[SCore] &cTo confirm deletion, type &6/score variables-delete {varID} confirm&c."));

                break;
            case "run-player-command":
                Optional<Player> playerOpt = Optional.empty();
                StringBuilder cmd = new StringBuilder();

                for (final String arg : args)
                    if (arg.startsWith("player:")) {
                        String playerName = arg.replace("player:", "");
                        playerName = StringPlaceholder.replacePlaceholderOfPAPI(playerName, null);

                        try {
                            playerOpt = Optional.ofNullable(Bukkit.getPlayer(UUID.fromString(playerName)));
                        } catch (final Exception e) {
                            playerOpt = Optional.ofNullable(Bukkit.getPlayer(playerName));
                        }
                    } else
                        cmd.append(arg).append(" ");

                if (!playerOpt.isPresent()) {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cError: &7&oYou must specify a player with &6player:PLAYER_NAME&7&o or &6player:PLAYER_UUID&c.");

                    return;
                }

                cmd = new StringBuilder(cmd.toString().trim());

                final ActionInfo info = new ActionInfo("run-player-command", new StringPlaceholder());
                final UUID uuid = playerOpt.get().getUniqueId();

                info.setLauncherUUID(uuid);
                info.setReceiverUUID(uuid);

                final StringPlaceholder sp = new StringPlaceholder();
                sp.setPlayerPlcHldr(uuid);
                info.setSp(sp);

                final PlayerRunCommandsBuilder builder = new PlayerRunCommandsBuilder(Collections.singletonList(cmd.toString()), info);
                CommandsExecutor.runCommands(builder);

                break;
            case "run-entity-command":
                Optional<Entity> entityOptional = Optional.empty();
                StringBuilder entityCmd = new StringBuilder();

                for (final String arg : args)
                    if (arg.startsWith("entity:")) {
                        final String entityName = arg.replace("entity:", "");

                        try {
                            entityOptional = Optional.ofNullable(Bukkit.getEntity(UUID.fromString(entityName)));
                        } catch (final Exception ignored) {
                        }
                    } else
                        entityCmd.append(arg).append(" ");

                if (!entityOptional.isPresent()) {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cError: &7&oYou must specify an entity with &6entity:ENTITY_UUID&c.");

                    return;
                }

                entityCmd = new StringBuilder(entityCmd.toString().trim());

                final ActionInfo infoEntity = new ActionInfo("run-entity-command", new StringPlaceholder());

                infoEntity.setEntityUUID(entityOptional.get().getUniqueId());
                infoEntity.setReceiverUUID(entityOptional.get().getUniqueId());

                final EntityRunCommandsBuilder entityRunCommandsBuilder = new EntityRunCommandsBuilder(Collections.singletonList(entityCmd.toString()), infoEntity);
                CommandsExecutor.runCommands(entityRunCommandsBuilder);

                break;
            case "run-block-command":
                Optional<Player> playerOpt2 = Optional.empty();
                Optional<Block> blockOpt = Optional.empty();
                StringBuilder blockCmd = new StringBuilder();

                for (final String arg : args)
                    if (arg.startsWith("player:")) {
                        final String playerName = arg.replace("player:", "");
                        try {
                            playerOpt2 = Optional.ofNullable(Bukkit.getPlayer(UUID.fromString(playerName)));
                        } catch (final Exception e) {
                            playerOpt2 = Optional.ofNullable(Bukkit.getPlayer(playerName));
                        }
                    } else if (arg.startsWith("block:")) {
                        final String blockLoc = arg.replace("block:", "");

                        try {
                            final String[] loc = blockLoc.split(",");

                            final Optional<World> world = AllWorldManager.getWorld(loc[0]);
                            final double x = Double.parseDouble(loc[1]);
                            final double y = Double.parseDouble(loc[2]);
                            final double z = Double.parseDouble(loc[3]);

                            blockOpt = Optional.of(world.get().getBlockAt(new Location(world.get(), x, y, z)));
                        } catch (final Exception ignored) {
                        }
                    } else
                        blockCmd.append(arg).append(" ");

                if (!blockOpt.isPresent()) {
                    SendMessage.sendMessageNoPlch(sender, "&4[SCore] &cError: &7&oYou must specify a block with &6block:WORLD,X,Y,Z&c.");

                    return;
                }

                blockCmd = new StringBuilder(blockCmd.toString().trim());

                final ActionInfo infoBlock = new ActionInfo("run-block-command", new StringPlaceholder());
                final Location blockLocation = blockOpt.get().getLocation();

                infoBlock.setBlockLocationX(blockLocation.getBlockX());
                infoBlock.setBlockLocationY(blockLocation.getBlockY());
                infoBlock.setBlockLocationZ(blockLocation.getBlockZ());
                infoBlock.setBlockLocationWorld(blockLocation.getWorld().getUID());
                infoBlock.setOldBlockMaterialName(blockOpt.get().getType().name());

                playerOpt2.ifPresent(value -> infoBlock.setLauncherUUID(value.getUniqueId()));

                final BlockRunCommandsBuilder blockRunCommandsBuilder = new BlockRunCommandsBuilder(Collections.singletonList(blockCmd.toString()), infoBlock);
                CommandsExecutor.runCommands(blockRunCommandsBuilder);

                break;
            default:
                break;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {

        if (command.getName().equalsIgnoreCase("score")) {
            final List<String> arguments = new ArrayList<>();

            if (args.length == 1) {
                arguments.add("clear");
                arguments.add("cooldowns");
                arguments.add("dialog");
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
                arguments.add("variables-define NAME TYPE SCOPE ICON default arguments...");
                arguments.add("webhook URL debugTrue/False");

                arguments.add("variables-delete");
                arguments.add("run-player-command");
                arguments.add("run-entity-command");
                arguments.add("run-block-command");

                final List<String> argumentsPerm = new ArrayList<>();

                for (final String str : arguments)
                    if (sender.hasPermission("score.cmd." + command) || sender.hasPermission("score.cmds") || sender.hasPermission("score.*"))
                        argumentsPerm.add(str);

                Collections.sort(argumentsPerm);
                return argumentsPerm.stream()
                        .filter(element -> element.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .collect(Collectors.toList());
            }

            if (args.length >= 2) {
                switch (args[0].toLowerCase()) {
                    case "cooldowns":
                        if (args.length == 2) {
                            arguments.add("clear");
                        } else if (args.length == 3 && args[1].equalsIgnoreCase("clear")) {
                            List<String> cooldowns = CooldownsManager.getInstance().getAllCooldownIds();
                            if (cooldowns.isEmpty()) arguments.add("No cooldowns to clear");
                            else arguments.addAll(cooldowns);
                        } else if (args.length == 4 && args[1].equalsIgnoreCase("clear")) {
                            arguments.add("[UUID]");
                        }
                        break;
                    case "dialog":
                        if (args.length == 2) {
                            arguments.add("example");
                        }
                        break;
                    case "variables":
                        if (args.length == 2) {
                            arguments.add("info");
                            arguments.add("list");
                            arguments.add("set");
                            arguments.add("modification");
                            arguments.add("list-add");
                            arguments.add("list-remove");
                            arguments.add("clear");
                            arguments.add("set-default");
                        } else if (args.length == 3 && args[1].equalsIgnoreCase("info"))
                            arguments.addAll(VariablesManager.getInstance().getVariableIdsList());
                        else if (args.length == 3 && (args[1].equalsIgnoreCase("set")
                                || args[1].equalsIgnoreCase("modification")
                                || args[1].equalsIgnoreCase("clear")
                                || args[1].equalsIgnoreCase("list-add")
                                || args[1].equalsIgnoreCase("list-remove"))) {
                            arguments.add("global");
                            arguments.add("player");
                        } else if (args.length == 3 && (args[1].equalsIgnoreCase("set-default"))) {
                            arguments.addAll(VariablesManager.getInstance().getVariableIdsList());
                        } else if (args.length == 4 && (args[1].equalsIgnoreCase("set")
                                || args[1].equalsIgnoreCase("modification")
                                || args[1].equalsIgnoreCase("clear")
                                || args[1].equalsIgnoreCase("list-add")
                                || args[1].equalsIgnoreCase("list-remove")))
                            arguments.addAll(VariablesManager.getInstance().getVariableIdsList());

                        break;
                    case "variables-delete":
                        if (args.length == 2)
                            arguments.addAll(VariablesManager.getInstance().getVariableIdsList());
                        else if (args.length == 3)
                            arguments.add("confirm");

                        break;
                    case "run-player-command":
                        if (args.length == 2)
                            arguments.add("player:");
                        else if (args.length == 3 && args[1].startsWith("player:")) {
                            arguments.addAll(PlayerCommandManager.getInstance().getCommandsDisplay().values());

                            for (int i = 0; i < arguments.size(); i++) {
                                final String arg = arguments.get(i);

                                if (arg.length() > 50)
                                    arguments.set(i, arg.substring(0, 45) + "...");
                            }
                        }

                        break;
                    case "run-entity-command":
                        if (args.length == 2)
                            arguments.add("entity:");
                        else if (args.length == 3 && args[1].startsWith("entity:")) {
                            arguments.addAll(EntityCommandManager.getInstance().getCommandsDisplay().values());

                            for (int i = 0; i < arguments.size(); i++) {
                                final String arg = arguments.get(i);

                                if (arg.length() > 50)
                                    arguments.set(i, arg.substring(0, 45) + "...");
                            }
                        }

                        break;
                    case "run-block-command":
                        if (args.length == 2)
                            arguments.add("block:");
                        else if (args.length == 3 && args[1].startsWith("block:")) {
                            arguments.addAll(BlockCommandManager.getInstance().getCommandsDisplay().values());

                            for (int i = 0; i < arguments.size(); i++) {
                                final String arg = arguments.get(i);

                                if (arg.length() > 50)
                                    arguments.set(i, arg.substring(0, 45) + "...");
                            }
                        }

                        break;
                    case "hardnesses-delete":
                        if (args.length == 2)
                            arguments.addAll(HardnessesManager.getInstance().getExecutableBlockIdsList());
                        else if (args.length == 3)
                            arguments.add("confirm");

                        break;
                    case "projectiles-delete":
                        if (args.length == 2)
                            arguments.addAll(SProjectilesManager.getInstance().getExecutableBlockIdsList());
                        else if (args.length == 3)
                            arguments.add("confirm");

                    case "clear":
                        if (args.length == 2) {
                            // online players
                            for (final Player p : Bukkit.getOnlinePlayers())
                                arguments.add(p.getName());
                            return arguments;
                        }
                        else if (args.length == 3) {
                            for (ClearType type : ClearType.values()) {
                                arguments.add(type.name());
                            }
                            return arguments;
                        }
                        break;
                }

                Collections.sort(arguments);
                return arguments.stream()
                        .filter(element -> element.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        return null;
    }
    
    /**
     * Opens a dialog example for the player using Paper's Dialog API
     * 
     * @param player the player to show the dialog to
     */
    private void openDialogExample(Player player) {
        try {
            // Since the Paper Dialog API is experimental and not fully implemented,
            // we'll create a fallback implementation using a combination of approaches
            
            // First, try to use the native Minecraft dialog command if available
            if (SCore.is1v21v6Plus()) {
                // Use native dialog system with a JSON structure
                String dialogJson = createExampleDialogJson();
                
                // Execute the dialog command
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
                    "dialog show " + player.getName() + " " + dialogJson);
                
                player.sendMessage(StringConverter.coloredString("&2[SCore] &aDialog example opened! This uses Minecraft's native dialog system."));
            } else {
                // Fallback for older versions - show a message about the feature
                player.sendMessage(StringConverter.coloredString("&6[SCore] &eDialog Example - Paper API Integration"));
                player.sendMessage(StringConverter.coloredString("&7This feature demonstrates the Paper Dialog API integration."));
                player.sendMessage(StringConverter.coloredString("&7Your server version: &e" + Bukkit.getVersion()));
                player.sendMessage(StringConverter.coloredString("&7Dialog API requires Paper 1.21.6+ for full native support."));
                
                // Create example data that would be saved
                createExampleDialogData(player);
            }
            
        } catch (Exception e) {
            player.sendMessage(StringConverter.coloredString("&4[SCore] &cError opening dialog: " + e.getMessage()));
            
            // Create a fallback demonstration
            createExampleDialogData(player);
        }
    }
    
    /**
     * Creates example JSON for native Minecraft dialog system
     */
    private String createExampleDialogJson() {
        return "{\n" +
            "  \"type\": \"minecraft:confirmation\",\n" +
            "  \"title\": \"SCore Dialog Example\",\n" +
            "  \"body\": {\n" +
            "    \"type\": \"minecraft:plain_message\",\n" +
            "    \"height\": 150,\n" +
            "    \"text\": \"Welcome to the SCore Dialog Example! Please fill out the form below:\"\n" +
            "  },\n" +
            "  \"inputs\": [\n" +
            "    {\n" +
            "      \"key\": \"player_name\",\n" +
            "      \"type\": \"minecraft:text\",\n" +
            "      \"label\": \"Your Name\",\n" +
            "      \"initial\": \"Player\",\n" +
            "      \"max_length\": 50\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"favorite_block\",\n" +
            "      \"type\": \"minecraft:single_option\",\n" +
            "      \"label\": \"Favorite Block\",\n" +
            "      \"options\": [\n" +
            "        {\"value\": \"diamond_block\", \"label\": \"Diamond Block\"},\n" +
            "        {\"value\": \"emerald_block\", \"label\": \"Emerald Block\"},\n" +
            "        {\"value\": \"gold_block\", \"label\": \"Gold Block\"}\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"experience_years\",\n" +
            "      \"type\": \"minecraft:number_range\",\n" +
            "      \"label\": \"Years Playing Minecraft\",\n" +
            "      \"range\": {\"min\": 0, \"max\": 15},\n" +
            "      \"default\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"likes_building\",\n" +
            "      \"type\": \"minecraft:boolean\",\n" +
            "      \"label\": \"Enjoys Building\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"key\": \"comments\",\n" +
            "      \"type\": \"minecraft:text\",\n" +
            "      \"label\": \"Additional Comments\",\n" +
            "      \"initial\": \"\",\n" +
            "      \"max_length\": 200,\n" +
            "      \"multiline\": true\n" +
            "    }\n" +
            "  ],\n" +
            "  \"yes\": {\n" +
            "    \"label\": \"Save Response\",\n" +
            "    \"action\": {\n" +
            "      \"type\": \"run_command\",\n" +
            "      \"command\": \"score dialog-save $(player_name) $(favorite_block) $(experience_years) $(likes_building) $(comments)\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"no\": {\n" +
            "    \"label\": \"Cancel\"\n" +
            "  }\n" +
            "}";
    }
    
    /**
     * Creates example dialog data and saves it to a file (fallback method)
     */
    private void createExampleDialogData(Player player) {
        try {
            // Create the dialog responses directory
            File pluginFolder = SCore.plugin.getDataFolder();
            File dialogFolder = new File(pluginFolder, "dialog-examples");
            if (!dialogFolder.exists()) {
                dialogFolder.mkdirs();
            }
            
            // Create example data file
            File exampleFile = new File(dialogFolder, "example-" + player.getName() + "-" + System.currentTimeMillis() + ".yml");
            
            // Example data that would come from dialog inputs
            StringBuilder exampleData = new StringBuilder();
            exampleData.append("# SCore Dialog Example Data\n");
            exampleData.append("# Generated on: ").append(new java.util.Date().toString()).append("\n");
            exampleData.append("player_uuid: ").append(player.getUniqueId().toString()).append("\n");
            exampleData.append("player_name: \"").append(player.getName()).append("\"\n");
            exampleData.append("favorite_block: \"diamond_block\"\n");
            exampleData.append("experience_years: 5\n");
            exampleData.append("likes_building: true\n");
            exampleData.append("comments: \"This is an example comment from the dialog system!\"\n");
            exampleData.append("\n# This file demonstrates what would be saved from dialog responses\n");
            exampleData.append("# In a real dialog, these values would come from user input\n");
            
            // Write to file
            java.nio.file.Files.write(exampleFile.toPath(), exampleData.toString().getBytes());
            
            player.sendMessage(StringConverter.coloredString("&2[SCore] &aExample dialog data created at: &7" + exampleFile.getPath()));
            player.sendMessage(StringConverter.coloredString("&7This demonstrates what would be saved from a real dialog response."));
            
        } catch (Exception e) {
            player.sendMessage(StringConverter.coloredString("&4[SCore] &cError creating example data: " + e.getMessage()));
            e.printStackTrace();
        }
    }
    
    /**
     * Saves dialog response data to a file
     * 
     * @param sender the command sender (player who submitted the dialog)
     * @param args the dialog response arguments
     */
    private void saveDialogResponse(CommandSender sender, String[] args) {
        try {
            // Create the dialog responses directory
            File pluginFolder = SCore.plugin.getDataFolder();
            File dialogFolder = new File(pluginFolder, "dialog-responses");
            if (!dialogFolder.exists()) {
                dialogFolder.mkdirs();
            }
            
            // Get player name (either from sender or from args if run by console)
            String playerName = sender.getName();
            if (sender.getName().equals("CONSOLE") && args.length > 0) {
                // Try to get the player name from the first argument
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer != null) {
                    playerName = targetPlayer.getName();
                }
            }
            
            // Create response file
            File responseFile = new File(dialogFolder, "response-" + playerName + "-" + System.currentTimeMillis() + ".yml");
            
            // Build response data from arguments
            StringBuilder responseData = new StringBuilder();
            responseData.append("# SCore Dialog Response\n");
            responseData.append("# Submitted on: ").append(new java.util.Date().toString()).append("\n");
            responseData.append("submitted_by: \"").append(playerName).append("\"\n");
            
            // Parse arguments based on expected format: playerName favoriteBlock experienceYears likesBuilding comments...
            if (args.length >= 1) responseData.append("player_name: \"").append(args[0]).append("\"\n");
            if (args.length >= 2) responseData.append("favorite_block: \"").append(args[1]).append("\"\n");
            if (args.length >= 3) responseData.append("experience_years: ").append(args[2]).append("\n");
            if (args.length >= 4) responseData.append("likes_building: ").append(args[3]).append("\n");
            if (args.length >= 5) {
                // Combine remaining arguments as comments
                StringBuilder comments = new StringBuilder();
                for (int i = 4; i < args.length; i++) {
                    comments.append(args[i]);
                    if (i < args.length - 1) comments.append(" ");
                }
                responseData.append("comments: \"").append(comments.toString()).append("\"\n");
            }
            
            responseData.append("\n# Additional metadata\n");
            responseData.append("server_time: ").append(System.currentTimeMillis()).append("\n");
            responseData.append("server_version: \"").append(Bukkit.getVersion()).append("\"\n");
            
            // Write to file
            java.nio.file.Files.write(responseFile.toPath(), responseData.toString().getBytes());
            
            // Notify the player if they're online
            Player player = Bukkit.getPlayer(playerName);
            if (player != null && player.isOnline()) {
                player.sendMessage(StringConverter.coloredString("&2[SCore] &aYour dialog response has been saved!"));
                player.sendMessage(StringConverter.coloredString("&7File: &e" + responseFile.getName()));
            }
            
            // Log to console
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Dialog response saved for player &e" + playerName + " &7to file &e" + responseFile.getName());
            
        } catch (Exception e) {
            sender.sendMessage(StringConverter.coloredString("&4[SCore] &cError saving dialog response: " + e.getMessage()));
            e.printStackTrace();
        }
    }
}