package com.ssomar.score.projectiles.types;

import com.ssomar.score.menu.SimpleGUI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;

public abstract class CustomProjectile {

    String identifierType;
    SimpleGUI configGui;

    public boolean loadConfiguration(File file){
        FileConfiguration projConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return this.loadConfiguration(projConfig);
    }

    public boolean loadConfiguration(FileConfiguration projConfig){
        identifierType = projConfig.getString("type", "null");
        return true;
    }

    public void transformTheProjectile(Entity e, Player launcher){

    }

    public SimpleGUI getConfigGUI(){
      return this.configGui;
    }

    public void openConfigGUIFor(Player p){
        this.getConfigGUI().openGUISync(p);
    }

    public abstract CustomProjectile getLoaded();

    public abstract String getId();

    public abstract String getIdentifierType();

}
