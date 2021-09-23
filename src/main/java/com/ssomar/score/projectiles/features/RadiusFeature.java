package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;

public class RadiusFeature extends DecorateurCustomProjectiles {

    float yield;

    public RadiusFeature(CustomProjectile cProj){
        super.cProj = cProj;
        yield = -1L;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        yield = (float) projConfig.getDouble("radius", -1);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if(e instanceof Explosive){
           Explosive fireball = (Explosive)e;
           fireball.setYield(yield);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        return gui;
    }
}
