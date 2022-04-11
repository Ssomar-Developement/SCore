package com.ssomar.score.conditions.condition.entityconditions;

import java.util.Optional;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Getter @Setter
public class EntityConditions extends NewConditions {

	public boolean verifConditions(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			EntityCondition entityCondition = (EntityCondition)object;
			if(!entityCondition.verifCondition(entity, playerOpt, messageSender)) return false;
		}
		return true;
	}

	@Override
	public NewConditions createNewInstance() {
		return new EntityConditions();
	}
}
