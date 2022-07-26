package com.ssomar.score.features.custom.conditions.world;

import com.ssomar.score.features.custom.conditions.ConditionFeature;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class WorldConditionFeature<Y extends FeatureAbstract, T extends WorldConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public WorldConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(World world, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event);

}


