package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GravityFeature extends DecorateurCustomProjectiles {

    boolean isGravity;

    public GravityFeature(CustomProjectile cProj){
        super(cProj.getId(), cProj.getProjConfig());
        super.cProj = cProj;
        isGravity = true;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isGravity = projConfig.getBoolean("gravity", true);
        return cProj.loadConfiguration() && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setGravity(isGravity);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.ELYTRA, 1, gui.TITLE_COLOR+"Gravity", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Gravity", isGravity);
        return gui;
    }
}
