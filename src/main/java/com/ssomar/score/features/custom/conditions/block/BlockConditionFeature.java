package com.ssomar.score.features.custom.conditions.block;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;

public abstract class BlockConditionFeature<Y extends FeatureAbstract, T extends BlockConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public BlockConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(BlockConditionRequest request);

}


