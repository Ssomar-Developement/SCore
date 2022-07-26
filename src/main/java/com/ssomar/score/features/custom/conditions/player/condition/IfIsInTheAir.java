package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfIsInTheAir extends PlayerConditionFeature<BooleanFeature, IfIsInTheAir> {


    public IfIsInTheAir(FeatureParentInterface parent) {
        super(parent, "ifIsInTheAir", "If is in the air", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();
            if (!type.equals(Material.AIR)) {
                sendErrorMsg(playerOpt, messageSender);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsInTheAir getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, "ifIsInTheAir", false, "If is in the air", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfIsInTheAir getNewInstance(FeatureParentInterface parent) {
        return new IfIsInTheAir(parent);
    }
}
