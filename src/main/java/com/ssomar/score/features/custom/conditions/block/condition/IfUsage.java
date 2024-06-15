package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.executableblocks.api.ExecutableBlocksAPI;
import com.ssomar.executableblocks.executableblocks.placedblocks.ExecutableBlockPlaced;
import com.ssomar.executableblocks.executableblocks.placedblocks.LocationConverter;
import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.custom.conditions.block.BlockConditionRequest;
import com.ssomar.score.features.types.NumberConditionFeature;
import com.ssomar.score.utils.strings.StringCalculation;
import org.bukkit.Location;

import java.util.Optional;

public class IfUsage extends BlockConditionFeature<NumberConditionFeature, IfUsage> {

    public IfUsage(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifUsage);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public boolean verifCondition(BlockConditionRequest request) {

        if (hasCondition() && SCore.hasExecutableBlocks) {

            Location bLoc = LocationConverter.convert(request.getBlock().getLocation(), false, false);
            Optional<ExecutableBlockPlaced> eBPOpt = ExecutableBlocksAPI.getExecutableBlocksPlacedManager().getExecutableBlockPlaced(bLoc);
            if (eBPOpt.isPresent()) {
                ExecutableBlockPlaced eBP = eBPOpt.get();
                int usage = eBP.getUsage();

                if (!StringCalculation.calculation(getCondition().getValue(request.getPlayerOpt(), request.getSp()).get(), usage)) {
                    runInvalidCondition(request);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfUsage getValue() {
        return this;
    }

    @Override
    public String[] getEditorDescription() {
        String[] finalDescription = new String[super.getEditorDescription().length + 1];
        if (getCondition().getValue().isPresent()) {
            finalDescription[finalDescription.length - 1] = "&7Condition: &e" + getCondition().getValue().get();
        } else {
            finalDescription[finalDescription.length - 1] = "&7Condition: &cNO CONDITION";
        }
        return finalDescription;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), FeatureSettingsSCore.ifUsage));
    }

    @Override
    public IfUsage getNewInstance(FeatureParentInterface parent) {
        return new IfUsage(parent);
    }
}
