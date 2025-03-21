package com.ssomar.score.features.custom.itemcheckers;

import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ItemCheckerEnum {

    AMOUNT(FeatureSettingsSCore.cameraOverlay),
    NAME(FeatureSettingsSCore.cancelItemDrop),
    MATERIAL(FeatureSettingsSCore.cancelItemPlace),

    CUSTOM_MODEL_DATA(FeatureSettingsSCore.cancelToolInteractions),
    LORE(FeatureSettingsSCore.cancelConsumption),

    DURABILITY(FeatureSettingsSCore.cancelHorn),

    EXECUTABLEITEM_ID(FeatureSettingsSCore.cancelCraft),
    EXECUTABLEITEM_USAGE(FeatureSettingsSCore.cancelAllCraft),

    EXECUTABLEITEM_VARIABLES(FeatureSettingsSCore.cancelDepositInChest);

    @Getter
    public final FeatureSettingsInterface featureSetting;

    public boolean deprecated = false;

    ItemCheckerEnum(FeatureSettingsInterface featureSetting) {
        this.featureSetting = featureSetting;
    }

    ItemCheckerEnum(FeatureSettingsInterface featureSetting, boolean deprecated) {
        this.featureSetting = featureSetting;
        this.deprecated = deprecated;
    }

    public boolean check(ItemStack item1, ItemStack item2) {

        boolean item1HasMeta = item1.hasItemMeta();
        boolean item2HasMeta = item2.hasItemMeta();
        boolean oneOfThemNaNoMeta = !item1HasMeta || !item2HasMeta;
        boolean bothNaNoMeta = !item1HasMeta && !item2HasMeta;
        ItemMeta item1Meta = item1HasMeta ? item1.getItemMeta() : null;
        ItemMeta item2Meta = item2HasMeta ? item2.getItemMeta() : null;

        switch (this) {
            case AMOUNT:
                return item1.getAmount() == item2.getAmount();
            case NAME:
                if(oneOfThemNaNoMeta) return bothNaNoMeta;

                boolean item1HasDisplayName = item1Meta.hasDisplayName();
                boolean item2HasDisplayName = item2Meta.hasDisplayName();
                boolean oneOfThemNaNoDisplayName = !item1HasDisplayName || !item2HasDisplayName;
                boolean bothNaNoDisplayName = !item1HasDisplayName && !item2HasDisplayName;
                if(oneOfThemNaNoDisplayName) return bothNaNoDisplayName;

                return item1Meta.getDisplayName().equals(item2Meta.getDisplayName());
            case MATERIAL:
                return item1.getType() == item2.getType();
            case CUSTOM_MODEL_DATA:
                if(oneOfThemNaNoMeta) return bothNaNoMeta;

                boolean item1HasCustomModelData = item1Meta.hasCustomModelData();
                boolean item2HasCustomModelData = item2Meta.hasCustomModelData();
                boolean oneOfThemNaNoCustomModelData = !item1HasCustomModelData || !item2HasCustomModelData;
                boolean bothNaNoCustomModelData = !item1HasCustomModelData && !item2HasCustomModelData;
                if(oneOfThemNaNoCustomModelData) return bothNaNoCustomModelData;

                return item1Meta.getCustomModelData() == item2Meta.getCustomModelData();
            case LORE:
                if(oneOfThemNaNoMeta) return bothNaNoMeta;

                boolean item1HasLore = item1Meta.hasLore();
                boolean item2HasLore = item2Meta.hasLore();
                boolean oneOfThemNaNoLore = !item1HasLore || !item2HasLore;
                boolean bothNaNoLore = !item1HasLore && !item2HasLore;
                if(oneOfThemNaNoLore) return bothNaNoLore;

                return item1Meta.getLore().equals(item2Meta.getLore());
            case DURABILITY:
                return item1.getDurability() == item2.getDurability();
            case EXECUTABLEITEM_ID:
                if(oneOfThemNaNoMeta) return bothNaNoMeta;
                return item1Meta.getPersistentDataContainer().getKeys().equals(item2Meta.getPersistentDataContainer().getKeys());
            case EXECUTABLEITEM_USAGE:
                if(oneOfThemNaNoMeta) return bothNaNoMeta;
                return item1Meta.getPersistentDataContainer().getKeys().equals(item2Meta.getPersistentDataContainer().getKeys());
            case EXECUTABLEITEM_VARIABLES:
                if(oneOfThemNaNoMeta) return bothNaNoMeta;
                return item1Meta.getPersistentDataContainer().getKeys().equals(item2Meta.getPersistentDataContainer().getKeys());
                
        }
        return false;
    }

}
