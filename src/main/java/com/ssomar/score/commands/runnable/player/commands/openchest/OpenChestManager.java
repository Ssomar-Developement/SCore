package com.ssomar.score.commands.runnable.player.commands.openchest;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import org.bukkit.Chunk;

import java.util.*;

public class OpenChestManager {

    private static OpenChestManager instance;

    private Map<Chunk, List<UUID>> forcedChunks = new HashMap<>();

    private OpenChestManager() {
        forcedChunks = new HashMap<>();
    }

    public Map<Chunk, List<UUID>> getForcedChunks() {
        return forcedChunks;
    }

    public boolean isChunkForced(Chunk chunk) {
        return forcedChunks.containsKey(chunk);
    }

    public void addForcedChunk(Chunk chunk, UUID uuid) {

        if(forcedChunks.containsKey(chunk)) {
            List<UUID> list = forcedChunks.get(chunk);
            if(!list.contains(uuid)) list.add(uuid);
        }
        else {
            List<UUID> list = new ArrayList<>();
            list.add(uuid);
            forcedChunks.put(chunk, list);

            chunk.load();
            chunk.addPluginChunkTicket(SCore.plugin);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    SsomarDev.testMsg("CHUNK >>"+ chunk.getX()+" "+chunk.getZ()+" >> "+ chunk.getLoadLevel(), true);
                }
            };
            SCore.schedulerHook.runRepeatingTask(run, 0, 20);
        }
    }

    public void removeForcedChunk(Chunk chunk, UUID uuid) {
        if(forcedChunks.containsKey(chunk)) {
            List<UUID> list = forcedChunks.get(chunk);
            list.remove(uuid);
            if(list.isEmpty()){
                forcedChunks.remove(chunk);
                chunk.removePluginChunkTicket(SCore.plugin);
            }
        }
    }

    public void removeForcedChunk(UUID uuid) {
        List<Chunk> toRemove = new ArrayList<>();
        for(Chunk chunk : forcedChunks.keySet()) {
            List<UUID> list = forcedChunks.get(chunk);
            list.remove(uuid);
            if(list.isEmpty()) {
                toRemove.add(chunk);
                chunk.removePluginChunkTicket(SCore.plugin);
            }
        }
        for(Chunk chunk : toRemove) {
            forcedChunks.remove(chunk);
        }
    }


    public static OpenChestManager getInstance() {
        if(instance == null) instance = new OpenChestManager();
        return instance;
    }

}
