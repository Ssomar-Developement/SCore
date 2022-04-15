package com.ssomar.score.conditions.condition.world;

import java.util.Optional;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.ConditionsVerification;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter @Setter
public class WorldConditions extends Conditions {

	@Override
	public Conditions createNewInstance() {
		return new WorldConditions();
	}

	public ConditionsVerification verifConditions(World w, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			WorldCondition worldCondition = (WorldCondition)object;
			if(!worldCondition.verifCondition(w, playerOpt, messageSender)) return new ConditionsVerification(false, worldCondition.isErrorCancelEvent());
		}
		return new ConditionsVerification(true, false);
	}
}
