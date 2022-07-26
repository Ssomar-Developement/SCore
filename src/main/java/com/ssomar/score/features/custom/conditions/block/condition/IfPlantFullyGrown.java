package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlantFullyGrown extends BlockConditionFeature<BooleanFeature, IfPlantFullyGrown> {

    public IfPlantFullyGrown(FeatureParentInterface parent) {
        super(parent, "ifPlantFullyGrown", "If plant fully grown", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {

        if (hasCondition() && b.getState().getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) b.getState().getBlockData();
            int age = ageable.getAge();
            if (age != ageable.getMaximumAge()) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfPlantFullyGrown getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlantFullyGrown", false, "If plant fully grown", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfPlantFullyGrown getNewInstance(FeatureParentInterface parent) {
        return new IfPlantFullyGrown(parent);
    }
}
