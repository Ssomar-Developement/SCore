package com.ssomar.score.conditions.condition.entity;

import java.util.Optional;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.ConditionsVerification;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Getter @Setter
public class EntityConditions extends Conditions {

	public ConditionsVerification verifConditions(Entity entity, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			EntityCondition entityCondition = (EntityCondition)object;
			if(!entityCondition.verifCondition(entity, playerOpt, messageSender)) return new ConditionsVerification(false, entityCondition.isErrorCancelEvent());
		}
		return new ConditionsVerification(true, false);
	}

	@Override
	public Conditions createNewInstance() {
		return new EntityConditions();
	}
}
