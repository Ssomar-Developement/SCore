package com.ssomar.score.pack.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.pack.spigot.InjectSpigot;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
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
        // copy the pack in a cache folder to avoid the pack to be deleted
        File cacheFolder = new File(SCore.dataFolder, "textures-cache");
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs();
        }
        File actualPackFile = pack.getFile();
        if (!actualPackFile.exists()) return;
        File cachePackFile = new File(cacheFolder, actualPackFile.getName());
        if (cachePackFile.exists()) {
            cachePackFile.delete();
        }

        // copy the file to the cache folder
        try{
            FileUtils.copyFile(actualPackFile, cachePackFile);
            pack.setFilePath(cachePackFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pack.isDeleteInitialFile()){
            actualPackFile.delete();
        }


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
