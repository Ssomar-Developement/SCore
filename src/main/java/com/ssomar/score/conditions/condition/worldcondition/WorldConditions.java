package com.ssomar.score.conditions.condition.worldcondition;

import java.util.Optional;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter @Setter
public class WorldConditions extends NewConditions {

	@Override
	public NewConditions createNewInstance() {
		return new WorldConditions();
	}

	public boolean verifConditions(World w, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			WorldCondition worldCondition = (WorldCondition)object;
			if(!worldCondition.verifCondition(w, playerOpt, messageSender)) return false;
		}
		return true;
	}
}
