package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.custom.required.items.group.RequiredItemGroupFeature;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;

public class IfContainerContains extends BlockConditionFeature<RequiredItemGroupFeature, IfContainerContains> {

    public IfContainerContains(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifContainerContains);
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {

        Block b = request.getBlock();
            if (b.getState() instanceof Container) {
                Container container = (Container) b.getState();
                Inventory inv = container.getInventory();
                if (hasCondition()) {
                    if (getCondition().verify(inv, null)) {
                        return true;
                    }

                    runInvalidCondition(request);
                    return false;
                }
            }
        return true;
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getRequiredItems().size() > 0;
    }

    @Override
    public IfContainerContains getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new RequiredItemGroupFeature(this));
    }

    @Override
    public IfContainerContains getNewInstance(FeatureParentInterface parent) {
        return new IfContainerContains(parent);
    }
}
