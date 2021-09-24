package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.util.Vector;

public class VelocityFeature extends DecorateurCustomProjectiles {

    double velocity;

    public VelocityFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        velocity = 1;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        velocity = projConfig.getDouble("velocity", 1);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        Vector v = e.getVelocity();
        if (e instanceof ShulkerBullet) v = launcher.getEyeLocation().getDirection();
        else  if (v.getX() == 0 && v.getY() == 0 && v.getZ() == 0) v = launcher.getEyeLocation().getDirection();
        if (velocity != 1)
            v = v.multiply(velocity);
        e.setVelocity(v);
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        gui.addItem(Material.FIREWORK_ROCKET, 1, gui.TITLE_COLOR+"Velocity", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        gui.updateDouble(gui.TITLE_COLOR+"Velocity", velocity);
        return gui;
    }
}
