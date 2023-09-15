package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.custom.ifhas.items.group.HasItemGroupFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IfHasItems extends PlayerConditionFeature<HasItemGroupFeature, IfHasItems> {

    public IfHasItems(FeatureParentInterface parent) {
        super(parent, "ifHasItems", "If has items", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        //SsomarDev.testMsg("IfHasItems.verifCondition >> "+ hasCondition());
        Player player = request.getPlayer();
        if (hasCondition() && !getCondition().verifHas(player.getInventory().getContents(), player.getInventory().getHeldItemSlot())) {
            runInvalidCondition(request);
            return false;
        }
        return true;
    }

    @Override
    public IfHasItems getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new HasItemGroupFeature(getParent(), "ifHasItems", "If has items", new String[]{}, Material.STONE, false, true));
    }

    @Override
    public boolean hasCondition() {
        return !getCondition().getHasItems().isEmpty();
    }

    @Override
    public IfHasItems getNewInstance(FeatureParentInterface parent) {
        return new IfHasItems(parent);
    }
}
