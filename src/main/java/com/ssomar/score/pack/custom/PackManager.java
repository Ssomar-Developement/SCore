package com.ssomar.score.pack.custom;

import com.ssomar.score.pack.spigot.InjectSpigot;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PackManager {

    @Getter
    private Map<UUID, PackSettings> packs;

    private static PackManager instance;

    private PackManager() {
        packs = new HashMap<>();
    }

    public void addPack(PackSettings pack) {
        packs.put(pack.getUuid(), pack);
        InjectSpigot.INSTANCE.registerInjector(pack.getInjector());
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.addResourcePack(pack.getUuid(), pack.getHostedPath(), null, pack.getCustomPromptMessage(), pack.isForce());
        }
    }

    public void removePack(UUID uuid) {
        PackSettings pack = packs.get(uuid);
        if(pack != null) {
            packs.remove(uuid);
            InjectSpigot.INSTANCE.unregisterInjector(pack.getInjector());
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.removeResourcePack(uuid);
            }
        }
    }

    public static PackManager getInstance() {
        if(instance == null) instance = new PackManager();
        return instance;
    }
}
