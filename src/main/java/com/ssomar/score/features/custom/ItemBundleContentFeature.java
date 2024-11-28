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
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemBundleContentFeature extends ListUncoloredStringFeature implements FeatureForItem {

    private final static boolean DEBUG = false;


    public ItemBundleContentFeature(FeatureParentInterface parent, List<String> defaultValue, FeatureSettingsInterface featureSettings, boolean notSaveIfEqualsToDefaultValue, Optional<List<Suggestion>> suggestions) {
        super(parent, defaultValue, featureSettings, notSaveIfEqualsToDefaultValue, suggestions);
    }

    @Override
    public boolean isAvailable() {
        return SCore.is1v20v5Plus();
    }

    @Override
    public boolean isApplicable(FeatureForItemArgs args) {
        return args.getMeta() instanceof BundleMeta;
    }

    @Override
    public void applyOnItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        if (!isAvailable() || !isApplicable(args)) return;

        BundleMeta bundleMeta = (BundleMeta) meta;
        List<ItemStack> items = new ArrayList<>();
        for (String value : this.getValues()) {
            ItemStack item = Bukkit.getServer().getItemFactory().createItemStack(value);
            SsomarDev.testMsg("ItemBundleContentFeature applyOnItemMeta: the item "+item, DEBUG);
            items.add(item);
        }
        bundleMeta.setItems(items);
    }

    @Override
    public void loadFromItemMeta(FeatureForItemArgs args) {

        ItemMeta meta = args.getMeta();
        List<String> values = new ArrayList<>();

        SsomarDev.testMsg("ItemBundleContentFeature loadFromItemMeta: " + meta, DEBUG);

        if (meta instanceof BundleMeta) {
            BundleMeta bundleMeta = (BundleMeta) meta;
            for (ItemStack item : bundleMeta.getItems()) {
                String value;
                if(item.hasItemMeta()) value = "minecraft:"+item.getType().toString().toLowerCase()+item.getItemMeta().getAsString();
                else value = "minecraft:"+item.getType().toString().toLowerCase();
                SsomarDev.testMsg("ItemBundleContentFeature loadFromItemMeta: the item meta "+value, DEBUG);
                values.add(value);
            }
        }
        else SsomarDev.testMsg("ItemBundleContentFeature loadFromItemMeta: the meta is not a BundleMeta", DEBUG);

        this.setValues(values);
    }

    @Override
    public ResetSetting getResetSetting() {
        return ResetSetting.BUNDLE;
    }

    public static byte[] toByteArray(String arrayString) {
        // Remove square brackets
        arrayString = arrayString.replace("[", "").replace("]", "").trim();

        // Handle empty case
        if (arrayString.isEmpty()) {
            return new byte[0];
        }

        // Split the string by commas and convert each element to a byte
        String[] byteValues = arrayString.split(",\\s*");
        byte[] bytes = new byte[byteValues.length];
        for (int i = 0; i < byteValues.length; i++) {
            bytes[i] = Byte.parseByte(byteValues[i]);
        }
        return bytes;
    }

    @Override
    public ItemBundleContentFeature clone(FeatureParentInterface newParent) {
        ItemBundleContentFeature clone = new ItemBundleContentFeature(newParent, getDefaultValue(), getFeatureSettings(), isNotSaveIfEqualsToDefaultValue(), Optional.empty());
        clone.setValues(this.getValues());
        return clone;
    }
}
