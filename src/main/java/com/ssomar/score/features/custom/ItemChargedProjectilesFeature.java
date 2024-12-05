package com.ssomar.score.features.custom;

import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.editor.Suggestion;
import com.ssomar.score.features.FeatureForItem;
import com.ssomar.score.features.FeatureForItemArgs;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.utils.emums.ResetSetting;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemChargedProjectilesFeature extends ListUncoloredStringFeature implements FeatureForItem {

    private final static boolean DEBUG = true;

    public ItemChargedProjectilesFeature(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, defaultValue, featureSettings, notSaveIfEqualsToDefaultValue, suggestions);
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v5Plus();
    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return args.getMeta() instanceof CrossbowMeta;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        if (!isAvailable() || !isApplicable(args)) return;

        CrossbowMeta crossbowMeta = (CrossbowMeta) meta;
        List<ItemStack> items = new ArrayList<>();
        for (String value : this.getValues()) {
            ItemStack item = Bukkit.getServer().getItemFactory().createItemStack(value);
            SsomarDev.testMsg("ItemContainerFeature applyOnItemMeta: the item "+item, DEBUG);
            items.add(item);
        }
        crossbowMeta.setChargedProjectiles(items);
    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        List<String> values = new ArrayList<>();

        SsomarDev.testMsg("ItemChargedProjectileFeature loadFromItemMeta: " + meta, DEBUG);

        if (meta instanceof CrossbowMeta) {
            CrossbowMeta crossbowMeta = (CrossbowMeta) meta;
            if(!crossbowMeta.hasChargedProjectiles()) return;
            for (ItemStack item : crossbowMeta.getChargedProjectiles()) {
                String value;
                if(item.hasItemMeta()) value = "minecraft:"+item.getType().toString().toLowerCase()+item.getItemMeta().getAsString();
                else value = "minecraft:"+item.getType().toString().toLowerCase();
                SsomarDev.testMsg("ItemChargedProjectileFeature loadFromItemMeta: the item meta "+value, DEBUG);
                values.add(value);
            }
        }
        else SsomarDev.testMsg("ItemChargedProjectileFeature loadFromItemMeta: the meta is not a CrossbowMeta", DEBUG);

        this.setValues(values);
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.CHARGED_PROJECTILES;
    }

    @Override
    public ItemChargedProjectilesFeature clone(FeatureParentInterface newParent) {
        ItemChargedProjectilesFeature clone = new ItemChargedProjectilesFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue(), Optional.empty());
        clone.setValues(this.getValues());
        return clone;
    }
}
