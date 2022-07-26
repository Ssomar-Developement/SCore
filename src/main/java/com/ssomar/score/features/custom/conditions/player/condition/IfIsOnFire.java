package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SsomarDev;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfIsOnFire extends PlayerConditionFeature<BooleanFeature, IfIsOnFire> {

    public IfIsOnFire(FeatureParentInterface parent) {
        super(parent, "ifIsOnFire", "If is on fire", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        //SsomarDev.testMsg("IfIsOnFire.verifCondition() >> "+ player.getFireTicks());
        if (hasCondition() && player.getFireTicks() <= 0) {
            sendErrorMsg(playerOpt, messageSender);
            cancelEvent(event);
            SsomarDev.testMsg("IfIsOnFire.verifCondition() >> false");
            return false;
        }
        return true;
    }

    @Override
    public IfIsOnFire getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifIsOnFire", false, "If is on fire", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfIsOnFire getNewInstance(FeatureParentInterface parent) {
        return new IfIsOnFire(parent);
    }
}
