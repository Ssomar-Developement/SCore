package com.ssomar.score.commands.runnable.player.commands.sudoop;

import com.ssomar.score.SCore;
import com.ssomar.score.data.Database;
import com.ssomar.score.data.SecurityOPQuery;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class SUDOOPManager {

    private static SUDOOPManager instance;
    private HashMap<Player, List<String>> commandsAsOP;
    private List<UUID> playersThatMustBeDeOP;
    private static Map<UUID, Boolean> cachedOpStatus = new HashMap<>();

    public SUDOOPManager() {
        commandsAsOP = new HashMap<>();
        playersThatMustBeDeOP = SecurityOPQuery.loadUsersOp(Database.getInstance().connect());
    }

    public boolean isPlayerOpCached(UUID playerUUID) {
        return cachedOpStatus.getOrDefault(playerUUID, false);
    }

    public static void performCommand(final Player player, final String command) {
//	    Runnable runnable = new Runnable() {
//			@Override
//			public void run() {
        player.chat(command);
//			}
//		};
//		runnable.runTask(SCore.getPlugin());
    }

    public static SUDOOPManager getInstance() {
        if (instance == null) instance = new SUDOOPManager();
        return instance;
    }

    public void runOPCommand(Player player, String cmd) {
        String command = this.verifyCommand(cmd);
        if (player.isOp()) performCommand(player, command);
        else {
            UUID playerUUID = player.getUniqueId();
            try {
                if (commandsAsOP.containsKey(player)) {
                    commandsAsOP.get(player).add(command);
                } else {
                    ArrayList<String> cList = new ArrayList<>();
                    cList.add(command);
                    commandsAsOP.put(player, cList);
                }

                /*  to be sure that even if the server crash or the server couldnt run finally. we have the info in the db and we can remove the ops when the player connect again */
                SCore.schedulerHook.runAsyncTask(() -> {
                    SecurityOPQuery.insertPlayerOP(Database.getInstance().connect(), Collections.singletonList(player));
                },0);

                // New main method cache, and db is in addition
                cachedOpStatus.put(playerUUID, true);
                player.setOp(true);
                performCommand(player, command);

            } finally {
                player.setOp(false);
                SCore.schedulerHook.runAsyncTask(() -> {
                    SecurityOPQuery.deletePlayerOP(Database.getInstance().connect(), player, false);
                }, 0);
                cachedOpStatus.put(playerUUID, false);
                if (commandsAsOP.get(player).size() == 1) commandsAsOP.remove(player);
                else commandsAsOP.get(player).remove(command);
            }
        }
    }

    public String verifyCommand(String cmd) {
        String command = cmd.trim();
        if (command.charAt(0) != '/') {
            command = "/" + command;
        }
        return command;
    }

}
