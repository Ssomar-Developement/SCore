package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IncendiaryFeature extends DecorateurCustomProjectiles {

    boolean isIncendiary;

    public IncendiaryFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isIncendiary = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        isIncendiary = projConfig.getBoolean("incendiary", false);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("incendiary", isIncendiary);
        cProj.saveConfiguration(config);
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
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        Material fire;
        if(SCore.is1v12()) fire = Material.FLINT_AND_STEEL;
        else fire = Material.CAMPFIRE;
        gui.addItem(fire, 1, GUI.TITLE_COLOR +"Incendiary", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(GUI.TITLE_COLOR +"Incendiary", isIncendiary);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(GUI.TITLE_COLOR +"Incendiary");

        if(itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(GUI.TITLE_COLOR +"Incendiary");
            gui.updateBoolean(GUI.TITLE_COLOR +"Incendiary", !bool);
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        isIncendiary = gui.getBoolean(GUI.TITLE_COLOR +"Incendiary");
    }
}
