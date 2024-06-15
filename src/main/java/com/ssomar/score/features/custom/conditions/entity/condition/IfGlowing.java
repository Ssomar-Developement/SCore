package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

public class IfGlowing extends EntityConditionFeature<BooleanFeature, IfGlowing> {

    public IfGlowing(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifGlowing);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (getCondition().getValue(request.getSp())) {
            Entity entity = request.getEntity();
            boolean hasError = !entity.isGlowing();
            LivingEntity lE = (LivingEntity) entity;
            try {
                hasError = !lE.hasPotionEffect(PotionEffectType.GLOWING);
            } catch (Exception ignored) {
            }
            if (hasError) {
                runInvalidCondition(request);
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
        setCondition(new BooleanFeature(getParent(),  false, FeatureSettingsSCore.ifGlowing, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().isConfigured();
    }

    @Override
    public IfGlowing getNewInstance(FeatureParentInterface parent) {
        return new IfGlowing(parent);
    }
}
