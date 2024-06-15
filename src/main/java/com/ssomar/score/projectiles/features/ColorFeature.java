package com.ssomar.score.projectiles.features;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.BukkitColorFeature;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.Optional;

/* For Arrow and potion color */
public class ColorFeature extends BukkitColorFeature implements SProjectileFeatureInterface {

    public ColorFeature(FeatureParentInterface parent) {
        super(parent, Optional.empty(), FeatureSettingsSCore.color);
    }

    @Override
    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        //SsomarDev.testMsg("ColorFeature transformTheProjectile ?? "+getValue().isPresent()+" type>> "+e.getType()+" >>> "+(e instanceof Arrow)+" >>>>>> "+e, true);

        //SsomarDev.testMsg("ColorFeature transformTheProjectile >> "+getValue(), true);

        if (e instanceof Arrow && getValue().isPresent()) {
            //SsomarDev.testMsg("ColorFeature transformTheProjectile >> "+getValue().get(), true);
            ((Arrow) e).setColor(getValue().get());
        } else if (e instanceof ThrownPotion) {
            ThrownPotion lp = (ThrownPotion) e;

            //SsomarDev.testMsg("ColorFeature transformTheProjectile", true);
            try {
                ItemStack item = lp.getItem();
                PotionMeta pMeta = (PotionMeta) item.getItemMeta();
                if (getValue().isPresent())
                    pMeta.setColor(getValue().get());
                item.setItemMeta(pMeta);
                lp.setItem(item);
            } catch (NoSuchMethodError ignored) {
                SsomarDev.testMsg(ignored.getMessage(), true);
            }
        }
    }

    @Override
    public ColorFeature clone(FeatureParentInterface newParent) {
        ColorFeature clone = new ColorFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
