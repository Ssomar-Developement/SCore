package com.ssomar.score.features.custom.itemcheckers;

import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import lombok.Getter;

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

    public final boolean deprecated = false;

    ItemCheckerEnum(FeatureSettingsInterface featureSetting) {
        this.featureSetting = featureSetting;
    }

    ItemCheckerEnum(FeatureSettingsInterface featureSetting, boolean deprecated) {
        this.featureSetting = featureSetting;
        deprecated = deprecated;
    }

}
