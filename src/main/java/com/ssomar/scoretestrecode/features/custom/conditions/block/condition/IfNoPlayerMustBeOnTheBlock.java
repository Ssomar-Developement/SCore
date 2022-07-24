package com.ssomar.scoretestrecode.features.custom.conditions.block.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.block.BlockConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfNoPlayerMustBeOnTheBlock extends BlockConditionFeature<BooleanFeature, IfNoPlayerMustBeOnTheBlock> {

    public IfNoPlayerMustBeOnTheBlock(FeatureParentInterface parent) {
        super(parent, "ifNoPlayerMustBeOnTheBlock", "If no player must be on the block", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public boolean verifCondition(Block b, Optional<Player> playerOpt, SendMessage messangeSender, Event event) {
        if (hasCondition()) {
            boolean onBlock = false;
            Location bLoc = b.getLocation();
            bLoc = bLoc.add(0.5, 1, 0.5);
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                Location pLoc = pl.getLocation();
                if (bLoc.getWorld().getUID().equals(pLoc.getWorld().getUID())) {
                    if (bLoc.distance(pl.getLocation()) < 1.135) {
                        onBlock = true;
                        break;
                    }
                }
            }
            if (onBlock) {
                sendErrorMsg(playerOpt, messangeSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfNoPlayerMustBeOnTheBlock getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifNoPlayerMustBeOnTheBlock", false, "If no player must be on the block", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public IfNoPlayerMustBeOnTheBlock getNewInstance(FeatureParentInterface parent) {
        return new IfNoPlayerMustBeOnTheBlock(parent);
    }
}
