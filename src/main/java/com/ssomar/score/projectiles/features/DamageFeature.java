package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DamageFeature extends DecorateurCustomProjectiles {

    double damage;

    public DamageFeature(CustomProjectile cProj){
        super(cProj.getId());
        super.cProj = cProj;
        damage = -1;
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
        gui.addItem(Material.DIAMOND_SWORD, 1, gui.TITLE_COLOR+"Damage", false, false, gui.CLICK_HERE_TO_CHANGE, "&7actually: ");
        if(damage == -1)  gui.updateActually(gui.TITLE_COLOR+"Damage", "&cVANILLA DAMAGE");
        else gui.updateDouble(gui.TITLE_COLOR+"Damage", damage);
        return gui;
    }

}
