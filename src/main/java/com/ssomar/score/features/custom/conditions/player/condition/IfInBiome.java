package com.ssomar.score.features.custom.conditions.player.condition;

import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.score.features.types.list.ListBiomeFeature;
import com.ssomar.score.utils.SendMessage;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Optional;

public class IfInBiome extends PlayerConditionFeature<ListBiomeFeature, IfInBiome> {

    public IfInBiome(FeatureParentInterface parent) {
        super(parent, "ifInBiome", "If in biome", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            boolean notValid = true;
            for (Biome b : getCondition().getValue()) {
                if (player.getLocation().getBlock().getBiome().equals(b)) {
                    notValid = false;
                    break;
                }
            }
            if (notValid) {
                sendErrorMsg(playerOpt, messageSender);
                cancelEvent(event);
                return false;
            }
        }
        return true;
    }

    @Override
    public IfInBiome getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new ListBiomeFeature(getParent(), "ifInBiome", new ArrayList<>(), "If in biome", new String[]{}, Material.ANVIL, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue().size() > 0;
    }

    @Override
    public IfInBiome getNewInstance(FeatureParentInterface parent) {
        return new IfInBiome(parent);
    }
}
