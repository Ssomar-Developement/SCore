package com.ssomar.scoretestrecode.features.custom.conditions.item;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureAbstract;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.ConditionFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ItemConditionFeature<Y extends FeatureAbstract, T extends ItemConditionFeature<Y, T>> extends ConditionFeature<Y, T> {


    public ItemConditionFeature(FeatureParentInterface parent, String name, String editorName, String[] editorDescription, Material editorMaterial, boolean requirePremium) {
        super(parent, name, editorName, editorDescription, editorMaterial, requirePremium);
    }

    public abstract boolean verifCondition(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event);

}


