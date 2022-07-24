package com.ssomar.scoretestrecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.usedapi.MyCoreProtectAPI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfMustBeNatural extends BlockConditionFeature<BooleanFeature, IfMustBeNatural> {

    public IfMustBeNatural(FeatureParentInterface parent) {
        super(parent, "ifMustBeNatural", "If must be natural", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender, Event event) {
        if (hasCondition()) {
            if (!MyCoreProtectAPI.isNaturalBlock(b)) {
                sendErrorMsg(playerOpt, messangeSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfMustBeNatural getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifMustBeNatural", false, "If must be natural", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfMustBeNatural getNewInstance(FeatureParentInterface parent) {
        return new IfMustBeNatural(parent);
    }

}
