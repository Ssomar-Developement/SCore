package com.ssomar.score.features.custom.conditions.player;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class PlayerConditionFeature<Y extends FeatureAbstract, T extends PlayerConditionFeature<Y, T>> extends ConditionFeature<Y, T> {

    public PlayerConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event);

}


