package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.conditions.condition.conditiontype.ConditionType;
import com.ssomar.score.conditions.condition.player.PlayerCondition;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.ListBiomeFeature;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IfNotInBiome extends PlayerConditionFeature<ListBiomeFeature, IfNotInBiome> {

    public IfNotInBiome(FeatureParentInterface parent) {
        super(parent, "ifNotInBiome", "If not in biome", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (hasCondition()) {
            boolean notValid = false;
            for (Biome b : getCondition().getValue()) {
                if (player.getLocation().getBlock().getBiome().equals(b)) {
                    notValid = true;
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
    public IfNotInBiome getValue() {
        return  this;
    }

    @Override
    public void subReset() {
        setCondition(new ListBiomeFeature(getParent(), "ifNotInBiome", new ArrayList<>(), "If not in biome", new String[]{}, Material.ANVIL, false));
    }

    @Override
    public boolean hasCondition() {
        return  getCondition().getValue().size() > 0;
    }

    @Override
    public IfNotInBiome getNewInstance() {
        return  new IfNotInBiome(getParent());
    }
}
