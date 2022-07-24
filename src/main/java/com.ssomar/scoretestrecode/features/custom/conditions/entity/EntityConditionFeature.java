package com.ssomar.scoretestrecode.features.custom.conditions.entity;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class EntityConditionFeature<Y extends FeatureAbstract, T extends EntityConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public EntityConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event);

}


