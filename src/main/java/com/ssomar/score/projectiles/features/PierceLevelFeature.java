package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PierceLevelFeature extends DecorateurCustomProjectiles {

    int pierceLevel;

    public PierceLevelFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        pierceLevel = -1;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        pierceLevel = projConfig.getInt("pierceLevel", -1);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!SCore.is1v12() && e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (pierceLevel != -1)
                aA.setPierceLevel(pierceLevel);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.TIPPED_ARROW, 1, gui.TITLE_COLOR+"Pierce level", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(pierceLevel == -1) gui.updateActually(gui.TITLE_COLOR+"Pierce level", "&cVANILLA PIERCE LEVEL");
        else gui.updateInt(gui.TITLE_COLOR+"Pierce level", pierceLevel);
        return gui;
    }
}
