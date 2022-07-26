package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class IfGlowing extends EntityConditionFeature<BooleanFeature, IfGlowing> {

    public IfGlowing(FeatureParentInterface parent) {
        super(parent, "ifGlowing", "If glowing", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            boolean hasError = !entity.isGlowing();
            LivingEntity lE = (LivingEntity) entity;
            try {
                hasError = !lE.hasPotionEffect(PotionEffectType.GLOWING);
            } catch (Exception ignored) {
            }
            if (hasError) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfGlowing getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifGlowing", false, "If glowing", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfGlowing getNewInstance(FeatureParentInterface parent) {
        return new IfGlowing(parent);
    }
}
