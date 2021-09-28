package com.ssomar.score.projectiles.features;

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

public class IncendiaryFeature extends DecorateurCustomProjectiles {

    boolean isIncendiary;

    public IncendiaryFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isIncendiary = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isIncendiary = projConfig.getBoolean("incendiary", false);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if(e instanceof Explosive){
            Explosive fireball = (Explosive)e;
            fireball.setIsIncendiary(isIncendiary);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.CAMPFIRE, 1, gui.TITLE_COLOR+"Incendiary", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Incendiary", isIncendiary);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(gui.TITLE_COLOR+"Incendiary");

        if(itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(gui.TITLE_COLOR+"Incendiary");
            gui.updateBoolean(gui.TITLE_COLOR+"Incendiary", !bool);
        }
        else return false;
        return true;
    }
}
