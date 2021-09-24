package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PickupFeature extends DecorateurCustomProjectiles {

    AbstractArrow.PickupStatus pickupStatus;

    public PickupFeature(CustomProjectile cProj){
        super(cProj.getId(), cProj.getProjConfig());
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
                pickupStatus = AbstractArrow.PickupStatus.ALLOWED;
                SCore.plugin.getLogger()
                        .severe("[ExecutableItems] Error invalid pickupStatus for the projectile: " + this.getId()
                                + " (ALLOWED, CREATIVE_ONLY, DISALLOWED) DEFAULT> ALLOWED");
                return cProj.loadConfiguration() && false;
            }
        }
        return cProj.loadConfiguration() && true;
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
        gui.addItem(Material.LEAD, 1, gui.TITLE_COLOR+"Pickup", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateActually(gui.TITLE_COLOR+"Pickup", pickupStatus.toString());
        return gui;
    }

}
