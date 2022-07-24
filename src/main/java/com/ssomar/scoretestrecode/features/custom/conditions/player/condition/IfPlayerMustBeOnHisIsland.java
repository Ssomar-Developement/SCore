package com.ssomar.scoretestrecode.features.custom.conditions.player.condition;

import com.ssomar.score.SCore;
import com.ssomar.score.usedapi.IridiumSkyblockTool;
import com.ssomar.score.usedapi.SuperiorSkyblockTool;
import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.player.PlayerConditionFeature;
import com.ssomar.scoretestrecode.features.types.BooleanFeature;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfPlayerMustBeOnHisIsland extends PlayerConditionFeature<BooleanFeature, IfPlayerMustBeOnHisIsland> {

    public IfPlayerMustBeOnHisIsland(FeatureParentInterface parent) {
        super(parent, "ifPlayerMustBeOnHisIsland", "If player must be on his island", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Player player, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if (SCore.hasIridiumSkyblock) {
            if (hasCondition()) {
                if (!IridiumSkyblockTool.playerIsOnHisIsland(player)) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }

            }
        } else if (SCore.hasSuperiorSkyblock2) {
            if (hasCondition()) {
                if (!SuperiorSkyblockTool.playerIsOnHisIsland(player)) {
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public IfPlayerMustBeOnHisIsland getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new BooleanFeature(getParent(), "ifPlayerMustBeOnHisIsland", false, "If player must be on his island", new String[]{}, Material.LEVER, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getValue();
    }

    @Override
    public IfPlayerMustBeOnHisIsland getNewInstance(FeatureParentInterface parent) {
        return new IfPlayerMustBeOnHisIsland(parent);
    }
}
