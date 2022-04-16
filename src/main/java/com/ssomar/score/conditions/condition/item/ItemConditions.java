package com.ssomar.score.conditions.condition.item;

import java.util.*;

import com.ssomar.score.conditions.Conditions;
import com.ssomar.score.conditions.ConditionsVerification;
import com.ssomar.score.utils.SendMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public class ItemConditions extends Conditions {

	public ConditionsVerification verifConditions(ItemStack itemStack, Optional<Player> playerOpt, SendMessage messageSender) {

		for(Object object : getConditions().values()){
			ItemCondition itemCondition = (ItemCondition)object;
			if(!itemCondition.verifCondition(itemStack, playerOpt, messageSender)) return new ConditionsVerification(false, itemCondition.isErrorCancelEvent());
		}
		return new ConditionsVerification(true, false);
	}

	@Override
	public Conditions createNewInstance() {
		return new ItemConditions();
	}
}