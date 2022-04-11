package com.ssomar.score.conditions.condition.blockcondition;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter @Setter
public class BlockConditions extends NewConditions {

	@Override
	public NewConditions createNewInstance() {
		return new BlockConditions();
	}

	public boolean verifConditions(Block b, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			BlockCondition blockCondition = (BlockCondition)object;
			if(!blockCondition.verifCondition(b, playerOpt, messageSender)) return false;
		}
		return true;
	}
}
