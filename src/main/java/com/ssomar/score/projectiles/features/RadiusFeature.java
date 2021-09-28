package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.Material;
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
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.HEART_OF_THE_SEA, 1, gui.TITLE_COLOR+"Radius", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(yield == -1) gui.updateActually(gui.TITLE_COLOR+"Radius", "&cVANILLA RADIUS");
        else gui.updateDouble(gui.TITLE_COLOR+"Radius", yield);
        return gui;
    }
}
