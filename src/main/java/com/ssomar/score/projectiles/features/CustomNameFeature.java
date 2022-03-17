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

public class CustomNameFeature extends DecorateurCustomProjectiles {

    boolean isCustomNameVisible;
    String customName;
    boolean askName;

    public CustomNameFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isCustomNameVisible = false;
        askName = false;
        customName = "";
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        isCustomNameVisible = projConfig.getBoolean("customNameVisible", false);
        customName = StringConverter.coloredString(projConfig.getString("customName", ""));
        return cProj.loadConfiguration(filePath, projConfig, showError);
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("customNameVisible", isCustomNameVisible);
        config.set("customName", customName);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setCustomNameVisible(isCustomNameVisible);
        e.setCustomName(customName);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.NAME_TAG, 1, GUI.TITLE_COLOR +"1) Custom name visible", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(GUI.TITLE_COLOR +"1) Custom name visible", isCustomNameVisible);

        gui.addItem(Material.NAME_TAG, 1, GUI.TITLE_COLOR +"2) Custom name", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(customName.isEmpty()) gui.updateActually(GUI.TITLE_COLOR +"2) Custom name", "&cNO NAME");
        else gui.updateActually(GUI.TITLE_COLOR +"2) Custom name", customName);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change1 = StringConverter.decoloredString(GUI.TITLE_COLOR +"1) Custom name visible");
        String change2 = StringConverter.decoloredString(GUI.TITLE_COLOR +"2) Custom name");

        if(itemName.equals(change1)) {
            boolean bool = gui.getBoolean(GUI.TITLE_COLOR +"1) Custom name visible");
            gui.updateBoolean(GUI.TITLE_COLOR +"1) Custom name visible", !bool);
        }
        else if(itemName.equals(change2)){
            requestChat = true;
            askName = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &eCustomName&a:"));
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        isCustomNameVisible = gui.getBoolean(GUI.TITLE_COLOR +"1) Custom name visible");
        customName = gui.getActuallyWithColor(GUI.TITLE_COLOR +"2) Custom name");
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        if(cProj.messageForConfig(gui, player, message)) return true;
        if(askName){
            gui.updateActually(GUI.TITLE_COLOR +"2) Custom name", message);
            gui.openGUISync(player);
            askName = false;
            requestChat = false;
            return true;
        }
        return false;
    }

}
