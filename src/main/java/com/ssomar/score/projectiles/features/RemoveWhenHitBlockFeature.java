package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.projectiles.types.SProjectiles;
import com.ssomar.score.utils.StringConverter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class RemoveWhenHitBlockFeature extends DecorateurCustomProjectiles {

    boolean removeWhenHitBlock;

    public RemoveWhenHitBlockFeature(CustomProjectile cProj) {
        super.cProj = cProj;
        removeWhenHitBlock = false;
    }

    @Override
    public boolean loadConfiguration(String filePath, FileConfiguration projConfig, boolean showError) {
        removeWhenHitBlock = projConfig.getBoolean("removeWhenHitBlock", false);

        return cProj.loadConfiguration(filePath, projConfig, showError) && true;
    }

    @Override
    public void saveConfiguration(FileConfiguration config) {
        config.set("removeWhenHitBlock", removeWhenHitBlock);
        cProj.saveConfiguration(config);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Projectile && removeWhenHitBlock) {
            ((Projectile) e).getPersistentDataContainer().set(new NamespacedKey(SCore.plugin, "remove_hit_block"), PersistentDataType.INTEGER, 1);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.LEVER, 1, GUI.TITLE_COLOR + "Remove When Hit Block", false, false, GUI.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(GUI.TITLE_COLOR + "Remove When Hit Block", removeWhenHitBlock);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if (cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(GUI.TITLE_COLOR + "Remove When Hit Block");

        if (itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(GUI.TITLE_COLOR + "Remove When Hit Block");
            gui.updateBoolean(GUI.TITLE_COLOR + "Remove When Hit Block", !bool);
        } else return false;
        return true;
    }

    @Override
    public void extractInfosGUI(GUI gui) {
        cProj.extractInfosGUI(gui);
        removeWhenHitBlock = gui.getBoolean(GUI.TITLE_COLOR + "Remove When Hit Block");
    }
}
