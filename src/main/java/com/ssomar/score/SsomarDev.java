package com.ssomar.score;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class SsomarDev {

    // version 1 of the testmsg
    public static void testMsg(String message, boolean isActiveDebug) {
        if (isActiveDebug /* replace */) {
            try {
                Bukkit.getPlayer("Ssomar").sendMessage(message);
                Bukkit.getLogger().info("from debug >> "+message);
            } catch (Exception ignored) {}

            /*try {
                Bukkit.getPlayer("vayk").sendMessage(message);
            } catch (Exception ignored) {}*/

            try {
                Player p = Bukkit.getPlayer("Moccains");
                Set<String> blockList = Set.of("has papi","add attributes", "Refreshing dura ?", "Food features paper", "updateVariables","VariableReal");

                for (String w : blockList) {
                    if (message.contains(w)) return;
                }

                p.sendMessage(message);
            } catch (Exception ignored) {}
        }

    }

    /**
     * Currently used by Special70. Do whatever you want as its mainly used for checking code flows.
     * @param message
     * @param groupType
     */
    public static void testMsg(String message, DebugMsgGroups groupType) {
        // Add what you want to whitelist
        Set<DebugMsgGroups> whitelist = new HashSet<>();
        whitelist.add(DebugMsgGroups._1);

        if (!whitelist.contains(groupType)) return;
        testMsg(message, true);
    }

    /**
     * Used to group up specific debug messages to allow the dev to enable/disable at will.
     * <br/>
     * Details for each enum is written in SCore Documentation. Contact Special70 for further details
     */
    public enum DebugMsgGroups {
        _1
    }

}
