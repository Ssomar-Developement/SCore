package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GlowingFeature extends DecorateurCustomProjectiles {

    boolean isGlowing;

    public GlowingFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        isGlowing = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isGlowing = projConfig.getBoolean("glowing", false);
        return cProj.loadConfiguration() && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setGlowing(isGlowing);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.BEACON, 1, gui.TITLE_COLOR+"Glowing", true, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Glowing", isGlowing);
        return gui;
    }
}
