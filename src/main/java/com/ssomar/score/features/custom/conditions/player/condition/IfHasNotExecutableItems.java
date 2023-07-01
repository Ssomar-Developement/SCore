package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.custom.ifhas.executableitems.group.HasExecutableItemGroupFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfHasNotExecutableItems extends PlayerConditionFeature<HasExecutableItemGroupFeature, IfHasNotExecutableItems> {

    public IfHasNotExecutableItems(FeatureParentInterface parent) {
        super(parent, "ifHasNotExecutableItems", "If has not Executableitems", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        Player player = request.getPlayer();
        if (hasCondition() && !getCondition().verifHasNot(player.getInventory().getContents(), player.getInventory().getHeldItemSlot())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfHasNotExecutableItems getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new HasExecutableItemGroupFeature(getParent(), "ifHasNotExecutableItems", "If has not Executableitems", new String[]{}, Material.DIAMOND, false, true));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getHasExecutableItems().isEmpty();
    }

    @Override
    public IfHasNotExecutableItems getNewInstance(FeatureParentInterface parent) {
        return new IfHasNotExecutableItems(parent);
    }
}
