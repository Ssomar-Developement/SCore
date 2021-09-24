package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;

public class ChargedFeature extends DecorateurCustomProjectiles {

    boolean isCharged;

    public ChargedFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        isCharged = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isCharged = projConfig.getBoolean("charged", false);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof WitherSkull) {
            ((WitherSkull) e).setCharged(isCharged);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.NETHER_STAR, 1, gui.TITLE_COLOR+"Charged", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Charged", isCharged);
        return gui;
    }

}
