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

public class RadiusFeature extends DecorateurCustomProjectiles {

    float yield;
    boolean askForYield;

    public RadiusFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        yield = -1L;
        askForYield = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        yield = (float) projConfig.getDouble("radius", -1);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("radius", yield);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Explosive) {
            Explosive fireball = (Explosive) e;
            fireball.setYield(yield);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        Material radiusMat;
        if (SCore.is1v12Less()) radiusMat = Material.valueOf("WEB");
        else radiusMat = Material.HEART_OF_THE_SEA;

        gui.addItem(radiusMat, 1, GUI.TITLE_COLOR + "Radius", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if (yield == -1) gui.updateActually(GUI.TITLE_COLOR + "Radius", "&cVANILLA RADIUS");
        else gui.updateDouble(GUI.TITLE_COLOR + "Radius", yield);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String change1 = StringConverter.decoloredString(GUI.TITLE_COLOR + "Radius");

        if (itemName.equals(change1)) {
            requestChat = true;
            askForYield = true;
            player.closeInventory();
            player.sendMessage(StringConverter.coloredString("&2&l>> &aEnter the new &eRadius&a: &7&o(Number, -1 = vanilla radius)"));
        } else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        if (gui.getActually(GUI.TITLE_COLOR + "Radius").contains("VANILLA RADIUS")) {
            yield = -1;
        } else yield = gui.getInt(GUI.TITLE_COLOR + "Radius");
    }

    @Override
    public boolean messageForConfig(SimpleGUI gui, Player player, String message) {
        if (cProj.messageForConfig(gui, player, message)) return true;
        if (askForYield) {
            int newRadius;
            try {
                newRadius = Integer.valueOf(StringConverter.decoloredString(message));
            } catch (NumberFormatException e) {
                player.sendMessage(StringConverter.coloredString("&4&l>> ERROR : &cInvalid number for the setting radius (" + message + ")"));
                return true;
            }
            if (newRadius == -1) gui.updateActually(GUI.TITLE_COLOR + "Radius", "&cVANILLA RADIUS");
            else gui.updateInt(GUI.TITLE_COLOR + "Radius", newRadius);
            gui.openGUISync(player);
            askForYield = false;
            requestChat = false;
            return true;
        }
        return false;
    }
}
