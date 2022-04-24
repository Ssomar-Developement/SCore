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

public class KnockbackStrengthFeature extends DecorateurCustomProjectiles {

    int knockbackStrength;
    boolean askKnockbackStrength;

    public KnockbackStrengthFeature(CustomProjectile cProj){
        super.cProj = cProj;
        knockbackStrength = -1;
        askKnockbackStrength = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        knockbackStrength = projConfig.getInt("knockbackStrength", -1);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("knockbackStrength", knockbackStrength);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (knockbackStrength != -1)
                aA.setKnockbackStrength(knockbackStrength);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.CHAINMAIL_CHESTPLATE, 1, GUI.TITLE_COLOR +"Knockback Strength", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(knockbackStrength == -1) gui.updateActually(GUI.TITLE_COLOR +"Knockback Strength", "&cVANILLA KNOCKBACK");
        else gui.updateInt(GUI.TITLE_COLOR +"Knockback Strength", knockbackStrength);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change1 = StringConverter.decoloredString(GUI.TITLE_COLOR +"Knockback Strength");

       if(itemName.equals(change1)){
            requestChat = true;
            askKnockbackStrength = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &eKnockbackStregth&a: &7&o(Number, -1 = vanilla knckback)"));
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if(gui.getActually(GUI.TITLE_COLOR +"Knockback Strength").contains("VANILLA KNOCKBACK")){
            knockbackStrength = -1;
        }
        else knockbackStrength = gui.getInt(GUI.TITLE_COLOR +"Knockback Strength");
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        if(cProj.messageForConfig(gui, player, message)) return true;
        if(askKnockbackStrength){
            int newKnock;
            try{
                newKnock = Integer.valueOf(StringConverter.decoloredString(message));
            }catch(NumberFormatException e){
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting knockbackStrength ("+message+")"));
                return true;
            }
            if(newKnock == -1)  gui.updateActually(GUI.TITLE_COLOR +"Knockback Strength", "&cVANILLA KNOCKBACK");
            else gui.updateInt(GUI.TITLE_COLOR +"Knockback Strength", newKnock);
            gui.openGUISync(player);
            askKnockbackStrength = false;
            requestChat = false;
            return true;
        }
        return false;
    }

}
