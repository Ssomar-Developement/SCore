package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class BounceFeature extends DecorateurCustomProjectiles {

    boolean isBounce;

    public BounceFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        isBounce = false;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        isBounce = projConfig.getBoolean("bounce", false);

        return cProj.loadConfiguration() && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Projectile) {
            ((Projectile) e).setBounce(isBounce);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.SLIME_BLOCK, 1, gui.TITLE_COLOR+"Bounce", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateBoolean(gui.TITLE_COLOR+"Bounce", isBounce);
        return gui;
    }
}
