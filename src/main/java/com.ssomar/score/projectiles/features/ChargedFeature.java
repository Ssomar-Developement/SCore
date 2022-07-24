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
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;

public class ChargedFeature extends DecorateurCustomProjectiles {

    boolean isCharged;

    public ChargedFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        isCharged = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        isCharged = projConfig.getBoolean("charged", false);
        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("charged", isCharged);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof WitherSkull) {
            ((WitherSkull) e).setCharged(isCharged);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.NETHER_STAR, 1, GUI.TITLE_COLOR + "Charged", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(GUI.TITLE_COLOR + "Charged", isCharged);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(GUI.TITLE_COLOR + "Charged");

        if (itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(GUI.TITLE_COLOR + "Charged");
            gui.updateBoolean(GUI.TITLE_COLOR + "Charged", !bool);
        } else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        isCharged = gui.getBoolean(GUI.TITLE_COLOR + "Charged");
    }

}
