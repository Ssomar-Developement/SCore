package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListMaterialFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfIsNotInTheBlock extends PlayerConditionFeature<ListMaterialFeature, IfIsNotInTheBlock> {

    public IfIsNotInTheBlock(FeatureParentInterface parent) {
        super(parent, "ifIsNotInTheBlock", "If not in the block", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Location pLoc = player.getLocation();
            Block block = pLoc.getBlock();
            Material type = block.getType();

            Block block2 = pLoc.getBlock().getRelative(BlockFace.UP);
            Material type2 = block2.getType();

            if (getCondition().getValue().contains(type) || getCondition().getValue().contains(type2)) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsNotInTheBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListMaterialFeature(this, "ifIsNotInTheBlock", new ArrayList<>(), "If is not in the block", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfIsNotInTheBlock getNewInstance() {
        return new IfIsNotInTheBlock(getParent());
    }
}
