package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PierceLevelFeature extends DecorateurCustomProjectiles {

    int pierceLevel;
    boolean askPierceLevel;

    public PierceLevelFeature(CustomProjectile cProj){
        super.cProj = cProj;
        pierceLevel = -1;
        askPierceLevel = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        pierceLevel = projConfig.getInt("pierceLevel", -1);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("pierceLevel", pierceLevel);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (pierceLevel != -1)
                aA.setPierceLevel(pierceLevel);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.TIPPED_ARROW, 1, GUI.TITLE_COLOR +"Pierce level", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(pierceLevel == -1) gui.updateActually(GUI.TITLE_COLOR +"Pierce level", "&cVANILLA PIERCE LEVEL");
        else gui.updateInt(GUI.TITLE_COLOR +"Pierce level", pierceLevel);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change1 = StringConverter.decoloredString(GUI.TITLE_COLOR +"Pierce level");

        if(itemName.equals(change1)){
            requestChat = true;
            askPierceLevel = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &ePierceLevel&a: &7&o(Number, -1 = vanilla pierce level)"));
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if(gui.getActually(GUI.TITLE_COLOR +"Pierce level").contains("VANILLA PIERCE LEVEL")){
            pierceLevel = -1;
        }
        else pierceLevel = gui.getInt(GUI.TITLE_COLOR +"Pierce level");
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        if(cProj.messageForConfig(gui, player, message)) return true;
        if(askPierceLevel){
            int newKnock;
            try{
                newKnock = Integer.valueOf(StringConverter.decoloredString(message));
            }catch(NumberFormatException e){
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting pierceLevel ("+message+")"));
                return true;
            }
            if(newKnock == -1)  gui.updateActually(GUI.TITLE_COLOR +"Pierce level", "&cVANILLA PIERCE LEVEL");
            else gui.updateInt(GUI.TITLE_COLOR +"Pierce level", newKnock);
            gui.openGUISync(player);
            askPierceLevel = false;
            requestChat = false;
            return true;
        }
        return false;
    }
}
