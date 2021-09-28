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
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class BounceFeature extends DecorateurCustomProjectiles {

    boolean isBounce;

    public BounceFeature(CustomProjectile cProj){
        super.cProj = cProj;
        isBounce = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isBounce = projConfig.getBoolean("bounce", false);

        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Projectile) {
            ((Projectile) e).setBounce(isBounce);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI loadConfigGUI(SProjectiles sProj) {
        SimpleGUI gui = cProj.loadConfigGUI(sProj);
        gui.addItem(Material.SLIME_BLOCK, 1, gui.TITLE_COLOR+"Bounce", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Bounce", isBounce);
        return gui;
    }

    @Override
    public boolean interactionConfigGUI(GUI gui, Player player, ItemStack itemS, String title) {
        if(cProj.interactionConfigGUI(gui, player, itemS, title)) return true;
        String itemName = StringConverter.decoloredString(itemS.getItemMeta().getDisplayName());
        String changeBounce = StringConverter.decoloredString(gui.TITLE_COLOR+"Bounce");

        if(itemName.equals(changeBounce)) {
            boolean bool = gui.getBoolean(gui.TITLE_COLOR + "Bounce");
            gui.updateBoolean(gui.TITLE_COLOR + "Bounce", !bool);
        }
        else return false;
        return true;
    }
}
