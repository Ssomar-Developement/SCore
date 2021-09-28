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

public class GravityFeature extends DecorateurCustomProjectiles {

    boolean isGravity;

    public GravityFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isGravity = true;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isGravity = projConfig.getBoolean("gravity", true);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        e.setGravity(isGravity);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.ELYTRA, 1, gui.TITLE_COLOR+"Gravity", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Gravity", isGravity);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(gui.TITLE_COLOR+"Gravity");

        if(itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(gui.TITLE_COLOR+"Gravity");
            gui.updateBoolean(gui.TITLE_COLOR+"Gravity", !bool);
        }
        else return false;
        return true;
    }
}
