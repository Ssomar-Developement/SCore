package com.ssomar.score.conditions.condition.blockcondition;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.ConditionsVerification;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter @Setter
public class BlockConditions extends Conditions {

	@Override
	public Conditions createNewInstance() {
		return new BlockConditions();
	}

	public ConditionsVerification verifConditions(Block b, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			BlockCondition blockCondition = (BlockCondition)object;
			if(!blockCondition.verifCondition(b, playerOpt, messageSender)) return new ConditionsVerification(false, blockCondition.isErrorCancelEvent());
		}
		return new ConditionsVerification(true, false);
	}
}
