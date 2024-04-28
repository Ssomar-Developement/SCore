package com.ssomar.score.commands.runnable;


import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.block.BlockRunCommand;
import com.ssomar.score.commands.runnable.entity.EntityRunCommand;
import com.ssomar.score.commands.runnable.player.PlayerRunCommand;
import com.ssomar.score.data.BlockCommandsQuery;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.EntityCommandsQuery;
import com.ssomar.score.data.PlayerCommandsQuery;
import com.ssomar.score.utils.logging.Utils;
import com.ssomar.score.utils.scheduler.ScheduledTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

    private Map<Player, List<Material>> stopPickupMaterial;

    /* Commands delayed saved that wait to be runned  PLAYER_UUID|PLAYERRUNCOMMAND -> Useful to avoid to call a query at each join*/
    private Map<UUID, List<PlayerRunCommand>> delayedCommandsSaved;

    public CommandsHandler() {
        delayedCommandsByRcUuid = new HashMap<>();
        delayedCommandsByReceiverUuid = new HashMap<>();
        delayedCommandsByEntityUuid = new ArrayList<>();
        delayedCommandsByBlockUuid = new ArrayList<>();
        stopPickup = new HashMap<>();
        stopPickupMaterial = new HashMap<>();
        delayedCommandsSaved = new HashMap<>();

        // create new timer task
        /* Bukkit.getScheduler().runTaskTimer(SCore.plugin, () -> {
            // display in console all info of delayedCommandsByReceiverUuid
            for (Map.Entry<UUID, List<RunCommand>> entry : delayedCommandsByReceiverUuid.entrySet()) {
                for(RunCommand runCommand: entry.getValue()) {
                   System.out.println("delayedCommandsByReceiverUuid: " + entry.getKey() + " " + runCommand.getBrutCommand() + " DELAYYYYYYY<>" + runCommand.getDelay() + " " + runCommand.getRunTime());
                }
            }
        }, 0L, 40L);*/
    }

    public static CommandsHandler getInstance() {
        if (instance == null) instance = new CommandsHandler();
        return instance;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerJoinEvent(PlayerJoinEvent e) {

        //System.out.println("JOIN EVENT");
        if (!SCore.plugin.isEnabled()) return;

        //System.out.println("JOIN EVENT 2");
        Player p = e.getPlayer();
        if (getInstance().getDelayedCommandsSaved().containsKey(p.getUniqueId())) {
            //System.out.println("JOIN EVENT 3 >>"+getInstance().getDelayedCommandsSaved().get(p.getUniqueId()).size());
            for (PlayerRunCommand command : getInstance().getDelayedCommandsSaved().get(p.getUniqueId())) {
                //System.out.println("JOIN EVENT 4");
                command.run();
                SCore.plugin.getLogger().info("SCore will execute the delayed command saved for " + p.getName() + " : " + command.getBrutCommand() + " >> delay: " + command.getDelay());
            }
        }
        getInstance().getDelayedCommandsSaved().remove(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerQuitEvent(PlayerQuitEvent e) {

        if (!SCore.plugin.isEnabled()) return;

        Player p = e.getPlayer();

        List<PlayerRunCommand> commands = getInstance().getDelayedCommandsWithReceiver(p.getUniqueId());
        //System.out.println("QUIT LIST SIEZ: "+commands.size());
        List<PlayerRunCommand> commandsToSave = new ArrayList<>();
        for (PlayerRunCommand command : commands) {
            if (!command.isRunOffline()) {
                //System.out.println("QUTI >> "+command.getBrutCommand());
                if (!command.isClearIfDisconnect()) commandsToSave.add(command);
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
        EntityCommandsQuery.deleteEntityCommands(Database.getInstance().connect(true));

        List<BlockRunCommand> commands2 = BlockCommandsQuery.selectAllCommands(Database.getInstance().connect());
        for (BlockRunCommand bCommand : commands2) {
            bCommand.run();
        }
        BlockCommandsQuery.deleteCommands(Database.getInstance().connect(true));
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
            //System.out.println("ADD DELEYED >>"+command.getBrutCommand());
            UUID receiverUUID = ((PlayerRunCommand) command).getReceiverUUID();
            if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
                delayedCommandsByReceiverUuid.get(receiverUUID).add(command);
                //System.out.println("ADD DELEYED >>"+command.getBrutCommand()+ ">>>>size >>>"+delayedCommandsByReceiverUuid.get(receiverUUID).size());
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
        removeDelayedCommand(uuid, receiverUUID, true);
    }

    public void removeDelayedCommand(UUID uuid, @Nullable UUID receiverUUID, boolean canceltask) {
        //SsomarDev.testMsg("removeDelayedCommand >> "+uuid, true);
        if (delayedCommandsByRcUuid.containsKey(uuid)) {
            ScheduledTask task;
            if ((task = delayedCommandsByRcUuid.get(uuid).getTask()) != null && canceltask) {
                //SsomarDev.testMsg("removeDelayedCommand CANCEL>> "+uuid, true);
                task.cancel();
            }
            delayedCommandsByRcUuid.remove(uuid);
        }

        /* ==================================== */
        RunCommand toDelete = null;

        for (RunCommand rC : delayedCommandsByEntityUuid) {
            if (rC.getUuid().equals(uuid)) {
                toDelete = rC;
                ScheduledTask task;
                if ((task = rC.getTask()) != null && canceltask) task.cancel();
            }
        }
        if (toDelete != null) delayedCommandsByEntityUuid.remove(toDelete);

        /* ==================================== */
        toDelete = null;

        for (RunCommand rC : delayedCommandsByBlockUuid) {
            if (rC.getUuid().equals(uuid)) {
                toDelete = rC;
                ScheduledTask task;
                if ((task = rC.getTask()) != null && canceltask) task.cancel();
            }
        }
        if (toDelete != null) delayedCommandsByBlockUuid.remove(toDelete);


        if (receiverUUID != null) {
            // System.out.println("REMOVE DELEYED 1 >>"+uuid);
            if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
                // System.out.println("REMOVE DELEYED 2 >>"+uuid);
                List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
                toDelete = null;
                //System.out.println("REMOVE DELEYED 3 >>"+uuid);
                for (RunCommand rC : runCommands) {
                    if (rC.getUuid().equals(uuid)) {
                        toDelete = rC;
                        ScheduledTask task;
                        if ((task = rC.getTask()) != null && canceltask) task.cancel();
                    }
                }
                if (toDelete != null) runCommands.remove(toDelete);

                if (runCommands.isEmpty()) {
                    delayedCommandsByReceiverUuid.remove(receiverUUID);
                    //  System.out.println("REMOVE DELEYED 4 >>"+uuid);
                }
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

    static int i = 0;

    public List<PlayerRunCommand> getDelayedCommandsWithReceiver(UUID receiverUUID) {
        List<PlayerRunCommand> commands = new ArrayList<>();
        if (delayedCommandsByReceiverUuid.containsKey(receiverUUID)) {
            List<RunCommand> runCommands = delayedCommandsByReceiverUuid.get(receiverUUID);
            for (RunCommand rC : runCommands) {
                //System.out.println("getDelayedCommandsWithReceiver :: "+i+">>>"+rC.getBrutCommand());
                if (rC instanceof PlayerRunCommand) commands.add((PlayerRunCommand) rC);
            }
        }
        i++;
        return commands;
    }

    public List<PlayerRunCommand> getDelayedPlayerCommands() {
        List<PlayerRunCommand> commands = new ArrayList<>();
        for (List<RunCommand> runCommands : getInstance().getDelayedCommandsByReceiverUuid().values()) {
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

    public void addStopPickup(Player p, Integer delay, Material material) {
        if (stopPickupMaterial.containsKey(p)) {
            stopPickupMaterial.get(p).add(material);
        } else {
            List<Material> list = new ArrayList<>();
            list.add(material);
            stopPickupMaterial.put(p, list);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SCore.plugin, () -> {
            if (stopPickupMaterial.containsKey(p)) {
                stopPickupMaterial.get(p).remove(material);
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

    public boolean hasStopPickup(@NotNull Player p, Material material) {
        return stopPickupMaterial.containsKey(p) && stopPickupMaterial.get(p).contains(material);
    }

    public Map<Player, Long> getStopPickup() {
        return stopPickup;
    }

    public void setStopPickup(Map<Player, Long> stopPickup) {
        this.stopPickup = stopPickup;
    }

}
