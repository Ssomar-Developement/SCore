package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DamageFeature extends DecorateurCustomProjectiles {

    double damage;

    public DamageFeature(CustomProjectile cProj){
        super.cProj = cProj;
        damage = 0;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        damage = projConfig.getDouble("damage", -1);
        return cProj.loadConfiguration(projConfig) && true;
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (!SCore.is1v12() && e instanceof AbstractArrow) {
            AbstractArrow aA = (AbstractArrow) e;
            if (damage != -1)
                aA.setDamage(damage);
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        return gui;
    }

}
