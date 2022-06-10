package com.ssomar.scoretestrecode.features.custom.conditions.entity.condition;

import com.ssomar.score.utils.SendMessage;
import com.ssomar.scoretestrecode.features.FeatureParentInterface;
import com.ssomar.scoretestrecode.features.custom.conditions.entity.EntityConditionFeature;
import com.ssomar.scoretestrecode.features.custom.entities.group.EntityTypeGroupFeature;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public class IfNotEntityType extends EntityConditionFeature<EntityTypeGroupFeature, IfNotEntityType> {

    public IfNotEntityType(FeatureParentInterface parent) {
        super(parent, "ifNotEntityType", "If not entityType", new String[]{}, Material.ANVIL, false);
    }

    @Override
    public boolean verifCondition(Entity entity, Optional<Player> playerOpt, SendMessage messageSender, Event event) {
        if(hasCondition()) {
            for(EntityType et: getCondition().getValue().getEntityTypeList()) {
                if(entity.getType().equals(et)){
                    sendErrorMsg(playerOpt, messageSender);
                    cancelEvent(event);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public IfNotEntityType getValue() {
        return this;
    }

    @Override
    public void subReset() {
        setCondition(new EntityTypeGroupFeature(this, "ifNotEntityType", "If not entityType", new String[]{}, false, true));
    }

    @Override
    public boolean hasCondition() {
        return getCondition().getEntityTypes().size() > 0;
    }

    @Override
    public IfNotEntityType getNewInstance() {
        return new IfNotEntityType(getParent());
    }
}
