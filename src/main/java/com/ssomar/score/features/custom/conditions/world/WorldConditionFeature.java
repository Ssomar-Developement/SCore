package com.ssomar.score.features.custom.conditions.world;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;

public abstract class WorldConditionFeature<Y extends FeatureAbstract, T extends WorldConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public WorldConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(WorldConditionRequest request);

}


