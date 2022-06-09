package com.ssomar.testRecode.features.custom.conditions.block;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.testRecode.features.FeatureInterface;
import com.ssomar.testRecode.features.FeatureParentInterface;
import com.ssomar.testRecode.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BlockConditionFeature<Y extends FeatureInterface, T extends BlockConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public BlockConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event);
}


