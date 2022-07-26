package com.ssomar.score.features.custom.conditions.block.condition;

import com.ssomar.score.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfBlockLocationX extends BlockConditionFeature<NumberConditionFeature, IfBlockLocationX> {

    public IfBlockLocationX(FeatureParentInterface parent) {
        super(parent, "ifBlockLocationX", "If block location X", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(playerOpt, messageSender.getSp()).get(), b.getLocation().getX())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
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
    public IfBlockLocationX getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), "ifBlockLocationX", "If block location X", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfBlockLocationX getNewInstance(FeatureParentInterface parent) {
        return new IfBlockLocationX(parent);
    }
}
