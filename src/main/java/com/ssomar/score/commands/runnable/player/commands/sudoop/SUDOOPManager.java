package com.ssomar.score.commands.runnable.player.commands.sudoop;

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

    public SUDOOPManager() {
        commandsAsOP = new HashMap<>();
        playersThatMustBeDeOP = SecurityOPQuery.loadUsersOp(Database.getInstance().connect());
    }

    public static void performCommand(final Player player, final String command) {
//	    BukkitRunnable runnable = new BukkitRunnable() {
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
            try {
                if (commandsAsOP.containsKey(player)) {
                    commandsAsOP.get(player).add(command);
                } else {
                    ArrayList<String> cList = new ArrayList<>();
                    cList.add(command);
                    commandsAsOP.put(player, cList);
                }
                if (SecurityOPQuery.insertPlayerOP(Database.getInstance().connect(), Arrays.asList(player))) {
                    player.setOp(true);
                    performCommand(player, command);
                }
            } finally {
                player.setOp(false);
                SecurityOPQuery.deletePlayerOP(Database.getInstance().connect(), player, true);
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
