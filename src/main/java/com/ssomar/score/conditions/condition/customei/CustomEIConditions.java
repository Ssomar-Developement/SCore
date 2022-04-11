package com.ssomar.score.conditions.condition.customei;

import com.ssomar.score.conditions.NewConditions;
import com.ssomar.score.conditions.condition.worldcondition.WorldCondition;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@Getter @Setter
public class CustomEIConditions extends NewConditions {

	@Override
	public NewConditions createNewInstance() {
		return new CustomEIConditions();
	}

	public boolean verifConditions(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			CustomEICondition customEICondition = (CustomEICondition)object;
			if(!customEICondition.verifCondition(player, itemStack, playerOpt, messageSender)) return false;
		}
		return true;
	}
}
