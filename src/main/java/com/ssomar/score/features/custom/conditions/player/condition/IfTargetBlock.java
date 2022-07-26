package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.list.ListMaterialFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

// TODO PASS TO ListMaterialGroupFeature
public class IfTargetBlock extends PlayerConditionFeature<ListMaterialFeature, IfTargetBlock> {

    public IfTargetBlock(FeatureParentInterface parent) {
        super(parent, "ifTargetBlock", "If target block", new String[]{}, Material.ANVIL, false);
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
            if (!getCondition().getValue().contains(block.getType())) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfTargetBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListMaterialFeature(this, "ifTargetBlock", new ArrayList<>(), "If target block", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfTargetBlock getNewInstance(FeatureParentInterface parent) {
        return new IfTargetBlock(parent);
    }
}
