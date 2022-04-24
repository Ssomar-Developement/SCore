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
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class VelocityFeature extends DecorateurCustomProjectiles {

    double velocity;
    boolean askVelocity;

    public VelocityFeature(CustomProjectile cProj){
        super.cProj = cProj;
        velocity = 1;
        askVelocity = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        velocity = projConfig.getDouble("velocity", 1);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("velocity", velocity);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        Vector v = e.getVelocity();
        if (!SCore.is1v11Less() && e instanceof ShulkerBullet) v = launcher.getEyeLocation().getDirection();
        else  if (v.getX() == 0 && v.getY() == 0 && v.getZ() == 0) v = launcher.getEyeLocation().getDirection();
        if (velocity != 1)
            v = v.multiply(velocity);
        e.setVelocity(v);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        Material firework;
        if(SCore.is1v12Less()) firework = Material.valueOf("FIREWORK");
        else firework = Material.FIREWORK_ROCKET;

        gui.addItem(firework, 1, GUI.TITLE_COLOR +"Velocity", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(velocity == -1) gui.updateActually(GUI.TITLE_COLOR +"Velocity", "&cVANILLA VELOCITY");
        else gui.updateDouble(GUI.TITLE_COLOR +"Velocity", velocity);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change1 = StringConverter.decoloredString(GUI.TITLE_COLOR +"Velocity");

        if(itemName.equals(change1)){
            requestChat = true;
            askVelocity = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &eVelocity&a: &7&o(Number, -1 = vanilla velocity)"));
        }
        else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if(gui.getActually(GUI.TITLE_COLOR +"Velocity").contains("VANILLA VELOCITY")){
            velocity = -1;
        }
        else velocity = gui.getDouble(GUI.TITLE_COLOR +"Velocity");
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message){
        if(cProj.messageForConfig(gui, player, message)) return true;
        if(askVelocity){
            double newVelocity;
            try{
                newVelocity = Double.valueOf(StringConverter.decoloredString(message));
            }catch(NumberFormatException e){
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting velocity ("+message+")"));
                return true;
            }
            if(newVelocity == -1)  gui.updateActually(GUI.TITLE_COLOR +"Velocity", "&cVANILLA VELOCITY");
            else gui.updateDouble(GUI.TITLE_COLOR +"Velocity", newVelocity);
            gui.openGUISync(player);
            askVelocity = false;
            requestChat = false;
            return true;
        }
        return false;
    }
}
