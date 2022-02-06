package com.ssomar.score.commands.runnable.player.commands;

import com.ssomar.score.SsomarDev;

import java.util.HashMap;
import java.util.UUID;

public class TeleportOnCursorManager {

    private static TeleportOnCursorManager instance;

    private HashMap<UUID, Long> timings = new HashMap<>();

    public boolean canTp(UUID uuid){
        long actual = System.currentTimeMillis();
        boolean canTp = true;
        if(timings.containsKey(uuid)){
            long time = timings.get(uuid);
            SsomarDev.testMsg("time: "+(actual - time));
            if(actual - time < 200){
                canTp = false;
                return canTp;
            }
        }

        timings.put(uuid, actual);
        return canTp;
    }

    public static TeleportOnCursorManager getInstance(){
        if (instance == null) instance = new TeleportOnCursorManager();
        return instance;
    }
}
