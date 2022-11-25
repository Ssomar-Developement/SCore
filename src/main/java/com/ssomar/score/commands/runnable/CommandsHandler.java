package com.ssomar.score.commands.runnable;


import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.block.BlockRunCommand;
import com.ssomar.score.commands.runnable.entity.EntityRunCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommand;
import com.ssomar.score.data.BlockCommandsQuery;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.EntityCommandsQuery;
import com.ssomar.score.data.PlayerCommandsQuery;
import com.ssomar.score.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
@Setter
public class CommandsHandler implements Listener {

    private static CommandsHandler instance;

    /* DelayedCommands by RunCommand UUID */
    private final Map<UUID, RunCommand> delayedCommandsByRcUuid;

    /* DelayedCommands by receiver UUID */
    private final Map<UUID, List<RunCommand>> delayedCommandsByReceiverUuid;

    /* DelayedCommands by entity UUID */
    List<EntityRunCommand> delayedCommandsByEntityUuid;

    /* DelayedCommands by block UUID */
    List<BlockRunCommand> delayedCommandsByBlockUuid;

    /* for "morph item" timing between delete item and regive item (2 ticks)  player */
    private Map<Player, Long> stopPickup;

    /* Commands delayed saved that wait to be runned  PLAYER_UUID|PLAYERRUNCOMMAND -> Useful to avoid to call a query at each join*/
    private Map<UUID, List<PlayerRunCommand>> delayedCommandsSaved;

    public CommandsHandler() {
        delayedCommandsByRcUuid = new HashMap<>();
        delayedCommandsByReceiverUuid = new HashMap<>();
        delayedCommandsByEntityUuid = new ArrayList<>();
        delayedCommandsByBlockUuid = new ArrayList<>();
        stopPickup = new HashMap<>();
        delayedCommandsSaved = new HashMap<>();
    }

    public static CommandsHandler getInstance() {
        if (instance == null) instance = new CommandsHandler();
        return instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (getInstance().getDelayedCommandsSaved().containsKey(p.getUniqueId())) {
            for (PlayerRunCommand command : getInstance().getDelayedCommandsSaved().get(p.getUniqueId())) {
                command.run();
                SCore.plugin.getLogger().info("SCore will execute the delayed command saved for " + p.getName() + " : " + command.getBrutCommand()+ " >> delay: "+command.getDelay());
            }
        }
        getInstance().getDelayedCommandsSaved().remove(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        List<PlayerRunCommand> commands = getInstance().getDelayedCommandsWithReceiver(p.getUniqueId());
        List<PlayerRunCommand> commandsToSave = new ArrayList<>();
        for (PlayerRunCommand command : commands) {
            if (!command.isRunOffline()) {
                commandsToSave.add(command);
            }
        }

        if (getInstance().getDelayedCommandsSaved().containsKey(p.getUniqueId())) {
            getInstance().getDelayedCommandsSaved().get(p.getUniqueId()).addAll(commandsToSave);
        } else getInstance().getDelayedCommandsSaved().put(p.getUniqueId(), commandsToSave);

        for (PlayerRunCommand command : commandsToSave) {
            getInstance().removeDelayedCommand(command.getUuid(), p.getUniqueId());
        }
    }

    public void onEnable() {

        getInstance().setDelayedCommandsSaved(PlayerCommandsQuery.loadSavedCommands(Database.getInstance().connect()));
        int cpt = 0;
        for (UUID uuid : getInstance().getDelayedCommandsSaved().keySet()) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            for (PlayerRunCommand command : getInstance().getDelayedCommandsSaved().get(uuid)) {
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7SCore loaded the delayed command for &a" + player.getName() + " &7: &6" + command.getBrutCommand() + " &7>> delay: &b" + command.getDelay());
                cpt++;
            }
        }
        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7SCore loaded &6" + cpt + " &7delayed commands saved");

        /* Quite useless because at the start of the server the entities seems not loaded and the Bukkit.getentity return null */
        List<EntityRunCommand> commands = EntityCommandsQuery.selectEntityCommands(Database.getInstance().connect());
        for (EntityRunCommand eCommand : commands) {
            eCommand.run();
        }
        EntityCommandsQuery.deleteEntityCommands(Database.getInstance().connect());


        List<BlockRunCommand> commands2 = BlockCommandsQuery.selectAllCommands(Database.getInstance().connect());
        for (BlockRunCommand bCommand : commands2) {
            bCommand.run();
        }
        BlockCommandsQuery.deleteCommands(Database.getInstance().connect());
    }

    public void onDisable() {
        List<PlayerRunCommand> savedCommands = new ArrayList<>(getInstance().getDelayedPlayerCommands());
        for (UUID uuid : getInstance().getDelayedCommandsSaved().keySet()) {
            savedCommands.addAll(getInstance().getDelayedCommandsSaved().get(uuid));
        }
        for (PlayerRunCommand command : savedCommands) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(command.getReceiverUUID());
            SCore.plugin.getLogger().info("SCore saved the delayed command for " + player.getName() + " : " + command.getBrutCommand());
        }
        PlayerCommandsQuery.deleteCommands(Database.getInstance().connect());
        PlayerCommandsQuery.insertCommand(Database.getInstance().connect(), savedCommands, false);
        getInstance().getDelayedCommandsSaved().clear();

        EntityCommandsQuery.insertCommand(Database.getInstance().connect(), this.delayedCommandsByEntityUuid);
        this.delayedCommandsByEntityUuid.clear();

        BlockCommandsQuery.insertCommand(Database.getInstance().connect(), this.delayedCommandsByBlockUuid);
        this.delayedCommandsByBlockUuid.clear();

        this.delayedCommandsByReceiverUuid.clear();
    }

    public void addDelayedCommand(@NotNull RunCommand command) {
        delayedCommandsByRcUuid.put(command.getUuid(), command);
        if (command instanceof PlayerRunCommand) {
            UUID receiverUUID = ((PlayerRunCommand) command).getReceiverUUID();
            if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
                delayedCommandsByReceiverUuid.get(receiverUUID).add(command);
            } else {
                List<RunCommand> list = new ArrayList<>();
                list.add(command);
                delayedCommandsByReceiverUuid.put(((PlayerRunCommand) command).getReceiverUUID(), list);
                //System.out.println(">>>>>> Yes add :: "+delayedCommandsByReceiverUuid.size());
            }
        } else if (command instanceof EntityRunCommand) {
            this.delayedCommandsByEntityUuid.add((EntityRunCommand) command);
        } else if (command instanceof BlockRunCommand) {
            this.delayedCommandsByBlockUuid.add((BlockRunCommand) command);
        }
    }

    public void removeDelayedCommand(UUID uuid, @Nullable UUID receiverUUID) {
        if (delayedCommandsByRcUuid.containsKey(uuid)) {
            BukkitTask task;
            if ((task = delayedCommandsByRcUuid.get(uuid).getTask()) != null) task.cancel();
            delayedCommandsByRcUuid.remove(uuid);
        }

        /* ==================================== */
        RunCommand toDelete = null;

        for (RunCommand rC : delayedCommandsByEntityUuid) {
            if (rC.getUuid().equals(uuid)) {
                toDelete = rC;
                BukkitTask task;
                if ((task = rC.getTask()) != null) task.cancel();
            }
        }
        if (toDelete != null) delayedCommandsByEntityUuid.remove(toDelete);

        /* ==================================== */
        toDelete = null;

        for (RunCommand rC : delayedCommandsByBlockUuid) {
            if (rC.getUuid().equals(uuid)) {
                toDelete = rC;
                BukkitTask task;
                if ((task = rC.getTask()) != null) task.cancel();
            }
        }
        if (toDelete != null) delayedCommandsByBlockUuid.remove(toDelete);


        if (receiverUUID != null) {
            if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {

                List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
                toDelete = null;

                for (RunCommand rC : runCommands) {
                    if (rC.getUuid().equals(uuid)) {
                        toDelete = rC;
                        BukkitTask task;
                        if ((task = rC.getTask()) != null) task.cancel();
                    }
                }
                if (toDelete != null) runCommands.remove(toDelete);

                if (runCommands.isEmpty()) delayedCommandsByReceiverUuid.remove(receiverUUID);
            }
        }

        //System.out.println(">>>>>> Yess remove :: "+delayedCommandsByReceiverUuid.size());
    }

    public void removeAllDelayedCommands(UUID receiverUUID) {
        if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
            List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);

            for (RunCommand rC : runCommands) {
                this.removeDelayedCommand(rC.getUuid(), null);
            }

            delayedCommandsByReceiverUuid.remove(receiverUUID);
        }
    }

    public List<PlayerRunCommand> getDelayedCommandsWithReceiver(UUID receiverUUID) {
        List<PlayerRunCommand> commands = new ArrayList<>();
        if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
            List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
            for (RunCommand rC : runCommands) {
                if (rC instanceof PlayerRunCommand) commands.add((PlayerRunCommand) rC);
            }
        }
        return commands;
    }

    public List<PlayerRunCommand> getDelayedPlayerCommands() {
        List<PlayerRunCommand> commands = new ArrayList<>();
        for (List<RunCommand> runCommands : delayedCommandsByReceiverUuid.values()) {
            for (RunCommand rC : runCommands) {
                if (rC instanceof PlayerRunCommand) commands.add((PlayerRunCommand) rC);
            }
        }
        return commands;
    }

    public void addStopPickup(Player p, Integer delay) {
        long time = System.currentTimeMillis() + (delay * 50);
        //System.out.println("ADD "+p.getDisplayName()+ " time: "+time);
        stopPickup.put(p, time);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SCore.plugin, () -> {
            if (stopPickup.containsKey(p) && stopPickup.get(p) == time) {
                stopPickup.remove(p);
            }
        }, delay);
    }

    //FAIRE AVEC LHEURE DE FIN CEST MIEUX

    public boolean hasStopPickup(@NotNull Player p) {
        long time = System.currentTimeMillis();
        //System.out.println("pickup "+CommandsHandler.getInstance().getStopPickup().get(p)+" actual "+time);
        boolean stop = stopPickup.containsKey(p) && CommandsHandler.getInstance().getStopPickup().get(p) > time;
        if (!stop) {
            stopPickup.remove(p);
        }
        return stop;
    }

    public Map<Player, Long> getStopPickup() {
        return stopPickup;
    }

    public void setStopPickup(Map<Player, Long> stopPickup) {
        this.stopPickup = stopPickup;
    }

}
