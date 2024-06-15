package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.list.ListColoredStringFeature;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Optional;

public class IfName extends EntityConditionFeature<ListColoredStringFeature, IfName> {

    public IfName(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifName);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {
            Entity entity = request.getEntity();
            boolean notValid = true;
            for (String name : getCondition().getValue(request.getSp())) {
                if (StringConverter.decoloredString(entity.getName()).equalsIgnoreCase(name)) {
                    notValid = false;
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
    public IfName getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListColoredStringFeature(getParent(),  new ArrayList<>(), FeatureSettingsSCore.ifName, true, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfName getNewInstance(FeatureParentInterface parent) {
        return new IfName(parent);
    }
}
