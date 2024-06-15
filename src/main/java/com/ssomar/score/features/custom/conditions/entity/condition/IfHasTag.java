package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Optional;

public class IfHasTag extends EntityConditionFeature<ListUncoloredStringFeature, IfHasTag> {


    public IfHasTag(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifHasTag);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            boolean notValid = false;
            for (String tag : getCondition().getValue(request.getSp())) {
                if (!entity.getScoreboardTags().contains(tag)) {
                    notValid = true;
                    break;
                }
            }
            if (notValid) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfHasTag getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifHasTag, true, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfHasTag getNewInstance(FeatureParentInterface parent) {
        return new IfHasTag(parent);
    }
}
