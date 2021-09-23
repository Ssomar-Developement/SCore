package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.menu.SimpleGUI;
import com.ssomar.score.projectiles.types.CustomProjectile;
import com.ssomar.score.utils.CustomColor;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

/* For Arrow and potion color */
public class ColorFeature extends DecorateurCustomProjectiles {

    boolean activeColor;
    Color color;

    public ColorFeature(CustomProjectile cProj){
        super.cProj = cProj;
        activeColor = true;
        color = Color.AQUA;
    }

    @Override
    public boolean loadConfiguration(FileConfiguration projConfig) {
        activeColor = projConfig.getBoolean("activeColor", true);
        String colorStr = projConfig.getString("color", "null");
        try {
            color = CustomColor.valueOf(colorStr);
        } catch (Exception e) {
            activeColor = false;
            SCore.plugin.getLogger()
                    .severe("[ExecutableItems] Error invalid color for the projectile: " + this.getId()
                            + " (https://helpch.at/docs/1.12.2/org/bukkit/Color.html)");
            return false;
        }
        return cProj.loadConfiguration(projConfig);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher) {
        if (e instanceof Arrow && activeColor && color != null) {
            ((Arrow) e).setColor(color);
        }
        else if (e instanceof ThrownPotion) {
            ThrownPotion lp = (ThrownPotion) e;

            try {
                ItemStack item = lp.getItem();
                PotionMeta pMeta = (PotionMeta) item.getItemMeta();
                if (this.activeColor)
                    pMeta.setColor(this.color);
                item.setItemMeta(pMeta);
                lp.setItem(item);
            } catch (NoSuchMethodError exception) {}
        }
        cProj.transformTheProjectile(e, launcher);
    }

    @Override
    public SimpleGUI getConfigGUI() {
        SimpleGUI gui = cProj.getConfigGUI();
        return gui;
    }
}
