package com.ssomar.score;

import org.bukkit.Bukkit;

public class SsomarDev {

    public static void testMsg(String message, boolean isActiveDebug) {
        if (isActiveDebug) {
            try {
                Bukkit.getPlayer("Ssomar").sendMessage(message);
            } catch (Exception ignored) {}
        }
    }

}
