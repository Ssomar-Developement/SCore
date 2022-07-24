package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.list.ListMaterialFeature;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

// TODO PASS TO ListMaterialGroupFeature
public class IfNotTargetBlock extends PlayerConditionFeature<ListMaterialFeature, IfNotTargetBlock> {

    public IfNotTargetBlock(FeatureParentInterface parent) {
        super(parent, "ifNotTargetBlock", "If not target block", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Block block = player.getTargetBlock(null, 5);
            /* take only the fix block, not hte falling block */
            if ((block.getType().equals(Material.WATER) || block.getType().equals(Material.LAVA)) && !block.getBlockData().getAsString().contains("level=0")) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
            if (getCondition().getValue().contains(block.getType())) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNotTargetBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListMaterialFeature(getParent(), "ifNotTargetBlock", new ArrayList<>(), "If not target block", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotTargetBlock getNewInstance(FeatureParentInterface parent) {
        return new IfNotTargetBlock(parent);
    }
}
