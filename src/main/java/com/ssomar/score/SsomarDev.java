package com.ssomar.score;

import org.bukkit.Bukkit;

import java.util.Set;

public class SsomarDev {

    // version 1 of the testmsg
    public static void testMsg(String message, boolean isActiveDebug) {
        if (isActiveDebug /* replace */) {
            try {
                Bukkit.getPlayer("Ssomar").sendMessage(message);
                Bukkit.getLogger().info("from debug >> "+message);
            } catch (Exception ignored) {}

            try {
                Bukkit.getPlayer("vayk").sendMessage(message);
            } catch (Exception ignored) {}

            try {
                Set<String> blockList = Set.of("has papi","add attributes", "Refreshing dura ?", "Food features paper", "updateVariables","VariableReal");

                for (String w : blockList) {
                    if (message.contains(w)) return;
                }

                Bukkit.getPlayer("Moccains").sendMessage(message);
            } catch (Exception ignored) {}
        }

    }

}
