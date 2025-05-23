package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockGrowEvent;

public class IfPlantNotFullyGrown extends BlockConditionFeature<BooleanFeature, IfPlantNotFullyGrown> {

    public IfPlantNotFullyGrown(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifPlantNotFullyGrown);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {

        Block b = request.getBlock();
        Event event = request.getEvent();
        BlockData blockData = b.getState().getBlockData();
        /* To make the condition works correctly with the BlockGrow event*/
        if(event instanceof BlockGrowEvent) {
            BlockGrowEvent blockGrowEvent = (BlockGrowEvent) event;
            blockData = blockGrowEvent.getNewState().getBlockData();
        }

        if (getCondition().getValue(request.getSp()) && blockData instanceof Ageable) {
            Ageable ageable = (Ageable) blockData;
            int age = ageable.getAge();
            if (age == ageable.getMaximumAge()) {
               runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlantNotFullyGrown getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), false, FeatureSettingsSCore.ifPlantNotFullyGrown));
    }

    @Override
    public IfPlantNotFullyGrown getNewInstance(FeatureParentInterface parent) {
        return new IfPlantNotFullyGrown(parent);
    }
}
