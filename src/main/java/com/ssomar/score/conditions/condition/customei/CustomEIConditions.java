package com.ssomar.score.conditions.condition.customei;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.ConditionsVerification;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

@Getter @Setter
public class CustomEIConditions extends Conditions {

	@Override
	public Conditions createNewInstance() {
		return new CustomEIConditions();
	}

	public ConditionsVerification verifConditions(Player player, ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			CustomEICondition customEICondition = (CustomEICondition)object;
			if(!customEICondition.verifCondition(player, itemStack, playerOpt, messageSender)) return new ConditionsVerification(false, customEICondition.isErrorCancelEvent());
		}
		return new ConditionsVerification(true, false);
	}
}
