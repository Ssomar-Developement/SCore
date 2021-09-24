package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CriticalFeature extends DecorateurCustomProjectiles {

    boolean isCritical;

    public CriticalFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        isCritical = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isCritical = projConfig.getBoolean("critical", false);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!SCore.is1v12() && e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            aA.setCritical(isCritical);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.DIAMOND_AXE, 1, gui.TITLE_COLOR+"Critical", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Critical", isCritical);
        return gui;
    }

}
