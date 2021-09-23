package com.ssomar.score.projectiles.features;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class DecorateurCustomProjectiles extends CustomProjectile {

    protected CustomProjectile cProj;

    public abstract boolean loadConfiguration(FileConfiguration projConfig);

    public abstract void transformTheProjectile(Entity e, Player launcher);

    public abstract SimpleGUI getConfigGUI();

    @Override
    public String getId() {
        return cProj.getId();
    }

    @Override
    public String getIdentifierType() {
        return cProj.getIdentifierType();
    }

    @Override
    public CustomProjectile getLoaded(){
        return cProj.getLoaded();
    }

}
