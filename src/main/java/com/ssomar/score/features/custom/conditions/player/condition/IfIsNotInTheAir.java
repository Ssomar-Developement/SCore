package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionRequest;
import com.ssomar.score.features.types.BooleanFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class IfIsNotInTheAir extends PlayerConditionFeature<BooleanFeature, IfIsNotInTheAir> {


    public IfIsNotInTheAir(FeatureParentInterface parent) {
        super(parent, "ifIsNotInTheAir", "If is not in the air", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(PlayerConditionRequest request) {
        if (hasCondition()) {
            Player player = request.getPlayer();
            Location pLoc = player.getLocation();
            pLoc.subtract(0, 0.1, 0);

            Block block = pLoc.getBlock();
            Material type = block.getType();
            if ((type.equals(Material.AIR) || SCore.is1v17Plus() && (type.equals(Material.LIGHT) || type.equals(Material.CAVE_AIR)))) {
                runInvalidCondition(request);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfIsNotInTheAir getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(this, "ifIsNotInTheAir", false, "If is not in the air", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfIsNotInTheAir getNewInstance(FeatureParentInterface parent) {
        return new IfIsNotInTheAir(parent);
    }
}
