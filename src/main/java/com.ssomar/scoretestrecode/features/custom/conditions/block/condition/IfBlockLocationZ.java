package com.ssomar.scoretestrecode.features.custom.conditions.block.condition;

import com.ssomar.score.menu.GUI;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfBlockLocationZ extends BlockConditionFeature<NumberConditionFeature, IfBlockLocationZ> {

    public IfBlockLocationZ(FeatureParentInterface parent) {
        super(parent, "ifBlockLocationZ", "If block location Z", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue(playerOpt, messageSender.getSp()).get(), b.getLocation().getZ())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
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
    public IfBlockLocationZ getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(getParent(), "ifBlockLocationZ", "If block location Z", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public IfBlockLocationZ getNewInstance(FeatureParentInterface parent) {
        return new IfBlockLocationZ(parent);
    }
}
