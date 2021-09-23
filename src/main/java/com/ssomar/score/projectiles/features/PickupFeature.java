package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PickupFeature extends DecorateurCustomProjectiles {

    AbstractArrow.PickupStatus pickupStatus;

    public PickupFeature(CustomProjectile cProj){
        super.cProj = cProj;
        pickupStatus = AbstractArrow.PickupStatus.ALLOWED;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {

        if (!SCore.is1v12() && projConfig.contains("pickupStatus")) {
            String pickStatus = projConfig.getString("pickupStatus", "null");
            try {
                pickupStatus = AbstractArrow.PickupStatus.valueOf(pickStatus.toUpperCase());
            } catch (Exception e) {
                SCore.plugin.getLogger()
                        .severe("[ExecutableItems] Error invalid pickupStatus for the projectile: " + this.getId()
                                + " (ALLOWED, CREATIVE_ONLY, DISALLOWED) DEFAULT> ALLOWED");
                return false;
            }
        }
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!SCore.is1v12() && e instanceof AbstractArrow)
            ((AbstractArrow)e).setPickupStatus(pickupStatus);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        return gui;
    }

}
