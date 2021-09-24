package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CustomNameFeature extends DecorateurCustomProjectiles {

    boolean isCustomNameVisible;
    String customName;

    public CustomNameFeature(CustomProjectile cProj){
        super(cProj.getId());
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
        gui.addItem(Material.NAME_TAG, 1, gui.TITLE_COLOR+"1) Custom name visible", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"1) Custom name visible", isCustomNameVisible);

        gui.addItem(Material.NAME_TAG, 1, gui.TITLE_COLOR+"2) Custom name", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(customName.isEmpty()) gui.updateActually(gui.TITLE_COLOR+"2) Custom name", "&cNO NAME");
        else gui.updateActually(gui.TITLE_COLOR+"2) Custom name", customName);
        return gui;
    }

}
