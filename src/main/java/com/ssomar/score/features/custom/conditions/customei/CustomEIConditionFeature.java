package com.ssomar.score.features.custom.conditions.customei;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.ConditionFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class CustomEIConditionFeature<Y extends FeatureAbstract, T extends CustomEIConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public CustomEIConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event);

}


