package com.ssomar.scoretestrecode.features.custom.conditions.customei.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.customei.CustomEIConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class IfNeedPlayerConfirmation extends CustomEIConditionFeature<BooleanFeature, IfNeedPlayerConfirmation> {

    public IfNeedPlayerConfirmation(FeatureParentInterface parent) {
        super(parent, "ifNeedPlayerConfirmation", "If need player confirmation", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public IfNeedPlayerConfirmation getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNeedPlayerConfirmation", false, "If need player confirmation", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfNeedPlayerConfirmation getNewInstance(FeatureParentInterface parent) {
        return new IfNeedPlayerConfirmation(parent);
    }

    @Override
    public boolean verifCondition(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender, @Nullable Event event) {
        return true;
    }

}
