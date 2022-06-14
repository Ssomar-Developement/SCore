package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.utils.StringCalculation;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.NumberConditionFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfLightLevel extends PlayerConditionFeature<NumberConditionFeature, IfLightLevel> {

    public IfLightLevel(FeatureParentInterface parent) {
        super(parent, "ifLightLevel", "If light level", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition() && !StringCalculation.calculation(getCondition().getValue().get(), player.getEyeLocation().getBlock().getLightLevel())) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            return false;
        }
        return true;
    }

    @Override
    public IfLightLevel getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new NumberConditionFeature(this, "ifLightLevel", "If light level", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().isPresent();
    }

    @Override
    public IfLightLevel getNewInstance() {
        return new IfLightLevel(getParent());
    }
}
