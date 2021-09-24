package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;

public class IncendiaryFeature extends DecorateurCustomProjectiles {

    boolean isIncendiary;

    public IncendiaryFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        isIncendiary = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isIncendiary = projConfig.getBoolean("incendiary", false);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if(e instanceof Explosive){
            Explosive fireball = (Explosive)e;
            fireball.setIsIncendiary(isIncendiary);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.CAMPFIRE, 1, gui.TITLE_COLOR+"Incendiary", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Incendiary", isIncendiary);
        return gui;
    }
}
