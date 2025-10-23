package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class IfEntityLastDamageCause extends EntityConditionFeature<ListUncoloredStringFeature, IfEntityLastDamageCause> {

    public IfEntityLastDamageCause(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifEntityLastDamageCause);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            EntityDamageEvent lastDamageCause = entity.getLastDamageCause();

            if (lastDamageCause == null) {
                runInvalidCondition(request);
                return false;
            }

            String causeType = lastDamageCause.getCause().name();
            boolean found = false;
            for (String cause : getCondition().getValue(request.getSp())) {
                if (causeType.equalsIgnoreCase(cause)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfEntityLastDamageCause getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifEntityLastDamageCause, java.util.Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfEntityLastDamageCause getNewInstance(FeatureParentInterface parent) {
        return new IfEntityLastDamageCause(parent);
    }
}
