package com.ssomar.score.features.custom.conditions.entity.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.score.features.custom.conditions.entity.EntityConditionRequest;
import com.ssomar.score.features.types.list.ListUncoloredStringFeature;
import com.ssomar.score.utils.strings.StringConverter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

import java.util.ArrayList;
import java.util.Optional;

public class IfSheepColor extends EntityConditionFeature<ListUncoloredStringFeature, IfSheepColor> {
    public IfSheepColor(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifSheepColor);
    }

    @Override
    public boolean verifCondition(EntityConditionRequest request) {
        if (hasCondition()) {


            Entity entity = request.getEntity();
            // return false automatically if entity is not sheep
            if (!(entity instanceof Sheep)) {
                runInvalidCondition(request);
                return false;
            }

            boolean notValid = true;
            for (String name : getCondition().getValue(request.getSp())) {
                if (StringConverter.decoloredString(
                        String.valueOf(
                                ((Sheep) entity).getColor()
                        )
                ).equalsIgnoreCase(name)) {
                    notValid = false;
                    break;
                }
            }

            if (notValid) {
                runInvalidCondition(request);
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    @Override
    public void subReset() {
        setCondition(new ListUncoloredStringFeature(getParent(), new ArrayList<>(), FeatureSettingsSCore.ifSheepColor, Optional.empty()));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getValue().isEmpty();
    }

    @Override
    public IfSheepColor getNewInstance(FeatureParentInterface newParent) {
        return new IfSheepColor(newParent);
    }

    @Override
    public IfSheepColor getValue() {
        return this;
    }
}
