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
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VisualItemFeature extends DecorateurCustomProjectiles {

    Material material;
    int customModeldata;
    boolean hasItem;
    boolean askVisualItem;
    boolean askCustomModelData;

    public VisualItemFeature(CustomProjectile cProj){
        super.cProj = cProj;
        material = null;
        customModeldata = -1;
        this.hasItem = false;
        askVisualItem = false;
        askCustomModelData = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        if (projConfig.contains("visualItem")) {
            String materialStr = projConfig.getString("visualItem", "");
            try {
                material = Material.valueOf(materialStr.toUpperCase());
                if (projConfig.contains("customModelData")) {
                    customModeldata = projConfig.getInt("customModelData", 0);
                }
                hasItem = true;
            } catch (Exception e) {
            }
        }
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        if(hasItem) {
            config.set("visualItem", material.name());
            config.set("customModelData", customModeldata);
        }
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!SCore.is1v12() && e instanceof ThrowableProjectile && hasItem) {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            if( customModeldata != -1) meta.setCustomModelData(customModeldata);
            item.setItemMeta(meta);
            ((ThrowableProjectile) e).setItem(item);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.ITEM_FRAME, 1, GUI.TITLE_COLOR +"1) Visual item", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(!hasItem) gui.updateActually(GUI.TITLE_COLOR +"1) Visual item", "VANILLA MATERIAL");
        else gui.updateActually(GUI.TITLE_COLOR +"1) Visual item", material.name());

        gui.addItem(Material.ITEM_FRAME, 1, GUI.TITLE_COLOR +"2) Visual item (model data)", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(customModeldata == -1) gui.updateActually(GUI.TITLE_COLOR +"2) Visual item (model data)", "VANILLA TEXTURE");
        else gui.updateInt(GUI.TITLE_COLOR +"2) Visual item (model data)", customModeldata);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change1 = StringConverter.decoloredString(GUI.TITLE_COLOR +"1) Visual item");
        String change2 = StringConverter.decoloredString(GUI.TITLE_COLOR +"2) Visual item (model data)");


        if(itemName.equals(change1)){
            requestChat = true;
            askVisualItem = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the material of the &eVisualItem&a: &7&o( https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html )"));
        }
        else if(itemName.equals(change2)){
            requestChat = true;
            askCustomModelData = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the id of the &eCustom model&a: &7&o(Number , -1 for vanilla texture )"));
        }
        else return false;
        return true;
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        if(cProj.messageForConfig(gui, player, message)) return true;
        if(askVisualItem){
            Material newMaterial;
            try{
                newMaterial = Material.valueOf(StringConverter.decoloredString(message).toUpperCase());
            }catch(IllegalArgumentException e){
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid material for the setting visualItem ("+message+") &7&o( https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html )"));
                return true;
            }
            if(newMaterial == null) gui.updateActually(GUI.TITLE_COLOR +"1) Visual item" ,"VANILLA MATERIAL");
            else gui.updateActually(GUI.TITLE_COLOR +"1) Visual item", newMaterial.name());

            gui.openGUISync(player);
            askVisualItem = false;
            requestChat = false;
            return true;
        }
        else  if(askCustomModelData){
            int newCustomModel;
            try{
                newCustomModel = Integer.valueOf(StringConverter.decoloredString(message));
            }catch(NumberFormatException e){
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting custom model data ("+message+") &7&o( Number, -1 for vanilla texture )"));
                return true;
            }
            if( newCustomModel == -1) gui.updateActually(GUI.TITLE_COLOR +"2) Visual item (model data)", "VANILLA TEXTURE");
            else gui.updateInt(GUI.TITLE_COLOR +"2) Visual item (model data)", newCustomModel);

            gui.openGUISync(player);
            askCustomModelData = false;
            requestChat = false;
            return true;
        }
        return false;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        if(gui.getActually(GUI.TITLE_COLOR +"1) Visual item").contains("VANILLA MATERIAL")){
            material = null;
            hasItem = false;
        } else{
            hasItem = true;
            material = Material.valueOf(gui.getActually(GUI.TITLE_COLOR +"1) Visual item"));
        }

        if(gui.getActually(GUI.TITLE_COLOR +"2) Visual item (model data)").contains("VANILLA TEXTURE")){
            customModeldata = -1;
        } else customModeldata = gui.getInt(GUI.TITLE_COLOR +"2) Visual item (model data)");
        cProj.extractInfosGUI(gui);
    }
}
