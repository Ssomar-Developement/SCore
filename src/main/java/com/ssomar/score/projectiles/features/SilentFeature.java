package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SilentFeature extends DecorateurCustomProjectiles {

    boolean isSilent;

    public SilentFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isSilent = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig, boolean showError) {
        isSilent = projConfig.getBoolean("silent", false);
        return cProj.loadConfiguration(projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("silent", isSilent);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setSilent(isSilent);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.BELL, 1, GUI.TITLE_COLOR +"Silent", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(GUI.TITLE_COLOR +"Silent", isSilent);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(GUI.TITLE_COLOR +"Silent");

        if(itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(GUI.TITLE_COLOR +"Silent");
            gui.updateBoolean(GUI.TITLE_COLOR +"Silent", !bool);
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        isSilent = gui.getBoolean(GUI.TITLE_COLOR +"Silent");
    }
}
