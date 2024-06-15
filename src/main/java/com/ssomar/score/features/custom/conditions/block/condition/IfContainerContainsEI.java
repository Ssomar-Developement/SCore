package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.custom.required.executableitems.group.RequiredExecutableItemGroupFeature;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;

public class IfContainerContainsEI extends BlockConditionFeature<RequiredExecutableItemGroupFeature, IfContainerContainsEI> {

    public IfContainerContainsEI(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifContainerContainsEI);
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
        return getCondition().getRequiredExecutableItems().size() > 0;
    }

    @Override
    public IfContainerContainsEI getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new RequiredExecutableItemGroupFeature(this));
    }

    @Override
    public IfContainerContainsEI getNewInstance(FeatureParentInterface parent) {
        return new IfContainerContainsEI(parent);
    }
}
