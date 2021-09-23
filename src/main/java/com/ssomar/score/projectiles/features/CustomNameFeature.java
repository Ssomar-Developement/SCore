package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CustomNameFeature extends DecorateurCustomProjectiles {

    boolean isCustomNameVisible;
    String customName;

    public CustomNameFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isCustomNameVisible = false;
        customName = "";
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isCustomNameVisible = projConfig.getBoolean("customNameVisible", false);
        customName = StringConverter.coloredString(projConfig.getString("customName", ""));
       return cProj.loadConfiguration(projConfig);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setCustomNameVisible(isCustomNameVisible);
        e.setCustomName(customName);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        return gui;
    }

}
