package com.ssomar.score.pack.custom;

import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.UUID;

@Getter
public class PackSettings {

    private UUID uuid;

    private SPlugin sPlugin;

    private String filePath;

    private String customPromptMessage;

    private boolean force;

    private PackHttpInjector injector;

    private String hostedPath;

    public PackSettings(SPlugin sPlugin, UUID uuid, String filePath, String customPromptMessage, boolean force) {
        this.sPlugin = sPlugin;
        this.uuid = uuid;
        this.filePath = filePath;
        this.customPromptMessage = customPromptMessage;
        this.force = force;
        this.injector = new PackHttpInjector(this);
        this.hostedPath = "http://"+Bukkit.getServer().getIp()+":"+ Bukkit.getServer().getPort() +"/score/" + getFileName();
    }

    public String getFileName() {
        return getFile().getName().replaceAll(" ", "_").replaceAll(".zip", "");
    }

    public File getFile() {
        return new File(filePath);
    }

}
