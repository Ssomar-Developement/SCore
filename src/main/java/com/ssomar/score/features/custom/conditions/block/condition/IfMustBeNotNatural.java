package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.usedapi.MyCoreProtectAPI;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfMustBeNotNatural extends BlockConditionFeature<BooleanFeature, IfMustBeNotNatural> {

    public IfMustBeNotNatural(FeatureParentInterface parent) {
        super(parent, "ifMustBeNotNatural", "If must be not natural", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender, Event event) {
        if (hasCondition()) {
            if (MyCoreProtectAPI.isNaturalBlock(b)) {
                sendErrorMsg(playerOpt, messangeSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeNotNatural getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifMustBeNotNatural", false, "If must be not natural", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfMustBeNotNatural getNewInstance(FeatureParentInterface parent) {
        return new IfMustBeNotNatural(parent);
    }

}
