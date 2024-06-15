package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsSCore;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.custom.ifhas.items.group.HasItemGroupFeature;
import org.bukkit.entity.Player;

public class IfHasNotItems extends PlayerConditionFeature<HasItemGroupFeature, IfHasNotItems> {

    public IfHasNotItems(FeatureParentInterface parent) {
        super(parent, FeatureSettingsSCore.ifHasNotItems);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        boolean verif = (!getCondition().verifHasNot(player.getInventory().getContents(), player.getInventory().getHeldItemSlot()));
        //SsomarDev.testMsg("ifHasNotItems >>"+hasCondition()+" >> "+verif, true);
        if (hasCondition() && verif) {
            //SsomarDev.testMsg("ifHasNotItems >> PASSE FALSE ", true);
            runInvalidCondition(request);
            return false;
        }
        //SsomarDev.testMsg("ifHasNotItems >>>>>>>>>>>>>>  "+verif, true);
        return true;
    }

    @Override
    public IfHasNotItems getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new HasItemGroupFeature(getParent(), FeatureSettingsSCore.ifHasNotItems, true));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getHasItems().isEmpty();
    }

    @Override
    public IfHasNotItems getNewInstance(FeatureParentInterface parent) {
        return new IfHasNotItems(parent);
    }
}
