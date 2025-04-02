package com.ssomar.score.projectiles.features;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.types.UncoloredStringFeature;
import com.ssomar.score.utils.numbers.NTools;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomModelDataFeature extends UncoloredStringFeature implements SProjectileFeatureInterface {

    public CustomModelDataFeature(FeatureParentInterface parent) {
        super(parent, Optional.empty(), FeatureSettingsSCore.customModelData, false);
    }

    public void transformTheProjectile(Entity e, Player launcher, Material materialLaunched) {
        if (getValue().isPresent() && e instanceof ThrowableProjectile) {
            ItemStack item = ((ThrowableProjectile)e).getItem();
            ItemMeta meta = item.getItemMeta();
            if (SCore.is1v21v4Plus()) {
                String[] split = getValue().get().split(";");
                List<Float> floats = new ArrayList<>();
                List<Boolean> booleans = new ArrayList<>();
                List<String> strings = new ArrayList<>();
                for (String s : split) {
                    Optional<Float> f = NTools.getFloat(s);
                    if (f.isPresent()) floats.add(f.get());
                    else if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false"))
                        booleans.add(Boolean.parseBoolean(s));
                    else strings.add(s);
                }
                CustomModelDataComponent customModelDataComponent = meta.getCustomModelDataComponent();
                customModelDataComponent.setStrings(strings);
                customModelDataComponent.setFloats(floats);
                customModelDataComponent.setFlags(booleans);
                meta.setCustomModelDataComponent(customModelDataComponent);
            } else {
                Optional<Integer> integerOptional = NTools.getInteger(getValue().get());
                if (integerOptional.isPresent())
                    meta.setCustomModelData(integerOptional.get());
            }
            item.setItemMeta(meta);
            ((ThrowableProjectile) e).setItem(item);
        }
    }

    @Override
    public CustomModelDataFeature clone(FeatureParentInterface newParent) {
        CustomModelDataFeature clone = new  CustomModelDataFeature(newParent);
        clone.setValue(getValue());
        return clone;
    }
}
