package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VisualItemFeature extends DecorateurCustomProjectiles {

    ItemStack item;
    boolean hasItem;

    public VisualItemFeature(CustomProjectile cProj){
        super.cProj = cProj;
        item = null;
        this.hasItem = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig, boolean showError) {
        if (projConfig.contains("visualItem")) {
            String material = projConfig.getString("visualItem", "");
            try {
                item = new ItemStack(Material.valueOf(material.toUpperCase()));
                if (projConfig.contains("customModelData")) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setCustomModelData(projConfig.getInt("customModelData", 0));
                    item.setItemMeta(meta);
                }
                hasItem = true;
            } catch (Exception e) {
            }
        }
        return cProj.loadConfiguration(projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        if(hasItem) {
            config.set("visualItem", item.getType().toString());
            if(item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) config.set("customModelData", item.getItemMeta().getCustomModelData());
        }
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!SCore.is1v12() && e instanceof ThrowableProjectile && hasItem) {
            ((ThrowableProjectile) e).setItem(item);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.ITEM_FRAME, 1, gui.TITLE_COLOR+"Visual item", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(item == null) gui.updateActually(gui.TITLE_COLOR+"Visual item", "VANILLA ITEM");
        else gui.updateActually(gui.TITLE_COLOR+"Visual item", item.getType().toString());
        return gui;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        // #TODO extract infos for visual item
        cProj.extractInfosGUI(gui);
    }
}
