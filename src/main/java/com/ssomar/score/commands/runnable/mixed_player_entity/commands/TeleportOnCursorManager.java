package com.ssomar.score.commands.runnable.mixed_player_entity.commands;

import java.util.HashMap;
import java.util.UUID;

/**
 * Fix an issue where the tp generates a click
 **/
public class TeleportOnCursorManager {

    private static TeleportOnCursorManager instance;

    private final HashMap<UUID, Long> timings = new HashMap<>();

    public static TeleportOnCursorManager getInstance() {
        if (instance == null) instance = new TeleportOnCursorManager();
        return instance;
    }

    public boolean canTp(UUID uuid) {
        long actual = System.currentTimeMillis();
        boolean canTp = true;
        if (timings.containsKey(uuid)) {
            long time = timings.get(uuid);
            //SsomarDev.testMsg("time: "+(actual - time));
            if (actual - time < 200) {
                canTp = false;
                return canTp;
            }
        }

        timings.put(uuid, actual);
        return canTp;
    }
}
